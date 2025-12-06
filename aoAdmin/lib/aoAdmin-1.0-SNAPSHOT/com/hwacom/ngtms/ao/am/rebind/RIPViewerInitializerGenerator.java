/*     */ package com.hwacom.ngtms.ao.am.rebind;
/*     */ 
/*     */ import com.google.gwt.core.ext.Generator;
/*     */ import com.google.gwt.core.ext.GeneratorContext;
/*     */ import com.google.gwt.core.ext.TreeLogger;
/*     */ import com.google.gwt.core.ext.UnableToCompleteException;
/*     */ import com.google.gwt.core.ext.typeinfo.JClassType;
/*     */ import com.google.gwt.core.ext.typeinfo.NotFoundException;
/*     */ import com.google.gwt.core.ext.typeinfo.TypeOracle;
/*     */ import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
/*     */ import com.google.gwt.user.rebind.SourceWriter;
/*     */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewer;
/*     */ import com.hwacom.ngtms.ao.am.view.rpt.Viewer;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RIPViewerInitializerGenerator
/*     */   extends Generator
/*     */ {
/*  25 */   private final String generatedTypeSuffix = "Impl";
/*     */ 
/*     */ 
/*     */   
/*     */   public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
/*     */     try {
/*  31 */       TypeOracle typeOracle = context.getTypeOracle();
/*  32 */       JClassType ripViewerInitializerType = typeOracle.getType(typeName);
/*  33 */       logger.log(TreeLogger.INFO, "Generating source for " + ripViewerInitializerType
/*     */           
/*  35 */           .getQualifiedSourceName());
/*     */       
/*  37 */       String generatedTypeFullName = ripViewerInitializerType.getQualifiedSourceName() + "Impl";
/*  38 */       SourceWriter sourceWriter = getSourceWriter(logger, context, ripViewerInitializerType);
/*  39 */       if (sourceWriter == null) {
/*  40 */         return generatedTypeFullName;
/*     */       }
/*     */       
/*  43 */       JClassType ripViewerType = typeOracle.getType(RIPViewer.class.getName());
/*  44 */       String parameterName = "className";
/*  45 */       sourceWriter.println("public " + ripViewerType
/*     */           
/*  47 */           .getQualifiedSourceName() + " init(String " + parameterName + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  52 */       int counter = 0;
/*  53 */       for (JClassType type : typeOracle.getTypes()) {
/*  54 */         if (!type.isAbstract() && type
/*  55 */           .isInterface() == null && type
/*  56 */           .isAssignableTo(ripViewerType)) {
/*  57 */           if (counter == 0) {
/*  58 */             sourceWriter.print("if (\"");
/*     */           } else {
/*  60 */             sourceWriter.print("else if (\"");
/*     */           } 
/*  62 */           sourceWriter.println(type
/*  63 */               .getQualifiedSourceName() + "\".equals(" + parameterName + ")) {");
/*  64 */           sourceWriter.println(ripViewerType
/*  65 */               .getQualifiedSourceName() + " ripViewer = new " + type
/*     */               
/*  67 */               .getQualifiedSourceName() + "();");
/*     */           
/*  69 */           sourceWriter.println("return ripViewer;");
/*  70 */           sourceWriter.println("}");
/*  71 */           counter++;
/*     */         } 
/*     */       } 
/*  74 */       sourceWriter.println("return null;");
/*  75 */       sourceWriter.println("}");
/*  76 */       sourceWriter.commit(logger);
/*  77 */       logger.log(TreeLogger.INFO, "Done Generating source for " + ripViewerInitializerType
/*     */           
/*  79 */           .getQualifiedSourceName());
/*     */       
/*  81 */       return generatedTypeFullName;
/*  82 */     } catch (NotFoundException ex) {
/*  83 */       logger.log(TreeLogger.Type.ERROR, "Class '" + Viewer.class.getName() + "' Not Found", (Throwable)ex);
/*  84 */       throw new UnableToCompleteException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SourceWriter getSourceWriter(TreeLogger logger, GeneratorContext context, JClassType classType) {
/*  90 */     String packageName = classType.getPackage().getName();
/*  91 */     String simpleName = classType.getSimpleSourceName() + "Impl";
/*  92 */     PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
/*  93 */     if (printWriter == null) {
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
/*     */     
/*  99 */     composer.addImplementedInterface(classType.getQualifiedSourceName());
/*     */     
/* 101 */     return composer.createSourceWriter(context, printWriter);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\rebind\RIPViewerInitializerGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */