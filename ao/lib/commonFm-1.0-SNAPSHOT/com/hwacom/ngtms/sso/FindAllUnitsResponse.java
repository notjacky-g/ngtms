/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="findAllUnitsResponse", propOrder={"unitValue"})
/*    */ public class FindAllUnitsResponse
/*    */ {
/*    */   protected List<SsoJobUnitDTO> unitValue;
/*    */   
/*    */   public List<SsoJobUnitDTO> getUnitValue()
/*    */   {
/* 61 */     if (this.unitValue == null) {
/* 62 */       this.unitValue = new ArrayList();
/*    */     }
/* 64 */     return this.unitValue;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\FindAllUnitsResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */