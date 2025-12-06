/*     */ package com.hwacom.ngtms.common.aop;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.NoOperationLog;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.service.Operation;
/*     */ import com.hwacom.ngtms.base.oplog.service.OperationHolder;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
/*     */ import com.hwacom.ngtms.common.util.AuthUtils;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.aspectj.lang.JoinPoint;
/*     */ import org.aspectj.lang.Signature;
/*     */ import org.aspectj.lang.annotation.AfterReturning;
/*     */ import org.aspectj.lang.annotation.AfterThrowing;
/*     */ import org.aspectj.lang.annotation.Aspect;
/*     */ import org.aspectj.lang.annotation.Before;
/*     */ import org.aspectj.lang.reflect.MethodSignature;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Aspect
/*     */ public class AspectRestLogger
/*     */ {
/*  41 */   private static final Logger logger = LoggerFactory.getLogger(AspectRestLogger.class);
/*     */   
/*     */   private static final String POINTCUT = "execution(*  com.hwacom.ngtms..restful.*Rest*.*(javax.servlet.http.HttpServletRequest,..))";
/*     */   
/*     */   private static final String DEVICE_NAME = "deviceName";
/*     */   
/*     */   @Autowired
/*     */   private BaseOpLogger opLogger;
/*     */   @Autowired
/*     */   protected MessageSourceExt messageSourceExt;
/*     */   
/*     */   @Before("execution(*  com.hwacom.ngtms..restful.*Rest*.*(javax.servlet.http.HttpServletRequest,..))")
/*     */   private void before() {
/*  54 */     logger.debug("Init operation.");
/*  55 */     OperationHolder.instance().setOperation(new Operation());
/*     */   }
/*     */   
/*     */   private void clearOperation() {
/*  59 */     logger.debug("Clear operation.");
/*  60 */     OperationHolder.instance().remove();
/*     */   }
/*     */   
/*     */   @AfterReturning(pointcut = "execution(*  com.hwacom.ngtms..restful.*Rest*.*(javax.servlet.http.HttpServletRequest,..))", returning = "retVal")
/*     */   private void logNormalReturn(JoinPoint joinPoint, Object retVal) {
/*     */     try {
/*  66 */       Boolean result = Boolean.TRUE;
/*  67 */       String remark = null;
/*  68 */       if (retVal instanceof Boolean) {
/*  69 */         result = (Boolean)retVal;
/*  70 */       } else if (retVal instanceof SingleHintMessageDTO) {
/*  71 */         SingleHintMessageDTO dto = (SingleHintMessageDTO)retVal;
/*  72 */         result = Boolean.valueOf((dto.getString() == null));
/*  73 */         remark = dto.getString();
/*     */       } 
/*  75 */       addOpLog(joinPoint, result.booleanValue(), remark);
/*  76 */       logger.debug("Add rest log success.");
/*  77 */     } catch (RuntimeException e) {
/*  78 */       logger.debug("Add rest log failed.", e);
/*     */     } finally {
/*  80 */       clearOperation();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toRemark() {
/*     */     try {
/*  86 */       StringBuffer stringBuffer = new StringBuffer();
/*  87 */       Operation operation = OperationHolder.instance().getOperation();
/*  88 */       for (Operation.Diff diff : operation.diff().values()) {
/*  89 */         Optional<Object> optDiff = toDiffFieldValue(operation, diff);
/*  90 */         if (!optDiff.isPresent()) {
/*  91 */           logger.debug("Field is empty. fieldName: '{}'", diff.getFieldName());
/*     */           continue;
/*     */         } 
/*  94 */         if (stringBuffer.length() != 0) {
/*  95 */           stringBuffer.append(", ");
/*     */         }
/*  97 */         stringBuffer.append(toFieldMessage(diff));
/*  98 */         stringBuffer.append(": ");
/*  99 */         stringBuffer.append(optDiff.get());
/*     */       } 
/* 101 */       String remark = stringBuffer.toString();
/* 102 */       return remark.isEmpty() ? null : remark;
/* 103 */     } catch (Exception e) {
/* 104 */       logger.warn("Operation to remark failed!", e);
/* 105 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toFieldMessage(Operation.Diff diff) {
/* 110 */     String fieldMessage = this.messageSourceExt.getMessage(diff.getFieldMessageCode());
/* 111 */     if (Objects.equals(fieldMessage, diff.getFieldMessageCode())) {
/* 112 */       fieldMessage = diff.getDefaultFieldDescription();
/*     */     }
/* 114 */     return fieldMessage;
/*     */   }
/*     */   
/*     */   private Optional<Object> toDiffFieldValue(Operation operation, Operation.Diff diff) {
/* 118 */     Optional<Object> optCurrentFieldValue = Optional.ofNullable(diff.getCurrentFieldValue());
/* 119 */     Optional<Object> optPreviousFieldValue = Optional.ofNullable(diff.getPreviousFieldValue());
/* 120 */     switch (operation.getAction()) {
/*     */       case CREATE:
/* 122 */         return convert(optCurrentFieldValue);
/*     */       case DELETE:
/* 124 */         return convert(optPreviousFieldValue);
/*     */     } 
/*     */     
/* 127 */     return Optional.of((new StringBuilder())
/* 128 */         .append(convert(optPreviousFieldValue).orElse("")).append(" -> ")
/*     */         
/* 130 */         .append(convert(optCurrentFieldValue).orElse("")).toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<Object> convert(Optional<Object> opt) {
/* 135 */     if (opt.isPresent() && 
/* 136 */       opt.get() instanceof Date) {
/* 137 */       Date date = (Date)opt.get();
/* 138 */       return Optional.of((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date));
/*     */     } 
/*     */     
/* 141 */     return opt;
/*     */   }
/*     */   
/*     */   private void addOpLog(JoinPoint joinPoint, boolean result, String remark) {
/* 145 */     if (isNoLog(joinPoint)) {
/*     */       return;
/*     */     }
/* 148 */     String username = null;
/* 149 */     String ip = null;
/* 150 */     String moduleName = "";
/* 151 */     String deviceName = null;
/* 152 */     for (Object arg : joinPoint.getArgs()) {
/* 153 */       if (arg instanceof java.io.Serializable) {
/* 154 */         deviceName = extractDeviceName(arg, joinPoint);
/* 155 */       } else if (arg instanceof HttpServletRequest) {
/* 156 */         HttpServletRequest request = (HttpServletRequest)arg;
/* 157 */         username = AuthUtils.extractUserLogin(request);
/* 158 */         ip = request.getRemoteAddr();
/* 159 */         moduleName = extractModuleName(request);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     String description = toDescription(joinPoint, moduleName + "." + joinPoint.getSignature().getName());
/* 164 */     this.opLogger.addLog(username, ip, moduleName
/*     */ 
/*     */         
/* 167 */         .toUpperCase(), OperationItem.SET, deviceName, new Date(), result ? OperationResult.SUCCESS : OperationResult.FAILURE, (remark == null) ? 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 172 */         toRemark() : remark, description, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isNoLog(JoinPoint joinPoint) {
/* 177 */     return extractAnnotation(joinPoint, NoOperationLog.class).isPresent();
/*     */   }
/*     */   
/*     */   private <T extends java.lang.annotation.Annotation> Optional<T> extractAnnotation(JoinPoint joinPoint, Class<T> c) {
/* 181 */     Signature signature = joinPoint.getSignature();
/* 182 */     if (signature instanceof MethodSignature) {
/* 183 */       MethodSignature methodSignature = (MethodSignature)signature;
/* 184 */       Method method = methodSignature.getMethod();
/* 185 */       if (method != null) {
/* 186 */         T result = method.getAnnotation(c);
/* 187 */         if (result != null) {
/* 188 */           return Optional.of(result);
/*     */         }
/*     */       } 
/*     */     } 
/* 192 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private String toDescription(JoinPoint joinPoint, String defaultDescriptionCode) {
/* 196 */     String description = this.messageSourceExt.getMessage(defaultDescriptionCode);
/* 197 */     if (Objects.equals(description, defaultDescriptionCode)) {
/* 198 */       Optional<ApiOperation> optApiOperation = extractAnnotation(joinPoint, ApiOperation.class);
/* 199 */       if (optApiOperation.isPresent()) {
/* 200 */         description = ((ApiOperation)optApiOperation.get()).value();
/*     */       }
/*     */     } 
/* 203 */     return description;
/*     */   }
/*     */   
/*     */   private String extractDeviceName(Object object, JoinPoint joinPoint) {
/*     */     try {
/* 208 */       return BeanUtils.getProperty(object, "deviceName");
/* 209 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException e) {
/* 210 */       return extractDeviceName(joinPoint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String extractDeviceName(JoinPoint joinPoint) {
/* 215 */     Signature signature = joinPoint.getSignature();
/* 216 */     if (signature instanceof MethodSignature) {
/* 217 */       MethodSignature methodSignature = (MethodSignature)signature;
/* 218 */       Parameter[] parameters = methodSignature.getMethod().getParameters();
/* 219 */       for (int i = 0; i < parameters.length; i++) {
/* 220 */         Parameter parameter = parameters[i];
/* 221 */         if ("deviceName".equals(parameter.getName())) {
/* 222 */           return (String)joinPoint.getArgs()[i];
/*     */         }
/*     */       } 
/*     */     } 
/* 226 */     return null;
/*     */   }
/*     */   
/*     */   private String extractModuleName(HttpServletRequest request) {
/* 230 */     String[] uriArray = request.getRequestURI().split("/");
/* 231 */     if (uriArray != null && uriArray.length > 1) {
/* 232 */       return uriArray[2];
/*     */     }
/* 234 */     return "";
/*     */   }
/*     */   
/*     */   @AfterThrowing(pointcut = "execution(*  com.hwacom.ngtms..restful.*Rest*.*(javax.servlet.http.HttpServletRequest,..))", throwing = "thrown")
/*     */   public void logExceptionalReturn(JoinPoint joinPoint, Throwable thrown) {
/*     */     try {
/* 240 */       addOpLog(joinPoint, false, thrown.getMessage());
/* 241 */     } catch (RuntimeException ex) {
/* 242 */       logger.warn("Add op log failed!", ex);
/*     */     } finally {
/* 244 */       clearOperation();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\aop\AspectRestLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */