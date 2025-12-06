/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.view.UserViewer;
/*    */ import com.hwacom.ngtms.common.am.AccountEP;
/*    */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
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
/*    */ public class UserPresenter
/*    */ {
/*    */   private UserViewer viewer;
/*    */   
/*    */   public UserPresenter(UserViewer viewer) {
/* 22 */     this.viewer = viewer;
/* 23 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 27 */     AccountEP.accountService.getUsers(new MethodCallback<List<UserDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<UserDTO> result)
/*    */           {
/* 31 */             if (result != null) {
/* 32 */               UserPresenter.this.viewer.init(result);
/*    */             }
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 38 */             GWT.log("UserPresenter.getUsers failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\UserPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */