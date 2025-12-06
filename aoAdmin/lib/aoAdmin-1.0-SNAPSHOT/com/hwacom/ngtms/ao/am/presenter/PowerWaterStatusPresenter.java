/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.PowerWaterStatusViewer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.PowerWaterStatusDTO;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class PowerWaterStatusPresenter
/*    */ {
/*    */   private PowerWaterStatusViewer viewer;
/*    */   
/*    */   public PowerWaterStatusPresenter(PowerWaterStatusViewer viewer) {
/* 15 */     this.viewer = viewer;
/*    */   }
/*    */   
/*    */   public void getPowerWaterStatusList() {
/* 19 */     AoEP.aoService.getPowerWaterStatusList(new MethodCallback<PowerWaterStatusDTO>()
/*    */         {
/*    */           public void onSuccess(Method method, PowerWaterStatusDTO response)
/*    */           {
/* 23 */             if (response != null) {
/* 24 */               PowerWaterStatusPresenter.this.viewer.fillWater(response.getWaterStatusList());
/* 25 */               PowerWaterStatusPresenter.this.viewer.fillPower(response.getPowerStatusList());
/*    */             } 
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 31 */             GWT.log("NcuConfigSettingPresenter.getNcuConfig failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\PowerWaterStatusPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */