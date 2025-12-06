/*      */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.NotImplementedException;
/*      */ import org.reflections.Reflections;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CmdProtMarshaller
/*      */ {
/*   39 */   private static Logger logger = LoggerFactory.getLogger(CmdProtMarshaller.class);
/*      */   private List<CmdFmtDefElement> cmdFmtDefs;
/*   41 */   private HashMap<String, CommandElement> cmdNameMap = new HashMap<>();
/*   42 */   private HashMap<Integer, CommandElement> c2tCmdIdMap = new HashMap<>();
/*   43 */   private HashMap<Integer, CommandElement> t2cCmdIdMap = new HashMap<>();
/*   44 */   private HashMap<Integer, CommandIdMeta> cmdIdMetaMap = new HashMap<>();
/*   45 */   private HashMap<String, ParamsElement> globalParamsMap = new HashMap<>();
/*   46 */   private HashMap<String, CmdBindingMeta> cmdBindingMetaMap = new HashMap<>();
/*   47 */   private HashMap<Class<?>, Method> marshalHelperMethodMap = new HashMap<>();
/*      */ 
/*      */   
/*      */   private Class marshalHelperClass;
/*      */ 
/*      */ 
/*      */   
/*      */   static class CmdBindingMeta
/*      */   {
/*      */     HashMap<String, CmdBindingMeta> listItemBindingMetaMap;
/*      */     
/*      */     Field cmdRef;
/*      */     
/*      */     Class<?> bindingClass;
/*      */     
/*      */     ParamsElement params;
/*      */     
/*      */     HashMap<String, Field> fieldMap;
/*      */   }
/*      */ 
/*      */   
/*      */   static class CommandIdMeta
/*      */   {
/*      */     HashMap<Integer, CommandIdMeta> extCmdIdMap;
/*      */     
/*      */     CommandElement c2tCommand;
/*      */     
/*      */     CommandElement t2cCommand;
/*      */   }
/*      */ 
/*      */   
/*      */   public CmdProtMarshaller(CmdFmtDefElement cmdFmtDef) throws CommandFormatDefinitionException {
/*   79 */     this(Arrays.asList(new CmdFmtDefElement[] { cmdFmtDef }, ), true);
/*      */   }
/*      */ 
/*      */   
/*      */   public CmdProtMarshaller(List<CmdFmtDefElement> cmdFmtDefs) throws CommandFormatDefinitionException {
/*   84 */     this(cmdFmtDefs, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CmdProtMarshaller(List<CmdFmtDefElement> cmdFmtDefs, boolean useCmdBindingClass) throws CommandFormatDefinitionException {
/*   92 */     this.cmdFmtDefs = cmdFmtDefs;
/*   93 */     int size = cmdFmtDefs.size();
/*      */     int index;
/*   95 */     for (index = size - 1; index >= 0; index--) {
/*   96 */       CmdFmtDefElement cmdFmtDef = cmdFmtDefs.get(index);
/*   97 */       if (cmdFmtDef.getMarshalHelperClass() != null) {
/*      */         try {
/*   99 */           this.marshalHelperClass = Class.forName(cmdFmtDef.getMarshalHelperClass());
/*  100 */           Method[] methods = this.marshalHelperClass.getMethods();
/*  101 */           for (Method method : methods) {
/*  102 */             Class<?>[] params = method.getParameterTypes();
/*  103 */             Class<?> returnType = method.getReturnType();
/*  104 */             if (method.getName().equals("marshalAnalyze") && params.length == 4 && 
/*  105 */               params[1].isAssignableFrom(String.class) && 
/*  106 */               returnType.isAssignableFrom(AnalysisResult.class)) {
/*  107 */               this.marshalHelperMethodMap.put(params[0], method);
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*  112 */         catch (ClassNotFoundException e) {
/*  113 */           throw new CommandFormatDefinitionException("secondaryDecoderClass not found", e);
/*      */         } 
/*      */       }
/*  116 */       if (cmdFmtDef.getGlobalParams() != null) {
/*  117 */         for (ParamsElement params : cmdFmtDef.getGlobalParams().getParams()) {
/*  118 */           if (this.globalParamsMap.get(params.getName()) != null)
/*  119 */             throw new CommandFormatDefinitionException("Repeated params name: " + params.getName()); 
/*  120 */           this.globalParamsMap.put(params.getName(), params);
/*      */         } 
/*      */ 
/*      */         
/*  124 */         for (ParamsElement params : cmdFmtDef.getGlobalParams().getParams()) {
/*  125 */           parsingParams(params.getValueOrByteArrayOrBitArray(), true);
/*      */         }
/*      */       } 
/*      */       
/*  129 */       for (CommandElement command : cmdFmtDef.getCommand()) {
/*  130 */         int cmdIdByte1; String cmdName = command.getName();
/*      */         
/*  132 */         if (this.cmdNameMap.get(cmdName) != null) {
/*  133 */           throw new CommandFormatDefinitionException("Repeated command name: " + command.getName());
/*      */         }
/*  135 */         this.cmdNameMap.put(cmdName, command);
/*      */         
/*  137 */         parsingParams(command.getValueOrByteArrayOrBitArray(), true);
/*      */         
/*  139 */         int cmdId = command.getId().intValue();
/*      */         
/*  141 */         if (cmdId > 255) { cmdIdByte1 = cmdId >> 8; }
/*  142 */         else { cmdIdByte1 = cmdId; }
/*  143 */          CommandIdMeta cm = this.cmdIdMetaMap.get(Integer.valueOf(cmdIdByte1));
/*  144 */         if (cm == null) {
/*  145 */           cm = new CommandIdMeta();
/*  146 */           this.cmdIdMetaMap.put(Integer.valueOf(cmdIdByte1), cm);
/*      */         } 
/*      */         
/*  149 */         if (cmdId > 255) {
/*  150 */           if (cm.extCmdIdMap == null) cm.extCmdIdMap = new HashMap<>(); 
/*  151 */           CommandIdMeta subCm = cm.extCmdIdMap.get(Integer.valueOf(cmdId));
/*  152 */           if (subCm == null) {
/*  153 */             subCm = new CommandIdMeta();
/*  154 */             cm.extCmdIdMap.put(Integer.valueOf(cmdId), subCm);
/*      */           } 
/*  156 */           DirectionType directionType = command.getDirection();
/*      */           
/*  158 */           if (directionType == null || command.getDirection() == DirectionType.C_2_T) {
/*  159 */             if (subCm.c2tCommand != null) {
/*  160 */               throw new CommandFormatDefinitionException("Repeated command, name: " + command
/*      */                   
/*  162 */                   .getName() + ", id: " + 
/*      */                   
/*  164 */                   Integer.toHexString(cmdId) + ", direction: C2T");
/*      */             }
/*  166 */             subCm.c2tCommand = command;
/*  167 */             this.c2tCmdIdMap.put(Integer.valueOf(cmdId), command);
/*      */           } else {
/*  169 */             if (subCm.t2cCommand != null) {
/*  170 */               throw new CommandFormatDefinitionException("Repeated command, name: " + command
/*      */                   
/*  172 */                   .getName() + ", id: " + 
/*      */                   
/*  174 */                   Integer.toHexString(cmdId) + ", direction: T2C");
/*      */             }
/*  176 */             subCm.t2cCommand = command;
/*  177 */             this.t2cCmdIdMap.put(Integer.valueOf(cmdId), command);
/*      */           } 
/*      */         } else {
/*  180 */           DirectionType directionType = command.getDirection();
/*      */           
/*  182 */           if (directionType == null || command.getDirection() == DirectionType.C_2_T) {
/*  183 */             if (cm.c2tCommand != null && !command.isOverride()) {
/*  184 */               throw new CommandFormatDefinitionException("Repeated command, name: " + command
/*      */                   
/*  186 */                   .getName() + ", id: " + 
/*      */                   
/*  188 */                   Integer.toHexString(cmdId) + ", direction: C2T");
/*      */             }
/*  190 */             cm.c2tCommand = command;
/*  191 */             this.c2tCmdIdMap.put(Integer.valueOf(cmdId), command);
/*      */           } else {
/*  193 */             if (cm.t2cCommand != null && !command.isOverride()) {
/*  194 */               throw new CommandFormatDefinitionException("Repeated command, name: " + command
/*      */                   
/*  196 */                   .getName() + ", id: " + 
/*      */                   
/*  198 */                   Integer.toHexString(cmdId) + ", direction: T2C");
/*      */             }
/*  200 */             cm.t2cCommand = command;
/*  201 */             this.t2cCmdIdMap.put(Integer.valueOf(cmdId), command);
/*      */           } 
/*      */         } 
/*  204 */         if (logger.isTraceEnabled())
/*  205 */           logger.trace("Parsing command, name: {}, id: {}", command
/*  206 */               .getName(), Integer.toHexString(cmdId)); 
/*      */       } 
/*      */     } 
/*  209 */     if (useCmdBindingClass) {
/*  210 */       for (index = size - 1; index >= 0; index--) {
/*  211 */         scanCmdBindingClasses(((CmdFmtDefElement)cmdFmtDefs.get(index)).getBindPackage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void scanCmdBindingClasses(String bindPackage) throws CommandFormatDefinitionException {
/*  226 */     Reflections reflections = new Reflections(bindPackage, new org.reflections.scanners.Scanner[0]);
/*      */     
/*  228 */     Set<Class<?>> paramsBindingClassSet = reflections.getTypesAnnotatedWith(GlobalParams.class);
/*  229 */     for (Class<?> paramsBindingClass : paramsBindingClassSet) {
/*      */       
/*  231 */       if (!bindPackage.equals(paramsBindingClass.getPackage().getName()))
/*  232 */         continue;  String paramsName = ((GlobalParams)paramsBindingClass.<GlobalParams>getAnnotation(GlobalParams.class)).paramsName();
/*  233 */       CmdBindingMeta cmdBindingMeta = new CmdBindingMeta();
/*  234 */       cmdBindingMeta.bindingClass = paramsBindingClass;
/*  235 */       cmdBindingMeta.fieldMap = new HashMap<>();
/*      */       CmdBindingMeta cmdBindingMeta1;
/*  237 */       if ((cmdBindingMeta1 = this.cmdBindingMetaMap.put(paramsName, cmdBindingMeta)) != null) {
/*  238 */         throw new CommandFormatDefinitionException("Duplicate parameter name, " + cmdBindingMeta1.bindingClass + ", " + cmdBindingMeta.bindingClass);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  244 */       for (Field f : paramsBindingClass.getDeclaredFields()) {
/*  245 */         scanGlobalParamsField(cmdBindingMeta, f);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  250 */     Set<Class<?>> cmdBindingClassSet = reflections.getTypesAnnotatedWith(CommandParams.class);
/*  251 */     for (Class<?> cmdBindingClass : cmdBindingClassSet) {
/*      */       
/*  253 */       if (!bindPackage.equals(cmdBindingClass.getPackage().getName()))
/*  254 */         continue;  String cmdName = ((CommandParams)cmdBindingClass.<CommandParams>getAnnotation(CommandParams.class)).cmdName();
/*  255 */       if (cmdName.length() == 0) cmdName = cmdBindingClass.getSimpleName(); 
/*  256 */       if (this.cmdNameMap.get(cmdName) == null) {
/*  257 */         logger.warn("Unknown command name {}, skip {} class", cmdName, cmdBindingClass);
/*      */         continue;
/*      */       } 
/*  260 */       CmdBindingMeta cmdBindingMeta = new CmdBindingMeta();
/*  261 */       cmdBindingMeta.bindingClass = cmdBindingClass;
/*  262 */       cmdBindingMeta.fieldMap = new HashMap<>();
/*      */       CmdBindingMeta cmdBindingMeta1;
/*  264 */       if ((cmdBindingMeta1 = this.cmdBindingMetaMap.put(cmdName, cmdBindingMeta)) != null) {
/*  265 */         throw new CommandFormatDefinitionException("Duplicate parameter name, " + cmdBindingMeta1.bindingClass + ", " + cmdBindingMeta.bindingClass);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  271 */       for (Field f : cmdBindingClass.getDeclaredFields()) {
/*  272 */         scanCommandParamsField(cmdBindingMeta, f);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void scanGlobalParamsField(CmdBindingMeta cmdBindingMeta, Field field) {
/*  278 */     CommandParam commandParam = field.<CommandParam>getAnnotation(CommandParam.class);
/*  279 */     if (commandParam == null)
/*  280 */       return;  String paramName = commandParam.name();
/*  281 */     if (paramName.length() == 0) paramName = field.getName(); 
/*  282 */     cmdBindingMeta.fieldMap.put(paramName, field);
/*  283 */     if (!List.class.isAssignableFrom(field.getType()))
/*  284 */       return;  if (cmdBindingMeta.listItemBindingMetaMap == null)
/*  285 */       cmdBindingMeta.listItemBindingMetaMap = new HashMap<>(); 
/*  286 */     CmdBindingMeta listCmdBindingMeta = new CmdBindingMeta();
/*      */     
/*  288 */     listCmdBindingMeta
/*  289 */       .bindingClass = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/*      */     
/*  291 */     listCmdBindingMeta.fieldMap = new HashMap<>();
/*  292 */     cmdBindingMeta.listItemBindingMetaMap.put(paramName, listCmdBindingMeta);
/*  293 */     for (Field lf : listCmdBindingMeta.bindingClass.getDeclaredFields()) {
/*  294 */       scanGlobalParamsField(listCmdBindingMeta, lf);
/*      */     }
/*      */   }
/*      */   
/*      */   private void scanCommandParamsField(CmdBindingMeta cmdBindingMeta, Field field) {
/*  299 */     CommandParam commandParam = field.<CommandParam>getAnnotation(CommandParam.class);
/*  300 */     if (commandParam == null)
/*      */       return; 
/*  302 */     if (!List.class.isAssignableFrom(field.getType())) {
/*  303 */       if (commandParam.cmdRef()) {
/*  304 */         cmdBindingMeta.cmdRef = field;
/*      */       } else {
/*  306 */         String str = commandParam.name();
/*  307 */         if (str.length() == 0) str = field.getName(); 
/*  308 */         cmdBindingMeta.fieldMap.put(str, field);
/*      */       } 
/*      */       return;
/*      */     } 
/*  312 */     if (cmdBindingMeta.listItemBindingMetaMap == null) {
/*  313 */       cmdBindingMeta.listItemBindingMetaMap = new HashMap<>();
/*      */     }
/*  315 */     CmdBindingMeta listCmdBindingMeta = new CmdBindingMeta();
/*      */     
/*  317 */     listCmdBindingMeta
/*  318 */       .bindingClass = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/*      */     
/*  320 */     listCmdBindingMeta.fieldMap = new HashMap<>();
/*  321 */     String paramName = commandParam.name();
/*  322 */     if (paramName.length() == 0) paramName = field.getName(); 
/*  323 */     cmdBindingMeta.listItemBindingMetaMap.put(paramName, listCmdBindingMeta);
/*  324 */     cmdBindingMeta.fieldMap.put(paramName, field);
/*  325 */     for (Field lf : listCmdBindingMeta.bindingClass.getDeclaredFields()) {
/*  326 */       scanCommandParamsField(listCmdBindingMeta, lf);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkNameUnique(String name, Set<String> nameSet) throws CommandFormatDefinitionException {
/*  332 */     if (name == null)
/*  333 */       return;  if (nameSet.contains(name))
/*  334 */       throw new CommandFormatDefinitionException("Repeated parameter name: " + name); 
/*  335 */     nameSet.add(name);
/*      */   }
/*      */ 
/*      */   
/*      */   private void parsingParams(List<XmlElement> paramList, boolean checkNameUnique) throws CommandFormatDefinitionException {
/*  340 */     parsingParams(paramList, checkNameUnique ? new HashSet<>() : null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parsingParams(List<XmlElement> paramList, HashSet<String> nameSet) throws CommandFormatDefinitionException {
/*  355 */     boolean checkNameUnique = !(nameSet == null);
/*  356 */     for (XmlElement param : paramList) {
/*  357 */       if (param instanceof ValueElement) {
/*  358 */         param.elType = XmlElement.ElementType.VALUE;
/*      */         
/*  360 */         ValueElement value = (ValueElement)param;
/*  361 */         param.id = value.name;
/*  362 */         if (value.isUseMarshalHelper() != null && value.isUseMarshalHelper().booleanValue())
/*  363 */           param.useMarshalHelper = true; 
/*  364 */         if (checkNameUnique) checkNameUnique(value.getName(), nameSet);  continue;
/*  365 */       }  if (param instanceof ByteArrayElement) {
/*  366 */         param.elType = XmlElement.ElementType.BYTE_ARRAY;
/*  367 */         ByteArrayElement byteArray = (ByteArrayElement)param;
/*  368 */         param.id = byteArray.name;
/*  369 */         if (byteArray.isUseMarshalHelper() != null && byteArray.isUseMarshalHelper().booleanValue())
/*  370 */           param.useMarshalHelper = true; 
/*  371 */         if (checkNameUnique) checkNameUnique(byteArray.getName(), nameSet);  continue;
/*  372 */       }  if (param instanceof BitArrayElement) {
/*  373 */         param.elType = XmlElement.ElementType.BIT_ARRAY;
/*  374 */         BitArrayElement bitArray = (BitArrayElement)param;
/*  375 */         param.id = bitArray.name;
/*  376 */         if (bitArray.isUseMarshalHelper() != null && bitArray.isUseMarshalHelper().booleanValue())
/*  377 */           param.useMarshalHelper = true; 
/*  378 */         if (checkNameUnique) {
/*  379 */           checkNameUnique(bitArray.getName(), nameSet);
/*  380 */           for (BitArrayElement.Bits b : bitArray.getBits())
/*  381 */             checkNameUnique(b.getName(), nameSet); 
/*      */         }  continue;
/*      */       } 
/*  384 */       if (param instanceof FixBytesElement) {
/*  385 */         param.elType = XmlElement.ElementType.FIX_VALUE;
/*  386 */         FixBytesElement fixBytes = (FixBytesElement)param;
/*  387 */         param.id = fixBytes.name;
/*  388 */         if (fixBytes.isUseMarshalHelper() != null && fixBytes.isUseMarshalHelper().booleanValue())
/*  389 */           param.useMarshalHelper = true; 
/*  390 */         if (checkNameUnique) checkNameUnique(fixBytes.getName(), nameSet);  continue;
/*  391 */       }  if (param instanceof ListElement) {
/*  392 */         param.elType = XmlElement.ElementType.LIST;
/*  393 */         ListElement list = (ListElement)param;
/*  394 */         param.id = list.name;
/*  395 */         if (list.isUseMarshalHelper() != null && list.isUseMarshalHelper().booleanValue())
/*  396 */           param.useMarshalHelper = true; 
/*  397 */         List<XmlElement> listParamList = list.getValueOrByteArrayOrBitArray();
/*  398 */         parsingParams(listParamList, nameSet); continue;
/*  399 */       }  if (param instanceof Ct3VehicleListElement) {
/*  400 */         param.elType = XmlElement.ElementType.CT3_VEHICLE_LIST;
/*  401 */         Ct3VehicleListElement ct3VehicleList = (Ct3VehicleListElement)param;
/*  402 */         param.id = ct3VehicleList.name;
/*  403 */         if (ct3VehicleList.isUseMarshalHelper() != null && ct3VehicleList.isUseMarshalHelper().booleanValue())
/*  404 */           param.useMarshalHelper = true; 
/*  405 */         List<XmlElement> vListParamList = ct3VehicleList.getValueOrByteArrayOrBitArray();
/*  406 */         parsingParams(vListParamList, nameSet); continue;
/*  407 */       }  if (param instanceof ParamsRefElement) {
/*  408 */         param.elType = XmlElement.ElementType.PARAMS_REF;
/*  409 */         ParamsRefElement paramsRef = (ParamsRefElement)param;
/*  410 */         param.id = paramsRef.paramsName;
/*  411 */         if (paramsRef.isUseMarshalHelper() != null && paramsRef.isUseMarshalHelper().booleanValue())
/*  412 */           param.useMarshalHelper = true; 
/*  413 */         ParamsElement paramsElement = this.globalParamsMap.get(paramsRef.getParamsName());
/*  414 */         if (paramsElement == null) {
/*  415 */           throw new CommandFormatDefinitionException("Unknown params name: " + paramsRef
/*  416 */               .getParamsName());
/*      */         }
/*  418 */         XmlElement xmlElement = paramsElement.getValueOrByteArrayOrBitArray().get(0);
/*      */         
/*  420 */         if (xmlElement.elType == null)
/*  421 */           parsingParams(paramsElement.getValueOrByteArrayOrBitArray(), nameSet);  continue;
/*      */       } 
/*  423 */       if (param instanceof CmdParamsRefElement) {
/*  424 */         param.elType = XmlElement.ElementType.CMD_PARAMS_REF;
/*  425 */         CmdParamsRefElement cmdParamsRef = (CmdParamsRefElement)param;
/*  426 */         param.id = "cmdParamsRef";
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CommandElement getCommandElement(Object cmdBindingObj) throws Exception {
/*  433 */     CommandParams commandParams = cmdBindingObj.getClass().<CommandParams>getAnnotation(CommandParams.class);
/*  434 */     CommandElement command = this.cmdNameMap.get(commandParams.cmdName());
/*  435 */     if (command == null) {
/*  436 */       throw new Exception("Unkonwn command name: " + commandParams.cmdName());
/*      */     }
/*  438 */     return command;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void marshal(Object cmdBindingObj, CommandDataInject commandDataInject) throws Exception {
/*  450 */     if (!(cmdBindingObj instanceof Map)) {
/*      */ 
/*      */       
/*  453 */       CommandParams commandParams = cmdBindingObj.getClass().<CommandParams>getAnnotation(CommandParams.class);
/*  454 */       CommandElement command = this.cmdNameMap.get(commandParams.cmdName());
/*  455 */       if (command == null) {
/*  456 */         throw new Exception("Unkonwn command name: " + commandParams.cmdName());
/*      */       }
/*  458 */       commandDataInject.setCmdDef(command);
/*  459 */       CmdBindingMeta cmdBindingMeta = this.cmdBindingMetaMap.get(commandParams.cmdName());
/*  460 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  461 */       DataOutputStream dos = new DataOutputStream(baos);
/*  462 */       int cmdId = command.getId().intValue();
/*  463 */       if (cmdId <= 255) { dos.write(cmdId); }
/*  464 */       else { dos.writeShort(cmdId); }
/*  465 */        marshalParams(cmdBindingObj, cmdBindingMeta, dos, command.getValueOrByteArrayOrBitArray());
/*  466 */       InputStream is = new ByteArrayInputStream(baos.toByteArray());
/*  467 */       commandDataInject.setEncodeData(is);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void marshalParams(Object cmdBindingObj, CmdBindingMeta cmdBindingMeta, DataOutputStream dos, List<XmlElement> paramList) throws Exception {
/*  477 */     HashMap<String, Field> fieldMap = cmdBindingMeta.fieldMap;
/*  478 */     for (XmlElement param : paramList) {
/*  479 */       ValueElement v; ByteArrayElement byteArray; BitArrayElement bitArray; FixBytesElement fixBytes; ListElement list; ParamsRefElement paramRef; Object refCmdBindingObj; Long y; byte[] d; long bitsData; List<?> listParamObj; ParamsElement params; CommandParams commandParams; String s; Integer lenByteNo; int itemNo; CmdBindingMeta paramBindingMeta; CommandElement refCommand; int cmdId; Integer len; int fromIndex, j; Object paramsObj; CmdBindingMeta refCmdBindingMeta; int no, i; CmdBindingMeta listCmdParamsMeta; int k, m; AnalysisResult analysisResult = null;
/*  480 */       if (param.useMarshalHelper) {
/*  481 */         Method method = this.marshalHelperMethodMap.get(cmdBindingObj.getClass());
/*  482 */         if (method == null)
/*  483 */           throw new NullPointerException("The marshal helper method for " + cmdBindingObj
/*  484 */               .getClass().getName() + " not found"); 
/*  485 */         analysisResult = (AnalysisResult)method.invoke(null, new Object[] { cmdBindingObj, param.id, Boolean.valueOf(true), Integer.valueOf(0) });
/*  486 */         if (analysisResult == null || !analysisResult.isProcessing())
/*      */           continue; 
/*  488 */       }  switch (param.elType) {
/*      */         
/*      */         case VALUE:
/*  491 */           v = (ValueElement)param;
/*  492 */           switch (v.getType()) {
/*      */             case VALUE:
/*  494 */               dos.writeByte(((Integer)((Field)fieldMap.get(v.getName())).get(cmdBindingObj)).intValue());
/*      */             
/*      */             case BYTE_ARRAY:
/*  497 */               dos.writeBoolean(((Boolean)((Field)fieldMap.get(v.getName())).get(cmdBindingObj)).booleanValue());
/*      */             
/*      */             case BIT_ARRAY:
/*  500 */               dos.writeShort(((Integer)((Field)fieldMap.get(v.getName())).get(cmdBindingObj)).intValue());
/*      */             
/*      */             case FIX_VALUE:
/*  503 */               y = (Long)((Field)fieldMap.get(v.getName())).get(cmdBindingObj);
/*  504 */               dos.writeInt((int)(y.longValue() & 0xFFFFFFFFFFFFFFFFL));
/*      */             
/*      */             case LIST:
/*  507 */               dos.writeFloat(((Float)((Field)fieldMap.get(v.getName())).get(cmdBindingObj)).floatValue());
/*      */             
/*      */             case CT3_VEHICLE_LIST:
/*  510 */               s = (String)((Field)fieldMap.get(v.getName())).get(cmdBindingObj);
/*  511 */               if (s == null) s = ""; 
/*  512 */               dos.writeUTF(s);
/*      */ 
/*      */             
/*      */             case PARAMS_REF:
/*  516 */               cmdId = ((Integer)((Field)fieldMap.get(v.getName())).get(cmdBindingObj)).intValue();
/*  517 */               if (cmdId <= 255) { dos.writeByte(cmdId); continue; }
/*  518 */                dos.writeShort(cmdId);
/*      */           } 
/*      */ 
/*      */         
/*      */         
/*      */ 
/*      */         
/*      */         case BYTE_ARRAY:
/*  526 */           byteArray = (ByteArrayElement)param;
/*  527 */           d = (byte[])((Field)fieldMap.get(byteArray.getName())).get(cmdBindingObj);
/*  528 */           lenByteNo = byteArray.getLenByteNo();
/*  529 */           if (lenByteNo != null && lenByteNo.intValue() > 0) {
/*  530 */             if (d == null) d = new byte[0]; 
/*  531 */             int byteNo = d.length;
/*  532 */             for (int n = lenByteNo.intValue() - 1; n >= 0; n--) {
/*  533 */               dos.write(byteNo >> n * 8 & 0xFF);
/*      */             }
/*      */             
/*  536 */             dos.write(d); continue;
/*      */           } 
/*  538 */           len = byteArray.getLength();
/*  539 */           if (len == null || len.intValue() == 0) {
/*  540 */             if (d != null) dos.write(d);  continue;
/*      */           } 
/*  542 */           if (d == null) d = new byte[byteArray.getLength().intValue()];
/*      */           
/*  544 */           if (d.length != len.intValue()) {
/*  545 */             byte[] d1 = new byte[len.intValue()];
/*  546 */             for (int n = 0; n < d.length && n < d1.length; n++) {
/*  547 */               d1[n] = d[n];
/*      */             }
/*  549 */             d = d1;
/*      */           } 
/*  551 */           dos.write(d);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case BIT_ARRAY:
/*  558 */           bitArray = (BitArrayElement)param;
/*  559 */           bitsData = 0L;
/*      */           
/*  561 */           fromIndex = -1;
/*  562 */           for (BitArrayElement.Bits b : bitArray.getBits()) {
/*  563 */             int n = ((Integer)((Field)fieldMap.get(b.getName())).get(cmdBindingObj)).intValue();
/*  564 */             int i1 = b.getBitsLenth();
/*  565 */             fromIndex += i1;
/*  566 */             bitsData = BytesUtility.setBitsValue(bitsData, fromIndex, i1, n);
/*      */           } 
/*  568 */           no = bitArray.getByteNo();
/*  569 */           for (m = no - 1; m >= 0; m--) {
/*  570 */             dos.write((int)(bitsData >> m * 8 & 0xFFL));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case FIX_VALUE:
/*  577 */           fixBytes = (FixBytesElement)param;
/*  578 */           dos.write(fixBytes.getData());
/*      */ 
/*      */ 
/*      */         
/*      */         case LIST:
/*  583 */           list = (ListElement)param;
/*  584 */           listParamObj = (List)((Field)fieldMap.get(list.getName())).get(cmdBindingObj);
/*  585 */           itemNo = listParamObj.size();
/*  586 */           j = list.getLenByteNo();
/*  587 */           for (i = j - 1; i >= 0; i--) {
/*  588 */             dos.write(itemNo >> i * 8 & 0xFF);
/*      */           }
/*      */           
/*  591 */           listCmdParamsMeta = cmdBindingMeta.listItemBindingMetaMap.get(list.getName());
/*  592 */           for (m = 0; m < itemNo; m++) {
/*  593 */             marshalParams(listParamObj
/*  594 */                 .get(m), listCmdParamsMeta, dos, list
/*      */ 
/*      */                 
/*  597 */                 .getValueOrByteArrayOrBitArray());
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         case CT3_VEHICLE_LIST:
/*  603 */           throw new NotImplementedException("ct3 vehicle marshal not implement!");
/*      */ 
/*      */         
/*      */         case PARAMS_REF:
/*  607 */           paramRef = (ParamsRefElement)param;
/*  608 */           params = this.globalParamsMap.get(paramRef.getParamsName());
/*  609 */           paramBindingMeta = this.cmdBindingMetaMap.get(params.getName());
/*  610 */           paramsObj = ((Field)fieldMap.get(paramRef.getParamsName())).get(cmdBindingObj);
/*  611 */           if (paramsObj == null) {
/*  612 */             throw new NullPointerException("The value of " + paramRef
/*  613 */                 .getParamsName() + " is null.");
/*      */           }
/*  615 */           marshalParams(paramsObj, paramBindingMeta, dos, params.getValueOrByteArrayOrBitArray());
/*      */ 
/*      */ 
/*      */         
/*      */         case CMD_PARAMS_REF:
/*  620 */           refCmdBindingObj = cmdBindingMeta.cmdRef.get(cmdBindingObj);
/*      */           
/*  622 */           commandParams = refCmdBindingObj.getClass().<CommandParams>getAnnotation(CommandParams.class);
/*  623 */           refCommand = this.cmdNameMap.get(commandParams.cmdName());
/*  624 */           refCmdBindingMeta = this.cmdBindingMetaMap.get(commandParams.cmdName());
/*  625 */           k = refCommand.getId().intValue();
/*  626 */           if (k <= 255) { dos.write(k); }
/*  627 */           else { dos.writeShort(k); }
/*  628 */            marshalParams(refCmdBindingObj, refCmdBindingMeta, dos, refCommand
/*      */ 
/*      */ 
/*      */               
/*  632 */               .getValueOrByteArrayOrBitArray());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unmarshal(CommandDataInject commandDataInject, byte[] data, int offset, int length) throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException {
/*  652 */     unmarshal(null, commandDataInject, data, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unmarshal(DirectionType directionType, CommandDataInject commandDataInject, byte[] data, int offset, int length) throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException {
/*      */     try {
/*      */       CommandElement command;
/*  665 */       int cmdId = data[offset] & 0xFF;
/*  666 */       offset++;
/*  667 */       length--;
/*  668 */       CommandIdMeta commandIdMeta = this.cmdIdMetaMap.get(Integer.valueOf(cmdId));
/*  669 */       if (commandIdMeta == null)
/*  670 */         throw new CommandDefinitionException("Unknown command id: " + Integer.toHexString(cmdId)); 
/*  671 */       if (commandIdMeta.extCmdIdMap != null) {
/*  672 */         cmdId = (cmdId << 8) + (data[offset] & 0xFF);
/*  673 */         offset++;
/*  674 */         length--;
/*  675 */         commandIdMeta = commandIdMeta.extCmdIdMap.get(Integer.valueOf(cmdId));
/*  676 */         if (commandIdMeta == null) {
/*  677 */           throw new CommandDefinitionException("Unknown command id: " + Integer.toHexString(cmdId));
/*      */         }
/*      */       } 
/*  680 */       if (directionType == null || directionType == DirectionType.C_2_T)
/*  681 */       { command = commandIdMeta.c2tCommand; }
/*  682 */       else { command = commandIdMeta.t2cCommand; }
/*      */       
/*  684 */       commandDataInject.setCmdDef(command);
/*  685 */       InputStream is = new ByteArrayInputStream(data, offset, length);
/*  686 */       DataInputStream dis = new DataInputStream(is);
/*  687 */       CmdBindingMeta cmdBindingMeta = this.cmdBindingMetaMap.get(command.getName());
/*  688 */       List<XmlElement> paramList = command.getValueOrByteArrayOrBitArray();
/*      */       
/*  690 */       if (cmdBindingMeta != null) {
/*  691 */         Object cmdBindingObj = cmdBindingMeta.bindingClass.newInstance();
/*  692 */         unmarshalParams(dis, paramList, cmdBindingMeta, cmdBindingObj, new HashMap<>());
/*  693 */         commandDataInject.setCmdBindingObj(cmdBindingObj);
/*      */       } 
/*      */       
/*  696 */       if (is.available() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  701 */         String errMsg = "Command params has been unmarshaled completely, but " + is.available() + " bytes are remaining, message data: " + BytesUtility.toHexString(data);
/*  702 */         logger.warn(errMsg);
/*  703 */         throw new CommandFormatException(errMsg);
/*      */       } 
/*  705 */     } catch (CommandDefinitionException ex) {
/*  706 */       throw ex;
/*  707 */     } catch (IllegalParamException ex) {
/*  708 */       throw ex;
/*  709 */     } catch (CommandFormatException ex) {
/*  710 */       throw ex;
/*  711 */     } catch (EOFException ex) {
/*  712 */       String errMsg = "Command params was not unmarshaled completely, there are no more bytes for reading";
/*      */       
/*  714 */       logger.warn(errMsg);
/*  715 */       throw new CommandFormatException(ex);
/*  716 */     } catch (Throwable t) {
/*  717 */       throw new DecodeException("Decode failed!", t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unmarshal(DirectionType directionType, CommandDataInject commandDataInject, int commandId, byte[] data, int offset, int length) throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException {
/*      */     try {
/*      */       CommandElement command;
/*  742 */       if (directionType == null || directionType == DirectionType.C_2_T)
/*  743 */       { command = this.c2tCmdIdMap.get(Integer.valueOf(commandId)); }
/*  744 */       else { command = this.t2cCmdIdMap.get(Integer.valueOf(commandId)); }
/*  745 */        if (command == null) {
/*  746 */         throw new CommandDefinitionException("Unknown command id: " + 
/*  747 */             Integer.toHexString(commandId));
/*      */       }
/*  749 */       commandDataInject.setCmdDef(command);
/*  750 */       InputStream is = new ByteArrayInputStream(data, offset, length);
/*  751 */       DataInputStream dis = new DataInputStream(is);
/*  752 */       CmdBindingMeta cmdBindingMeta = this.cmdBindingMetaMap.get(command.getName());
/*  753 */       List<XmlElement> paramList = command.getValueOrByteArrayOrBitArray();
/*      */       
/*  755 */       if (cmdBindingMeta != null) {
/*  756 */         Object cmdBindingObj = cmdBindingMeta.bindingClass.newInstance();
/*  757 */         unmarshalParams(dis, paramList, cmdBindingMeta, cmdBindingObj, new HashMap<>());
/*  758 */         commandDataInject.setCmdBindingObj(cmdBindingObj);
/*      */       } 
/*      */       
/*  761 */       if (is.available() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  766 */         String errMsg = "Command params has been unmarshaled completely, but " + is.available() + " bytes are remaining, message data: " + BytesUtility.toHexString(data);
/*  767 */         logger.warn(errMsg);
/*  768 */         throw new CommandFormatException(errMsg);
/*      */       } 
/*  770 */     } catch (CommandDefinitionException ex) {
/*  771 */       throw ex;
/*  772 */     } catch (IllegalParamException ex) {
/*  773 */       throw ex;
/*  774 */     } catch (CommandFormatException ex) {
/*  775 */       throw ex;
/*  776 */     } catch (EOFException ex) {
/*  777 */       String errMsg = "Command params was not unmarshaled completely, there are no more bytes for reading";
/*      */       
/*  779 */       logger.warn(errMsg);
/*  780 */       throw new CommandFormatException(ex);
/*  781 */     } catch (Throwable t) {
/*  782 */       throw new DecodeException("Decode failed!", t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object unmarshalParams(DataInputStream dis, List<XmlElement> paramList, CmdBindingMeta cmdBindingMeta, Object cmdBindingObj, Map<String, Integer> lengthMap) throws Exception {
/*  793 */     HashMap<String, Field> fieldMap = cmdBindingMeta.fieldMap;
/*  794 */     int lengthOfNextList = -1;
/*  795 */     int lengthOfNextByteArray = -1;
/*  796 */     for (XmlElement param : paramList) {
/*  797 */       ValueElement ve; ByteArrayElement byteArray; BitArrayElement bitArray; FixBytesElement fixBytes; ListElement list; Ct3VehicleListElement vlist; ParamsRefElement paramRef; CmdParamsRefElement cmdParamsRef; Boolean b; byte[] d; long itemNo; byte[] noBytes; CmdBindingMeta paramBindingMeta; int cmdId, v; Integer lenByteNo; long bitsData; int vehicleNo; Object paramsBindingObj; CommandIdMeta cm; long v1; CmdBindingMeta listItemBindingMeta; int vehicleLen; ParamsElement params; CommandElement refCommand; int fromIndex; List<Object> pl; CmdBindingMeta listBindingMeta, refCmdBindingMeta; int k, i; ParamsRefElement paramsRefElement1; Object refCmdBindingObj; CommandIdMeta commandIdMeta1; CmdBindingMeta cmdBindingMeta1; ParamsElement paramsElement1; List<XmlElement> listParamsList, newListParamList; int j; List<Object> list1; int m; AnalysisResult analysisResult = null;
/*  798 */       if (param.useMarshalHelper) {
/*  799 */         Method method = this.marshalHelperMethodMap.get(cmdBindingObj.getClass());
/*  800 */         if (method == null) {
/*  801 */           throw new NullPointerException("The marshal helper method for " + cmdBindingObj
/*  802 */               .getClass().getName() + " not found");
/*      */         }
/*  804 */         analysisResult = (AnalysisResult)method.invoke(null, new Object[] { cmdBindingObj, param.id, Boolean.valueOf(false), Integer.valueOf(dis.available()) });
/*  805 */         if (analysisResult == null || !analysisResult.isProcessing())
/*      */           continue; 
/*  807 */       }  switch (param.elType) {
/*      */         
/*      */         case VALUE:
/*  810 */           ve = (ValueElement)param;
/*      */           
/*  812 */           if ((b = ve.isOptional()) != null && b.booleanValue() && 
/*  813 */             dis.available() <= 0) return cmdBindingObj;
/*      */ 
/*      */           
/*  816 */           switch (ve.getType()) {
/*      */             case VALUE:
/*  818 */               v = dis.readUnsignedByte();
/*  819 */               if (ve.isLengthOfNextList()) lengthOfNextList = v; 
/*  820 */               if (ve.isLengthOfNextByteArray()) lengthOfNextByteArray = v; 
/*  821 */               if (ve.isLengthField()) lengthMap.put(ve.getName(), Integer.valueOf(v)); 
/*  822 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Integer.valueOf(v));
/*      */             
/*      */             case BYTE_ARRAY:
/*  825 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Boolean.valueOf(dis.readBoolean()));
/*      */             
/*      */             case BIT_ARRAY:
/*  828 */               v = dis.readUnsignedShort();
/*  829 */               if (ve.isLengthOfNextList()) lengthOfNextList = v; 
/*  830 */               if (ve.isLengthOfNextByteArray()) lengthOfNextByteArray = v; 
/*  831 */               if (ve.isLengthField()) lengthMap.put(ve.getName(), Integer.valueOf(v)); 
/*  832 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Integer.valueOf(v));
/*      */             
/*      */             case FIX_VALUE:
/*  835 */               v1 = dis.readInt() & 0xFFFFFFFFL;
/*  836 */               if (ve.isLengthOfNextList()) lengthOfNextList = (int)v1 & Integer.MAX_VALUE; 
/*  837 */               if (ve.isLengthOfNextByteArray()) lengthOfNextByteArray = (int)v1 & Integer.MAX_VALUE; 
/*  838 */               if (ve.isLengthField()) lengthMap.put(ve.getName(), Integer.valueOf((int)v1 & Integer.MAX_VALUE)); 
/*  839 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Long.valueOf(v1));
/*      */             
/*      */             case LIST:
/*  842 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Float.valueOf(dis.readFloat()));
/*      */             
/*      */             case CT3_VEHICLE_LIST:
/*  845 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, dis.readUTF());
/*      */ 
/*      */             
/*      */             case PARAMS_REF:
/*  849 */               k = dis.read();
/*  850 */               commandIdMeta1 = this.cmdIdMetaMap.get(Integer.valueOf(k));
/*  851 */               if (commandIdMeta1 == null)
/*  852 */                 throw new IllegalParamException("Unknown command id: " + 
/*  853 */                     Integer.toHexString(k)); 
/*  854 */               if (commandIdMeta1.extCmdIdMap != null) {
/*  855 */                 k = (k << 8) + dis.read();
/*  856 */                 commandIdMeta1 = commandIdMeta1.extCmdIdMap.get(Integer.valueOf(k));
/*  857 */                 if (commandIdMeta1 == null)
/*  858 */                   throw new IllegalParamException("Unknown command id: " + 
/*  859 */                       Integer.toHexString(k)); 
/*      */               } 
/*  861 */               ((Field)fieldMap.get(ve.getName())).set(cmdBindingObj, Integer.valueOf(k));
/*      */           } 
/*      */ 
/*      */         
/*      */         
/*      */ 
/*      */         
/*      */         case BYTE_ARRAY:
/*  869 */           byteArray = (ByteArrayElement)param;
/*  870 */           d = null;
/*  871 */           lenByteNo = byteArray.getLenByteNo();
/*  872 */           if (lenByteNo != null && lenByteNo.intValue() > 0) {
/*  873 */             d = new byte[lenByteNo.intValue()];
/*  874 */             dis.read(d);
/*  875 */             int byteNo = (int)BytesUtility.setBytesToValue(d);
/*  876 */             d = new byte[byteNo];
/*  877 */             dis.read(d);
/*  878 */           } else if (byteArray.getLenFieldName() != null && lengthMap
/*  879 */             .containsKey(byteArray.getLenFieldName())) {
/*  880 */             d = new byte[((Integer)lengthMap.get(byteArray.getLenFieldName())).intValue()];
/*  881 */             dis.read(d);
/*  882 */           } else if (lengthOfNextByteArray >= 0) {
/*  883 */             d = new byte[lengthOfNextByteArray];
/*  884 */             dis.read(d);
/*  885 */             lengthOfNextByteArray = -1;
/*  886 */           } else if (analysisResult != null && analysisResult.getLength() >= 0) {
/*  887 */             d = new byte[analysisResult.getLength()];
/*  888 */             dis.read(d);
/*      */           } else {
/*  890 */             Integer len = byteArray.getLength();
/*  891 */             if (len == null || len
/*  892 */               .intValue() == 0) {
/*  893 */               len = Integer.valueOf(dis.available());
/*      */             }
/*  895 */             d = new byte[len.intValue()];
/*  896 */             dis.read(d);
/*      */           } 
/*      */           
/*  899 */           ((Field)fieldMap.get(byteArray.getName())).set(cmdBindingObj, d);
/*      */ 
/*      */ 
/*      */         
/*      */         case BIT_ARRAY:
/*  904 */           bitArray = (BitArrayElement)param;
/*  905 */           d = new byte[bitArray.getByteNo()];
/*  906 */           dis.read(d);
/*  907 */           bitsData = BytesUtility.setBytesToValue(d);
/*  908 */           fromIndex = -1;
/*  909 */           for (BitArrayElement.Bits bits : bitArray.getBits()) {
/*  910 */             int bl = bits.getBitsLenth();
/*  911 */             fromIndex += bl;
/*  912 */             ((Field)fieldMap
/*  913 */               .get(bits.getName()))
/*  914 */               .set(cmdBindingObj, Integer.valueOf(BytesUtility.getBitsValue(bitsData, fromIndex, bl)));
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         case FIX_VALUE:
/*  920 */           fixBytes = (FixBytesElement)param;
/*  921 */           d = new byte[(fixBytes.getData()).length];
/*  922 */           dis.read(d);
/*  923 */           if (!Arrays.equals(d, fixBytes.getData())) {
/*  924 */             logger.info("Fixed value unequaled, {}, {}", 
/*      */                 
/*  926 */                 BytesUtility.toHexString(fixBytes.getData()), 
/*  927 */                 BytesUtility.toHexString(d));
/*      */           }
/*      */ 
/*      */         
/*      */         case LIST:
/*  932 */           list = (ListElement)param;
/*  933 */           itemNo = 0L;
/*  934 */           if (list.getLenByteNo() > 0) {
/*  935 */             byte[] arrayOfByte = new byte[list.getLenByteNo()];
/*  936 */             dis.read(arrayOfByte);
/*  937 */             itemNo = BytesUtility.setBytesToValue(arrayOfByte);
/*  938 */           } else if (list.getLenFieldName() != null && lengthMap
/*  939 */             .containsKey(list.getLenFieldName())) {
/*  940 */             itemNo = ((Integer)lengthMap.get(list.getLenFieldName())).intValue();
/*  941 */           } else if (lengthOfNextList >= 0) {
/*  942 */             itemNo = lengthOfNextList;
/*  943 */             lengthOfNextList = -1;
/*  944 */           } else if (analysisResult != null && analysisResult.getLength() >= 0) {
/*  945 */             itemNo = analysisResult.getLength();
/*      */           } 
/*      */ 
/*      */           
/*  949 */           listItemBindingMeta = cmdBindingMeta.listItemBindingMetaMap.get(list.getName());
/*  950 */           pl = new ArrayList();
/*  951 */           for (i = 0; i < itemNo; i++) {
/*  952 */             Object listItemObj = listItemBindingMeta.bindingClass.newInstance();
/*  953 */             unmarshalParams(dis, list
/*      */                 
/*  955 */                 .getValueOrByteArrayOrBitArray(), listItemBindingMeta, listItemObj, lengthMap);
/*      */ 
/*      */ 
/*      */             
/*  959 */             pl.add(listItemObj);
/*      */           } 
/*  961 */           ((Field)fieldMap.get(list.getName())).set(cmdBindingObj, pl);
/*      */ 
/*      */ 
/*      */         
/*      */         case CT3_VEHICLE_LIST:
/*  966 */           vlist = (Ct3VehicleListElement)param;
/*  967 */           noBytes = new byte[vlist.getVehicleNoLength()];
/*  968 */           dis.read(noBytes);
/*  969 */           vehicleNo = (int)BytesUtility.setBytesToValue(noBytes);
/*  970 */           vehicleLen = dis.readByte();
/*  971 */           ((Field)fieldMap.get(vlist.getVehicleNoName())).set(cmdBindingObj, Integer.valueOf(vehicleNo));
/*  972 */           ((Field)fieldMap.get(vlist.getLenByteName())).set(cmdBindingObj, Integer.valueOf(vehicleLen));
/*      */           
/*  974 */           listBindingMeta = cmdBindingMeta.listItemBindingMetaMap.get(vlist.getName());
/*      */ 
/*      */           
/*  977 */           paramsRefElement1 = (ParamsRefElement)vlist.getValueOrByteArrayOrBitArray().get(0);
/*  978 */           cmdBindingMeta1 = this.cmdBindingMetaMap.get(paramsRefElement1.getParamsName());
/*  979 */           paramsElement1 = this.globalParamsMap.get(paramsRefElement1.getParamsName());
/*      */           
/*  981 */           listParamsList = paramsElement1.getValueOrByteArrayOrBitArray();
/*  982 */           newListParamList = new ArrayList<>(listParamsList.size() - 1);
/*  983 */           for (j = 1; j < listParamsList.size(); j++) {
/*  984 */             newListParamList.add(listParamsList.get(j));
/*      */           }
/*  986 */           list1 = new ArrayList(vehicleNo);
/*  987 */           for (m = 0; m < vehicleNo; m++) {
/*  988 */             Object listItemBindingObj = listBindingMeta.bindingClass.newInstance();
/*  989 */             Object object1 = cmdBindingMeta1.bindingClass.newInstance();
/*      */             
/*  991 */             byte[] vehicleBytes = new byte[vehicleLen];
/*  992 */             dis.read(vehicleBytes);
/*  993 */             String vehicle = new String(vehicleBytes, Charset.forName("ASCII"));
/*  994 */             ValueElement valueElement = (ValueElement)listParamsList.get(0);
/*  995 */             ((Field)cmdBindingMeta1.fieldMap.get(valueElement.getName())).set(object1, vehicle);
/*  996 */             unmarshalParams(dis, newListParamList, cmdBindingMeta1, object1, lengthMap);
/*  997 */             ((Field)listBindingMeta.fieldMap
/*      */               
/*  999 */               .get(paramsRefElement1.getParamsName()))
/* 1000 */               .set(listItemBindingObj, object1);
/* 1001 */             list1.add(listItemBindingObj);
/*      */           } 
/* 1003 */           ((Field)fieldMap.get(vlist.getName())).set(cmdBindingObj, list1);
/*      */ 
/*      */ 
/*      */         
/*      */         case PARAMS_REF:
/* 1008 */           paramRef = (ParamsRefElement)param;
/* 1009 */           paramBindingMeta = this.cmdBindingMetaMap.get(paramRef.getParamsName());
/* 1010 */           paramsBindingObj = paramBindingMeta.bindingClass.newInstance();
/* 1011 */           params = this.globalParamsMap.get(paramRef.getParamsName());
/* 1012 */           unmarshalParams(dis, params
/*      */               
/* 1014 */               .getValueOrByteArrayOrBitArray(), paramBindingMeta, paramsBindingObj, lengthMap);
/*      */ 
/*      */ 
/*      */           
/* 1018 */           ((Field)fieldMap.get(paramRef.getParamsName())).set(cmdBindingObj, paramsBindingObj);
/*      */ 
/*      */ 
/*      */         
/*      */         case CMD_PARAMS_REF:
/* 1023 */           cmdParamsRef = (CmdParamsRefElement)param;
/* 1024 */           cmdId = dis.read();
/* 1025 */           cm = this.cmdIdMetaMap.get(Integer.valueOf(cmdId));
/* 1026 */           if (cm == null) throw new IllegalParamException("Unknown command id: " + cmdId); 
/* 1027 */           if (cm.extCmdIdMap != null) {
/* 1028 */             cmdId = (cmdId << 8) + dis.read();
/* 1029 */             cm = cm.extCmdIdMap.get(Integer.valueOf(cmdId));
/* 1030 */             if (cm == null) throw new IllegalParamException("Unknown command id: " + cmdId);
/*      */           
/*      */           } 
/* 1033 */           if (cmdParamsRef.getDirection() == DirectionType.C_2_T) { refCommand = cm.c2tCommand; }
/* 1034 */           else { refCommand = cm.t2cCommand; }
/* 1035 */            if (refCommand == null)
/* 1036 */             throw new IllegalParamException("Undefined command id: " + 
/* 1037 */                 Integer.toHexString(cmdId)); 
/* 1038 */           refCmdBindingMeta = this.cmdBindingMetaMap.get(refCommand.getName());
/* 1039 */           refCmdBindingObj = refCmdBindingMeta.bindingClass.newInstance();
/* 1040 */           unmarshalParams(dis, refCommand
/*      */               
/* 1042 */               .getValueOrByteArrayOrBitArray(), refCmdBindingMeta, refCmdBindingObj, lengthMap);
/*      */ 
/*      */ 
/*      */           
/* 1046 */           cmdBindingMeta.cmdRef.set(cmdBindingObj, refCmdBindingObj);
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 1051 */     return cmdBindingObj;
/*      */   }
/*      */   
/*      */   public CmdFmtDefElement getCmdFmtDef() {
/* 1055 */     return this.cmdFmtDefs.get(0);
/*      */   }
/*      */   
/*      */   public List<CmdFmtDefElement> getCmdFmtDefs() {
/* 1059 */     return this.cmdFmtDefs;
/*      */   }
/*      */   
/*      */   public ParamsElement getParamsElement(String name) {
/* 1063 */     return this.globalParamsMap.get(name);
/*      */   }
/*      */   
/*      */   public CommandElement getCommandByName(String cmdName) {
/* 1067 */     return this.cmdNameMap.get(cmdName);
/*      */   }
/*      */   
/*      */   public CommandElement getCommandById(int cmdId, DirectionType direction) {
/* 1071 */     if (direction == DirectionType.C_2_T) return this.c2tCmdIdMap.get(Integer.valueOf(cmdId)); 
/* 1072 */     return this.t2cCmdIdMap.get(Integer.valueOf(cmdId));
/*      */   }
/*      */   
/*      */   public String getCommandNameById(int cmdId, DirectionType direction) {
/*      */     CommandElement command;
/* 1077 */     if (direction == DirectionType.C_2_T) { command = this.c2tCmdIdMap.get(Integer.valueOf(cmdId)); }
/* 1078 */     else { command = this.t2cCmdIdMap.get(Integer.valueOf(cmdId)); }
/* 1079 */      if (command != null) return command.getName(); 
/* 1080 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1085 */     StringBuilder sb = new StringBuilder();
/* 1086 */     sb.append("CmdProtMarshaller [");
/* 1087 */     for (CmdFmtDefElement cmdFmtDefElement : this.cmdFmtDefs) {
/* 1088 */       sb.append(cmdFmtDefElement.getName()).append('/');
/*      */     }
/* 1090 */     sb.setLength(sb.length() - 1);
/* 1091 */     sb.append(']');
/* 1092 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1097 */     if (this == obj) return true; 
/* 1098 */     if (obj == null) return false; 
/* 1099 */     if (getClass() != obj.getClass()) return false; 
/* 1100 */     CmdProtMarshaller other = (CmdProtMarshaller)obj;
/* 1101 */     if (this.cmdFmtDefs.size() == other.cmdFmtDefs.size()) {
/* 1102 */       for (int i = 0; i < this.cmdFmtDefs.size(); ) {
/* 1103 */         CmdFmtDefElement cmdFmtDefElement1 = this.cmdFmtDefs.get(i);
/* 1104 */         CmdFmtDefElement cmdFmtDefElement2 = other.cmdFmtDefs.get(i);
/* 1105 */         if (cmdFmtDefElement1.getName().equals(cmdFmtDefElement2.getName()) && cmdFmtDefElement1
/* 1106 */           .getVersion().equals(cmdFmtDefElement2.getVersion())) { i++; continue; }
/* 1107 */          return false;
/*      */       } 
/* 1109 */       return true;
/*      */     } 
/* 1111 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdProtMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */