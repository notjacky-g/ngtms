/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.shared.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.OperatorExplorer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.OperatorDTO;
/*    */ import com.sencha.gxt.data.shared.ListStore;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OperatorPresenter
/*    */ {
/*    */   private final OperatorExplorer explorer;
/*    */   
/*    */   public OperatorPresenter(OperatorExplorer explorer) {
/* 24 */     this.explorer = explorer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void fetchSysOperators(final ListStore<OperatorDTO> listStore, final List<OperatorDTO> subSysAccounts) {
/* 29 */     RptEP.aoRptService.fetchSysOperators(new MethodCallback<List<OperatorDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<OperatorDTO> result)
/*    */           {
/* 33 */             listStore.clear();
/* 34 */             listStore.addAll(result);
/* 35 */             listStore.addAll(subSysAccounts);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 40 */             GWT.log("OperatorPresenter.fetchSysOperators failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\OperatorPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */