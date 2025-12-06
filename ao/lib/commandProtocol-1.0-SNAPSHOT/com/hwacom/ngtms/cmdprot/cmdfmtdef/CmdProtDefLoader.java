/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.ValidationEvent;
/*    */ import javax.xml.bind.ValidationEventHandler;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.core.io.ClassPathResource;
/*    */ import org.springframework.oxm.jaxb.Jaxb2Marshaller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CmdProtDefLoader
/*    */ {
/* 25 */   private static Logger logger = LoggerFactory.getLogger(CmdProtDefLoader.class);
/*    */   private Jaxb2Marshaller jaxb2Marshaller;
/*    */   private String curCmdFmtDefFile;
/*    */   
/*    */   public CmdProtDefLoader(Jaxb2Marshaller jaxb2Marshaller) {
/* 30 */     this.jaxb2Marshaller = jaxb2Marshaller;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<CmdFmtDefElement> load(String cmdFmtDefFile)
/*    */     throws Exception
/*    */   {
/* 40 */     this.jaxb2Marshaller.setValidationEventHandler(new ValidationEventHandler()
/*    */     {
/*    */       public boolean handleEvent(ValidationEvent event)
/*    */       {
/* 44 */         CmdProtDefLoader.logger.error("Validate command format definition file: {} failed, {}", 
/*    */         
/* 46 */           CmdProtDefLoader.this.curCmdFmtDefFile, event
/* 47 */           .getMessage());
/* 48 */         return false;
/*    */       }
/* 50 */     });
/* 51 */     List<CmdFmtDefElement> cmdFmtDefs = new ArrayList();
/* 52 */     StreamSource source = null;
/*    */     try {
/*    */       ClassPathResource resource;
/* 55 */       for (;;) { resource = new ClassPathResource(cmdFmtDefFile);
/* 56 */         source = new StreamSource(resource.getInputStream());
/* 57 */         CmdFmtDefElement cmdFmtDef = (CmdFmtDefElement)this.jaxb2Marshaller.unmarshal(source);
/* 58 */         cmdFmtDefs.add(cmdFmtDef);
/* 59 */         if (cmdFmtDef.extend == null) break;
/* 60 */         source.getInputStream().close();
/* 61 */         cmdFmtDefFile = cmdFmtDef.extend;
/*    */       }
/*    */       
/* 64 */       return cmdFmtDefs;
/*    */     } finally {
/* 66 */       if (source != null) source.getInputStream().close();
/* 67 */       this.jaxb2Marshaller.setValidationEventHandler(null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdProtDefLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */