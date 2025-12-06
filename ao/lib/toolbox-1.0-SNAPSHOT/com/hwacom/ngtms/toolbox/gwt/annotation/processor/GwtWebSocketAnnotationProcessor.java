/*    */ package com.hwacom.ngtms.toolbox.gwt.annotation.processor;
/*    */ 
/*    */ import com.hwacom.ngtms.toolbox.gwt.annotation.GwtWebSocket;
/*    */ import com.hwacom.ngtms.toolbox.gwt.annotation.GwtWebSockets;
/*    */ import freemarker.cache.ClassTemplateLoader;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import freemarker.template.TemplateException;
/*    */ import java.io.IOException;
/*    */ import java.time.Year;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.processing.AbstractProcessor;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.RoundEnvironment;
/*    */ import javax.annotation.processing.SupportedSourceVersion;
/*    */ import javax.lang.model.SourceVersion;
/*    */ import javax.lang.model.element.Element;
/*    */ import javax.lang.model.element.PackageElement;
/*    */ import javax.lang.model.element.TypeElement;
/*    */ import javax.tools.JavaFileObject;
/*    */ 
/*    */ @javax.annotation.processing.SupportedAnnotationTypes({"com.hwacom.ngtms.toolbox.gwt.annotation.GwtWebSockets"})
/*    */ @SupportedSourceVersion(SourceVersion.RELEASE_8)
/*    */ public class GwtWebSocketAnnotationProcessor extends AbstractProcessor
/*    */ {
/*    */   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
/*    */   {
/* 30 */     for (Element each : roundEnv.getElementsAnnotatedWith(GwtWebSockets.class)) {
/* 31 */       String packageName = ((PackageElement)each).getQualifiedName().toString();
/* 32 */       GwtWebSockets events = (GwtWebSockets)each.getAnnotation(GwtWebSockets.class);
/* 33 */       for (GwtWebSocket webSocket : events.value()) {
/* 34 */         generateJavaFile(packageName, webSocket);
/*    */       }
/*    */     }
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   private void generateJavaFile(String packageName, GwtWebSocket webSocket) {
/*    */     try {
/* 42 */       Configuration ex = new Configuration(Configuration.VERSION_2_3_23);
/* 43 */       ex.setDefaultEncoding("UTF-8");
/* 44 */       ex.setTemplateLoader(new ClassTemplateLoader(GwtEventAnnotationProcessor.class, "templates"));
/* 45 */       Template template = ex.getTemplate("GwtWebSocket.template");
/* 46 */       Map<String, Object> context = new HashMap();
/* 47 */       context.put("year", String.valueOf(Year.now().getValue()));
/* 48 */       context.put("packageName", packageName);
/* 49 */       context.put("webSocketName", webSocket.name());
/* 50 */       context.put("entryPoint", webSocket.entryPoint());
/* 51 */       context.put("uri", webSocket.uri());
/*    */       
/* 53 */       JavaFileObject javaFile = this.processingEnv.getFiler().createSourceFile(packageName + "." + webSocket.name(), new Element[0]);
/* 54 */       template.process(context, javaFile.openWriter());
/*    */     } catch (IOException|TemplateException e) {
/* 56 */       throw new RuntimeException(e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\gwt\annotation\processor\GwtWebSocketAnnotationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */