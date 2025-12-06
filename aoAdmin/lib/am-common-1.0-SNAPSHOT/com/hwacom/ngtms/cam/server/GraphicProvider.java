/*    */ package com.hwacom.ngtms.cam.server;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.EntryObject;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphicConfig;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.annotation.WebServlet;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.context.support.SpringBeanAutowiringSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebServlet(name = "GraphicProvider", urlPatterns = {"/api/graphicProvider"})
/*    */ public class GraphicProvider
/*    */   extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = -4413421584217398968L;
/* 35 */   private static final Logger logger = LoggerFactory.getLogger(GraphicProvider.class);
/*    */   
/*    */   @Autowired
/*    */   private HazelcastClient hzClient;
/*    */   
/*    */   public void init(ServletConfig config) throws ServletException {
/* 41 */     super.init(config);
/* 42 */     SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config
/* 43 */         .getServletContext());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 49 */     String backgorundTcCodeId = req.getParameter("backgroundGrapihcId");
/* 50 */     DisGraphicConfig graphicConfig = retrieveGraphicConfig(backgorundTcCodeId);
/* 51 */     resp.setContentType("image/png");
/* 52 */     resp.getOutputStream().write(graphicConfig.getPicture());
/*    */   }
/*    */   
/*    */   private DisGraphicConfig retrieveGraphicConfig(String tcCodeId) {
/* 56 */     EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     PredicateBuilder predicate = entryObject.get("graphicMode").equal(Integer.valueOf(3)).and((Predicate)entryObject.get("tcCodeId").equal(Integer.valueOf(Integer.parseInt(tcCodeId))));
/* 62 */     IMap<String, DisGraphicConfig> graphicConfigMap = this.hzClient.getIMap((HzDistObjEnum)DisHzMap.GraphicConfig);
/* 63 */     return graphicConfigMap.values((Predicate)predicate).iterator().next();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\am-common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cam\server\GraphicProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */