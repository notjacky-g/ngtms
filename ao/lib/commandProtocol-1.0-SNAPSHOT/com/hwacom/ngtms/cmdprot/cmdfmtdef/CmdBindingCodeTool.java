/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.oxm.jaxb.Jaxb2Marshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CmdBindingCodeTool
/*     */ {
/*  21 */   private static Logger logger = LoggerFactory.getLogger(CmdBindingCodeTool.class);
/*     */   
/*     */ 
/*     */   private String outDir;
/*     */   
/*     */ 
/*     */   private Jaxb2Marshaller jaxb2Marshaller;
/*     */   
/*     */ 
/*     */ 
/*     */   public static class CustomizeCode
/*     */   {
/*     */     String extendsCode;
/*     */     
/*     */ 
/*     */     String methodCode;
/*     */     
/*     */ 
/*     */     public CustomizeCode(String extendsCode)
/*     */     {
/*  41 */       this.extendsCode = extendsCode;
/*     */     }
/*     */     
/*     */     public CustomizeCode(String extendsCode, String methodCode) {
/*  45 */       this.extendsCode = extendsCode;
/*  46 */       this.methodCode = methodCode;
/*     */     }
/*     */   }
/*     */   
/*     */   public CmdBindingCodeTool(Jaxb2Marshaller jaxb2Marshaller, String outDir) {
/*  51 */     this.jaxb2Marshaller = jaxb2Marshaller;
/*  52 */     this.outDir = outDir;
/*     */   }
/*     */   
/*     */   private boolean deleteDirectory(File path) {
/*  56 */     if (path.exists()) {
/*  57 */       File[] files = path.listFiles();
/*  58 */       for (int i = 0; i < files.length; i++) {
/*  59 */         if (files[i].isDirectory()) {
/*  60 */           deleteDirectory(files[i]);
/*     */         } else {
/*  62 */           files[i].delete();
/*     */         }
/*     */       }
/*     */     }
/*  66 */     return path.delete();
/*     */   }
/*     */   
/*     */   private String toJavaClassName(String name) {
/*  70 */     if (Character.isLowerCase(name.charAt(0))) {
/*  71 */       return Character.toUpperCase(name.charAt(0)) + name.substring(1);
/*     */     }
/*  73 */     return name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendExternalCode(String name, String type, String bits, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/*  90 */     writeSb.append(tab).append('\t').append("out.write").append(type).append("(").append(name).append(");\n");
/*  91 */     readSb
/*  92 */       .append(tab)
/*  93 */       .append('\t')
/*  94 */       .append(name)
/*  95 */       .append("=in.read")
/*  96 */       .append(type)
/*  97 */       .append("()")
/*  98 */       .append(bits)
/*  99 */       .append(";\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendStringExternalCode(String name, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 111 */     writeSb.append(tab).append('\t').append("byte[] bytes = ").append(name).append(" == null ? new byte[0] : ").append(name).append(".getBytes(java.nio.charset.Charset.forName(\"UTF-8\"));\n");
/* 112 */     writeSb.append(tab).append('\t').append("out.writeInt(bytes.length);\n");
/* 113 */     writeSb.append(tab).append('\t').append("out.write(bytes);\n");
/*     */     
/* 115 */     readSb.append(tab).append('\t').append("int len = in.readInt();\n");
/* 116 */     readSb.append(tab).append('\t').append("byte[] bytes = new byte[len];\n");
/* 117 */     readSb.append(tab).append('\t').append("in.read(bytes);\n");
/* 118 */     readSb
/* 119 */       .append(tab)
/* 120 */       .append('\t')
/* 121 */       .append(name)
/* 122 */       .append(" = new String(bytes, java.nio.charset.Charset.forName(\"UTF-8\"));\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendLongExternalCode(String name, String type, String bits, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 139 */     writeSb.append(tab).append('\t').append("out.write").append(type).append("((int)").append(name).append(");\n");
/* 140 */     readSb
/* 141 */       .append(tab)
/* 142 */       .append('\t')
/* 143 */       .append(name)
/* 144 */       .append("=in.read")
/* 145 */       .append(type)
/* 146 */       .append("()")
/* 147 */       .append(bits)
/* 148 */       .append(";\n");
/*     */   }
/*     */   
/*     */   private void appendListExternalCode(ListElement list, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 153 */     String listItemClassName = toJavaClassName(list.getName()) + "Item";
/* 154 */     writeSb
/* 155 */       .append(tab)
/* 156 */       .append('\t')
/* 157 */       .append("out.writeObject(")
/* 158 */       .append(list.getName())
/* 159 */       .append(");\n");
/* 160 */     readSb
/* 161 */       .append(tab)
/* 162 */       .append('\t')
/* 163 */       .append(list.getName())
/* 164 */       .append("=(java.util.List<")
/* 165 */       .append(listItemClassName)
/* 166 */       .append(">)in.readObject();\n");
/*     */   }
/*     */   
/*     */   private void appendCt3VehicleListExternalCode(Ct3VehicleListElement list, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 171 */     String listItemClassName = toJavaClassName(list.getName()) + "Item";
/* 172 */     writeSb
/* 173 */       .append(tab)
/* 174 */       .append('\t')
/* 175 */       .append("out.writeObject(")
/* 176 */       .append(list.getName())
/* 177 */       .append(");\n");
/* 178 */     readSb
/* 179 */       .append(tab)
/* 180 */       .append('\t')
/* 181 */       .append(list.getName())
/* 182 */       .append("=(java.util.List<")
/* 183 */       .append(listItemClassName)
/* 184 */       .append(">)in.readObject();\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendByteArrayExternalCode(String name, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 196 */     writeSb.append(tab).append('\t').append("out.writeObject(").append(name).append(");\n");
/* 197 */     readSb.append(tab).append('\t').append(name).append("=(byte[])in.readObject();\n");
/*     */   }
/*     */   
/*     */   private void appendObjectExternalCode(String name, String tab, StringBuilder writeSb, StringBuilder readSb)
/*     */   {
/* 202 */     writeSb.append(tab).append('\t').append("out.writeObject(").append(name).append(");\n");
/* 203 */     readSb
/* 204 */       .append(tab)
/* 205 */       .append('\t')
/* 206 */       .append(name)
/* 207 */       .append("=(")
/* 208 */       .append(toJavaClassName(name))
/* 209 */       .append(")in.readObject();\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendToStringCode(String name, String tab, StringBuilder sb)
/*     */   {
/* 219 */     sb.append(tab).append('\t').append("sb.append(\"").append(name).append(": \").append(").append(name).append(").append(\", \");\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendArrayToStringCode(String name, String tab, StringBuilder sb)
/*     */   {
/* 229 */     sb.append(tab).append('\t').append("sb.append(\"").append(name).append(": \").append(com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility.toHexString(").append(name).append(")).append(\", \");\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendFixValueToStringCode(String name, String tab, StringBuilder sb)
/*     */   {
/* 239 */     sb.append(tab).append('\t').append("sb.append(\"").append(name).append(": \").append(" + BytesUtility.class.getName() + ".toHexString(").append(name).append(")).append(\", \");\n");
/*     */   }
/*     */   
/*     */   private void appendCmdParamCode(String name, String defaultValue, String type, String tab, StringBuilder sb)
/*     */   {
/* 244 */     sb.append(tab).append("@CommandParam(name=\"").append(name).append("\")\n");
/* 245 */     sb.append(tab).append("public ").append(type).append(' ').append(name);
/* 246 */     if ((defaultValue != null) && (defaultValue.length() > 0))
/* 247 */       sb.append(" = ").append(defaultValue);
/* 248 */     sb.append(";\n");
/*     */   }
/*     */   
/*     */   private void appendParamsRefParamCode(String annotaionName, String name, String type, String tab, StringBuilder sb)
/*     */   {
/* 253 */     sb.append(tab).append("@CommandParam(name=\"").append(annotaionName).append("\")\n");
/* 254 */     sb.append(tab).append("public ").append(type).append(' ').append(name).append(";\n");
/*     */   }
/*     */   
/*     */   private void appendFixValueParamCode(String name, String type, String value, String tab, StringBuilder sb)
/*     */   {
/* 259 */     sb.append(tab).append("@CommandParam(name=\"").append(name).append("\")\n");
/* 260 */     sb.append(tab)
/* 261 */       .append("public static final ")
/* 262 */       .append(type)
/* 263 */       .append(' ')
/* 264 */       .append(name)
/* 265 */       .append('=')
/* 266 */       .append(value)
/* 267 */       .append(";\n");
/*     */   }
/*     */   
/*     */   private void appendCmdRefParamCode(String tab, StringBuilder sb) {
/* 271 */     sb.append(tab).append("@CommandParam(cmdRef=true)\n");
/* 272 */     sb.append(tab).append("public Object cmdParamRef;\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendListStartCode(ListElement list, String tab, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 282 */     classSb.append(tab).append("@CommandParam(name=\"").append(list.getName()).append("\")\n");
/* 283 */     String listItemClassName = toJavaClassName(list.getName()) + "Item";
/* 284 */     classSb
/* 285 */       .append(tab)
/* 286 */       .append("public java.util.List<")
/* 287 */       .append(listItemClassName)
/* 288 */       .append("> ")
/* 289 */       .append(list.getName())
/* 290 */       .append(";\n");
/* 291 */     classSb
/* 292 */       .append(tab)
/* 293 */       .append("public static class ")
/* 294 */       .append(listItemClassName)
/* 295 */       .append(" implements Externalizable{\n")
/* 296 */       .append(tab)
/* 297 */       .append("\tprivate static final long serialVersionUID = 1L;\n");
/* 298 */     toStringSb.append("\t\tpublic String toString(){\n\t\t\tStringBuilder sb=new StringBuilder();\n\t\t\tsb.append('[');\n");
/*     */     
/* 300 */     writeExternalSb.append("\t\t@Override\n\t\tpublic void writeExternal(ObjectOutput out) throws IOException {\n");
/*     */     
/* 302 */     readExternalSb.append("\t\t@Override\n\t\tpublic void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendCt3VehicleListStartCode(Ct3VehicleListElement list, String tab, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 317 */     classSb.append(tab).append("@CommandParam(name=\"").append(list.getVehicleNoName()).append("\")\n");
/* 318 */     classSb.append(tab).append("public int ").append(list.getVehicleNoName()).append(";\n\n");
/* 319 */     classSb
/* 320 */       .append(tab)
/* 321 */       .append("@CommandParam(name=\"")
/* 322 */       .append(list.getLenByteName())
/* 323 */       .append("\")\n");
/* 324 */     classSb.append(tab).append("public int ").append(list.getLenByteName()).append(";\n\n");
/*     */     
/* 326 */     classSb.append(tab).append("@CommandParam(name=\"").append(list.getName()).append("\")\n");
/* 327 */     String listItemClassName = toJavaClassName(list.getName()) + "Item";
/* 328 */     classSb
/* 329 */       .append(tab)
/* 330 */       .append("public java.util.List<")
/* 331 */       .append(listItemClassName)
/* 332 */       .append("> ")
/* 333 */       .append(list.getName())
/* 334 */       .append(";\n");
/* 335 */     classSb
/* 336 */       .append(tab)
/* 337 */       .append("public static class ")
/* 338 */       .append(listItemClassName)
/* 339 */       .append(" implements Externalizable{\n")
/* 340 */       .append(tab)
/* 341 */       .append("\tprivate static final long serialVersionUID = 1L;\n");
/* 342 */     toStringSb.append("\t\tpublic String toString(){\n\t\t\tStringBuilder sb=new StringBuilder();\n\t\t\tsb.append('[');\n");
/*     */     
/* 344 */     writeExternalSb.append("\t\t@Override\n\t\tpublic void writeExternal(ObjectOutput out) throws IOException {\n");
/*     */     
/* 346 */     readExternalSb.append("\t\t@Override\n\t\tpublic void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendListEndCode(String tab, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 356 */     toStringSb.setLength(toStringSb.length() - 15);
/* 357 */     toStringSb.append(".append(']');\n\t\t\treturn sb.toString();\n\t\t}");
/*     */     
/* 359 */     writeExternalSb.append("\t\t}");
/* 360 */     readExternalSb.append("\t\t}");
/* 361 */     classSb.append('\n').append(writeExternalSb).append('\n');
/* 362 */     classSb.append('\n').append(readExternalSb).append('\n');
/* 363 */     classSb.append('\n').append(toStringSb).append('\n');
/* 364 */     classSb.append('}');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String appendParamsClassStartCode(ParamsElement params, String classSuffix, String packageName, int paramNo, List<String> parentImportList, Map<String, CustomizeCode> classExtendMap, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 391 */     CustomizeCode customizeCode = null;
/* 392 */     if (classExtendMap != null) customizeCode = (CustomizeCode)classExtendMap.get(params.getName());
/* 393 */     classSb.append("// This file was generated by CmdBindingCodeTool\n");
/* 394 */     classSb.append("package ").append(packageName).append(";\n");
/* 395 */     classSb.append("import java.io.Externalizable;\n");
/* 396 */     classSb.append("import java.io.IOException;\n");
/* 397 */     classSb.append("import java.io.ObjectInput;\n");
/* 398 */     classSb.append("import java.io.ObjectOutput;\n");
/* 399 */     classSb.append("import ").append(GlobalParams.class.getCanonicalName()).append(";\n");
/* 400 */     if (paramNo > 0) {
/* 401 */       classSb.append("import ").append(CommandParam.class.getCanonicalName()).append(";\n");
/* 402 */       if (parentImportList.size() > 0) {
/* 403 */         for (String s : parentImportList) {
/* 404 */           classSb.append("import ").append(s).append(".*;\n");
/*     */         }
/*     */       }
/*     */     }
/* 408 */     String className = toJavaClassName(params.getName()) + classSuffix;
/* 409 */     classSb.append("\n@GlobalParams(paramsName=\"").append(params.getName()).append("\")\n");
/* 410 */     classSb.append("public class ").append(className);
/* 411 */     if ((customizeCode != null) && (customizeCode.extendsCode != null))
/* 412 */       classSb.append(' ').append(customizeCode.extendsCode);
/* 413 */     classSb.append(" implements Externalizable{\n\tprivate static final long serialVersionUID = 1L;\n");
/*     */     
/* 415 */     if ((customizeCode != null) && (customizeCode.methodCode != null))
/* 416 */       toStringSb.append('\t').append(customizeCode.methodCode).append('\n');
/* 417 */     if (paramNo > 0) {
/* 418 */       toStringSb.append("\tpublic String toString(){\n\t\tStringBuilder sb=new StringBuilder();\n\t\tsb.append('[');\n");
/*     */     }
/* 420 */     writeExternalSb.append("\t@Override\n\tpublic void writeExternal(ObjectOutput out) throws IOException {\n");
/*     */     
/* 422 */     readExternalSb.append("\t@Override\n\tpublic void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {\n");
/*     */     
/* 424 */     return className;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendParamsClassEndCode(int paramNo, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 440 */     appendClassEndCode(paramNo, classSb, toStringSb, writeExternalSb, readExternalSb);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String appendClassStartCode(CommandElement command, String classSuffix, String packageName, int paramNo, List<String> parentImportList, Map<String, CustomizeCode> classExtendMap, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 467 */     CustomizeCode customizeCode = null;
/* 468 */     if (classExtendMap != null) {
/* 469 */       customizeCode = (CustomizeCode)classExtendMap.get(command.getName());
/* 470 */       if (customizeCode == null) customizeCode = (CustomizeCode)classExtendMap.get("*");
/*     */     }
/* 472 */     classSb.append("// This file was generated by CmdBindingCodeTool\n");
/* 473 */     classSb.append("package ").append(packageName).append(";\n");
/* 474 */     classSb.append("import java.io.Externalizable;\n");
/* 475 */     classSb.append("import java.io.IOException;\n");
/* 476 */     classSb.append("import java.io.ObjectInput;\n");
/* 477 */     classSb.append("import java.io.ObjectOutput;\n");
/*     */     
/* 479 */     classSb.append("import ").append(CommandParams.class.getCanonicalName()).append(";\n");
/* 480 */     if (paramNo > 0) {
/* 481 */       classSb.append("import ").append(CommandParam.class.getCanonicalName()).append(";\n");
/* 482 */       if (parentImportList.size() > 0) {
/* 483 */         for (String s : parentImportList) {
/* 484 */           classSb.append("import ").append(s).append(".*;\n");
/*     */         }
/*     */       }
/*     */     }
/* 488 */     String className = toJavaClassName(command.getName()) + classSuffix;
/* 489 */     classSb.append("\n@CommandParams(cmdName=\"").append(command.getName()).append("\")\n");
/* 490 */     classSb.append("public class ").append(className);
/* 491 */     if ((customizeCode != null) && (customizeCode.extendsCode != null))
/* 492 */       classSb.append(' ').append(customizeCode.extendsCode);
/* 493 */     classSb.append(" implements Externalizable{\n\tprivate static final long serialVersionUID = 1L;\n");
/*     */     
/* 495 */     classSb
/* 496 */       .append("\tpublic static final int cmdId=0x")
/* 497 */       .append(Integer.toHexString(command.getId().intValue()))
/* 498 */       .append(";\n");
/* 499 */     classSb
/* 500 */       .append("\tpublic static final String cmdName=\"")
/* 501 */       .append(command.getName())
/* 502 */       .append("\";\n\n");
/* 503 */     if ((customizeCode != null) && (customizeCode.methodCode != null))
/* 504 */       toStringSb.append('\t').append(customizeCode.methodCode).append('\n');
/* 505 */     if (paramNo > 0) {
/* 506 */       toStringSb.append("\tpublic String toString(){\n\t\tStringBuilder sb=new StringBuilder();\n\t\tsb.append('[');\n");
/*     */     }
/*     */     
/* 509 */     writeExternalSb.append("\t@Override\n\tpublic void writeExternal(ObjectOutput out) throws IOException {\n");
/*     */     
/* 511 */     readExternalSb.append("\t@Override\n\tpublic void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {\n");
/*     */     
/* 513 */     return className;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void appendClassEndCode(int paramNo, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb)
/*     */   {
/* 528 */     if (paramNo > 0) {
/* 529 */       toStringSb.setLength(toStringSb.length() - 15);
/* 530 */       toStringSb.append(".append(']');\n\t\treturn sb.toString();\n\t}");
/*     */     } else {
/* 532 */       toStringSb.append("\tpublic String toString(){\n\t\treturn(\"[]\");\n\t}");
/*     */     }
/* 534 */     writeExternalSb.append("\t}");
/* 535 */     readExternalSb.append("\t}");
/* 536 */     classSb.append('\n').append(writeExternalSb).append('\n');
/* 537 */     classSb.append('\n').append(readExternalSb).append('\n');
/* 538 */     classSb.append('\n').append(toStringSb).append('\n');
/* 539 */     classSb.append('}');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void genParamClassSrc(List<XmlElement> paramList, String classSuffix, StringBuilder classSb, StringBuilder toStringSb, StringBuilder writeExternalSb, StringBuilder readExternalSb, String tab, CmdProtMarshaller cmdProtMarshaller)
/*     */   {
/* 560 */     for (XmlElement param : paramList) {
/* 561 */       switch (param.elType) {
/*     */       case VALUE: 
/* 563 */         ValueElement value = (ValueElement)param;
/* 564 */         switch (value.getType()) {
/*     */         case BYTE: 
/* 566 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "int", tab, classSb);
/* 567 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 568 */           appendExternalCode(value
/* 569 */             .getName(), "Byte", "&0xff", tab, writeExternalSb, readExternalSb);
/* 570 */           break;
/*     */         case BOOLEAN: 
/* 572 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "boolean", tab, classSb);
/* 573 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 574 */           appendExternalCode(value
/* 575 */             .getName(), "Boolean", "", tab, writeExternalSb, readExternalSb);
/* 576 */           break;
/*     */         case SHORT: 
/* 578 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "int", tab, classSb);
/* 579 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 580 */           appendExternalCode(value
/* 581 */             .getName(), "Short", "&0xffff", tab, writeExternalSb, readExternalSb);
/* 582 */           break;
/*     */         case INT: 
/* 584 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "long", tab, classSb);
/* 585 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 586 */           appendLongExternalCode(value
/* 587 */             .getName(), "Int", "&0xffffffffL", tab, writeExternalSb, readExternalSb);
/* 588 */           break;
/*     */         case FLOAT: 
/* 590 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "double", tab, classSb);
/* 591 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 592 */           appendExternalCode(value
/* 593 */             .getName(), "Float", "", tab, writeExternalSb, readExternalSb);
/* 594 */           break;
/*     */         case STRING: 
/* 596 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "String", tab, classSb);
/* 597 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 598 */           appendStringExternalCode(value.getName(), tab, writeExternalSb, readExternalSb);
/* 599 */           break;
/*     */         case CMD_ID: 
/* 601 */           appendCmdParamCode(value.getName(), value.getDefaultValue(), "int", tab, classSb);
/* 602 */           appendToStringCode(value.getName(), tab, toStringSb);
/* 603 */           appendExternalCode(value
/* 604 */             .getName(), "Short", "&0xffff", tab, writeExternalSb, readExternalSb);
/*     */         }
/*     */         
/* 607 */         break;
/*     */       case BYTE_ARRAY: 
/* 609 */         ByteArrayElement byteArray = (ByteArrayElement)param;
/* 610 */         appendCmdParamCode(byteArray.getName(), null, "byte[]", tab, classSb);
/* 611 */         appendArrayToStringCode(byteArray.getName(), tab, toStringSb);
/* 612 */         appendByteArrayExternalCode(byteArray.getName(), tab, writeExternalSb, readExternalSb);
/* 613 */         break;
/*     */       case BIT_ARRAY: 
/* 615 */         BitArrayElement bitArray = (BitArrayElement)param;
/* 616 */         for (BitArrayElement.Bits b : bitArray.getBits()) {
/* 617 */           appendCmdParamCode(b.getName(), null, "int", tab, classSb);
/* 618 */           appendToStringCode(b.getName(), tab, toStringSb);
/* 619 */           appendExternalCode(b
/* 620 */             .getName(), "Byte", "&0xffff", tab, writeExternalSb, readExternalSb);
/*     */         }
/* 622 */         break;
/*     */       case FIX_VALUE: 
/* 624 */         FixBytesElement fixBytes = (FixBytesElement)param;
/* 625 */         appendFixValueParamCode(fixBytes
/* 626 */           .getName(), "byte[]", 
/*     */           
/* 628 */           BytesUtility.toHexCode(fixBytes.getData()), tab, classSb);
/*     */         
/*     */ 
/* 631 */         appendFixValueToStringCode(fixBytes.getName(), tab, toStringSb);
/* 632 */         break;
/*     */       case LIST: 
/* 634 */         ListElement list = (ListElement)param;
/* 635 */         StringBuilder listToStringSb = new StringBuilder();
/* 636 */         StringBuilder listWriteExternalSb = new StringBuilder();
/* 637 */         StringBuilder listReadExternalSb = new StringBuilder();
/* 638 */         appendListStartCode(list, tab, classSb, listToStringSb, listWriteExternalSb, listReadExternalSb);
/*     */         
/* 640 */         genParamClassSrc(list
/* 641 */           .getValueOrByteArrayOrBitArray(), classSuffix, classSb, listToStringSb, listWriteExternalSb, listReadExternalSb, tab + "\t", cmdProtMarshaller);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 649 */         appendListEndCode(tab, classSb, listToStringSb, listWriteExternalSb, listReadExternalSb);
/* 650 */         appendToStringCode(list.getName(), tab, toStringSb);
/* 651 */         if (readExternalSb.indexOf("@SuppressWarnings") < 0)
/* 652 */           readExternalSb.insert(0, "\t@SuppressWarnings(\"unchecked\")\n");
/* 653 */         appendListExternalCode(list, tab, writeExternalSb, readExternalSb);
/* 654 */         break;
/*     */       case CT3_VEHICLE_LIST: 
/* 656 */         Ct3VehicleListElement vehicleList = (Ct3VehicleListElement)param;
/* 657 */         StringBuilder vListToStringSb = new StringBuilder();
/* 658 */         StringBuilder vListWriteExternalSb = new StringBuilder();
/* 659 */         StringBuilder vListReadExternalSb = new StringBuilder();
/* 660 */         List<XmlElement> listParamsList = vehicleList.getValueOrByteArrayOrBitArray();
/* 661 */         List<XmlElement> newListParamsList = new ArrayList(listParamsList.size() - 1);
/* 662 */         for (int i = 1; i < listParamsList.size(); i++) {
/* 663 */           newListParamsList.add(listParamsList.get(i));
/*     */         }
/*     */         
/* 666 */         appendCt3VehicleListStartCode(vehicleList, tab, classSb, vListToStringSb, vListWriteExternalSb, vListReadExternalSb);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 673 */         genParamClassSrc(vehicleList
/* 674 */           .getValueOrByteArrayOrBitArray(), classSuffix, classSb, vListToStringSb, vListWriteExternalSb, vListReadExternalSb, tab + "\t", cmdProtMarshaller);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 682 */         appendListEndCode(tab, classSb, vListToStringSb, vListWriteExternalSb, vListReadExternalSb);
/*     */         
/* 684 */         appendToStringCode(vehicleList.getName(), tab, toStringSb);
/* 685 */         if (readExternalSb.indexOf("@SuppressWarnings") < 0)
/* 686 */           readExternalSb.insert(0, "\t@SuppressWarnings(\"unchecked\")\n");
/* 687 */         appendCt3VehicleListExternalCode(vehicleList, tab, writeExternalSb, readExternalSb);
/* 688 */         break;
/*     */       case PARAMS_REF: 
/* 690 */         ParamsRefElement paramsRef = (ParamsRefElement)param;
/* 691 */         appendParamsRefParamCode(paramsRef
/* 692 */           .getParamsName(), paramsRef
/* 693 */           .getParamsName() + classSuffix, 
/* 694 */           toJavaClassName(paramsRef.getParamsName()) + classSuffix, tab, classSb);
/*     */         
/*     */ 
/* 697 */         appendToStringCode(paramsRef.getParamsName() + classSuffix, tab, toStringSb);
/* 698 */         appendObjectExternalCode(paramsRef
/* 699 */           .getParamsName() + classSuffix, tab, writeExternalSb, readExternalSb);
/* 700 */         break;
/*     */       case CMD_PARAMS_REF: 
/* 702 */         appendCmdRefParamCode(tab, classSb);
/* 703 */         appendToStringCode("cmdParamRef", tab, toStringSb);
/* 704 */         appendExternalCode("cmdParamRef", "Object", "", tab, writeExternalSb, readExternalSb);
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void genClassSrc(CmdProtMarshaller cmdProtMarshaller, Map<String, CustomizeCode> classExtendMap, boolean genExtend, boolean cleanPackage)
/*     */     throws IOException
/*     */   {
/* 725 */     List<CmdFmtDefElement> cmdFmtDefs = cmdProtMarshaller.getCmdFmtDefs();
/* 726 */     String packageName = ((CmdFmtDefElement)cmdFmtDefs.get(0)).bindPackage;
/* 727 */     String classSuffix = ((CmdFmtDefElement)cmdFmtDefs.get(0)).classSuffix;
/* 728 */     if (classSuffix == null) { classSuffix = "";
/*     */     }
/*     */     
/* 731 */     File packageDir = new File(this.outDir + File.separator + packageName.replace('.', File.separatorChar));
/* 732 */     if (cleanPackage) {
/* 733 */       deleteDirectory(packageDir);
/*     */     }
/* 735 */     packageDir.mkdirs();
/* 736 */     int size = cmdFmtDefs.size();
/* 737 */     if (!genExtend) size = 1;
/* 738 */     List<String> parentImportList = new ArrayList();
/* 739 */     StringBuilder classSb = new StringBuilder();
/* 740 */     StringBuilder toStringSb = new StringBuilder();
/* 741 */     StringBuilder writeExternalgSb = new StringBuilder();
/* 742 */     StringBuilder readExternalSb = new StringBuilder();
/* 743 */     for (int index = size - 1; index >= 0; index--) {
/* 744 */       CmdFmtDefElement cmdFmtDef = (CmdFmtDefElement)cmdFmtDefs.get(index);
/* 745 */       parentImportList.clear();
/* 746 */       for (int i = index; i < cmdFmtDefs.size(); i++) {
/* 747 */         CmdFmtDefElement cmdFmtDef1 = (CmdFmtDefElement)cmdFmtDefs.get(i);
/* 748 */         if (!parentImportList.contains(cmdFmtDef1.getBindPackage()))
/* 749 */           parentImportList.add(cmdFmtDef1.getBindPackage());
/*     */       }
/* 751 */       parentImportList.remove(0);
/* 752 */       if (cmdFmtDef.getGlobalParams() != null) {
/* 753 */         for (ParamsElement params : cmdFmtDef.getGlobalParams().getParams()) {
/* 754 */           classSb.setLength(0);
/* 755 */           toStringSb.setLength(0);
/* 756 */           writeExternalgSb.setLength(0);
/* 757 */           readExternalSb.setLength(0);
/* 758 */           List<XmlElement> paramList = params.getValueOrByteArrayOrBitArray();
/*     */           
/* 760 */           String className = appendParamsClassStartCode(params, classSuffix, packageName, paramList
/*     */           
/*     */ 
/*     */ 
/* 764 */             .size(), parentImportList, classExtendMap, classSb, toStringSb, writeExternalgSb, readExternalSb);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 771 */           genParamClassSrc(paramList, classSuffix, classSb, toStringSb, writeExternalgSb, readExternalSb, "\t", cmdProtMarshaller);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 780 */           appendParamsClassEndCode(paramList
/* 781 */             .size(), classSb, toStringSb, writeExternalgSb, readExternalSb);
/* 782 */           File file = new File(packageDir, className + ".java");
/* 783 */           FileWriter fw = new FileWriter(file);
/* 784 */           fw.write(classSb.toString());
/* 785 */           fw.close();
/*     */         }
/*     */       }
/* 788 */       for (CommandElement command : cmdFmtDef.getCommand()) {
/* 789 */         classSb.setLength(0);
/* 790 */         toStringSb.setLength(0);
/* 791 */         writeExternalgSb.setLength(0);
/* 792 */         readExternalSb.setLength(0);
/* 793 */         List<XmlElement> paramList = command.getValueOrByteArrayOrBitArray();
/*     */         
/* 795 */         String className = appendClassStartCode(command, classSuffix, packageName, paramList
/*     */         
/*     */ 
/*     */ 
/* 799 */           .size(), parentImportList, classExtendMap, classSb, toStringSb, writeExternalgSb, readExternalSb);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 806 */         genParamClassSrc(paramList, classSuffix, classSb, toStringSb, writeExternalgSb, readExternalSb, "\t", cmdProtMarshaller);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 815 */         appendClassEndCode(paramList.size(), classSb, toStringSb, writeExternalgSb, readExternalSb);
/* 816 */         File file = new File(packageDir, className + ".java");
/* 817 */         FileWriter fw = new FileWriter(file);
/* 818 */         fw.write(classSb.toString());
/* 819 */         fw.close();
/*     */       }
/* 821 */       logger.info("Generate Command Binding Classes of {} to {} successfully", cmdFmtDef
/*     */       
/* 823 */         .getName(), packageDir);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void genBindingSrc(String cmdFmtDefFile, Map<String, CustomizeCode> classExtendMap, boolean cleanPackage)
/*     */     throws IOException
/*     */   {
/* 838 */     genBindingSrc(cmdFmtDefFile, classExtendMap, false, cleanPackage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void genBindingSrc(String cmdFmtDefFile, Map<String, CustomizeCode> classExtendMap, boolean genExtend, boolean cleanPackage)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 856 */       CmdProtDefLoader cmdProtDefLoader = new CmdProtDefLoader(this.jaxb2Marshaller);
/* 857 */       List<CmdFmtDefElement> cmdFmtDefs = cmdProtDefLoader.load(cmdFmtDefFile);
/* 858 */       CmdProtMarshaller cmdProtMarshaller = new CmdProtMarshaller(cmdFmtDefs, false);
/* 859 */       genClassSrc(cmdProtMarshaller, classExtendMap, genExtend, cleanPackage);
/*     */     } catch (Exception ex) {
/* 861 */       logger.error("Generate binding code failed", ex);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdBindingCodeTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */