/*    */ package com.hwacom.ngtms.cam.server;
/*    */ 
/*    */ import gwtupload.server.UploadAction;
/*    */ import gwtupload.server.exceptions.UploadActionException;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.annotation.WebServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.fileupload.FileItem;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ 
/*    */ @WebServlet(name = "BackgroundGraphicUploader", urlPatterns = {"/api/graphicUploader"})
/*    */ public class BackgroundGraphicUploader
/*    */   extends UploadAction
/*    */ {
/*    */   private static final long serialVersionUID = -6163907169474382849L;
/* 30 */   private static final Logger logger = LoggerFactory.getLogger(BackgroundGraphicUploader.class);
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
/* 35 */     UploadAction.removeSessionFileItems(request, getSessionFilesKey(request));
/* 36 */     super.doPost(request, response);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
/* 42 */     logger.debug("Upload {} Background Graphic.", 
/*    */         
/* 44 */         Integer.valueOf(getSessionFileItems(request, "BACKGROUND_GRAPHICS").size()));
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getSessionFilesKey(HttpServletRequest request) {
/* 50 */     return "BACKGROUND_GRAPHICS";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getSessionLastFilesKey(HttpServletRequest request) {
/* 55 */     return "BACKGROUND_LAST_GRAPHICS";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\am-common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cam\server\BackgroundGraphicUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */