/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*    */ @XmlType(name = "")
/*    */ @XmlRootElement(name = "cmdParamsRef")
/*    */ public class CmdParamsRefElement
/*    */   extends XmlElement
/*    */ {
/*    */   @XmlAttribute(name = "direction", required = true)
/*    */   protected DirectionType direction;
/*    */   @XmlAttribute(name = "allowedCmdSet")
/*    */   @XmlJavaTypeAdapter(Adapter1.class)
/*    */   protected int[] allowedCmdSet;
/*    */   
/*    */   public DirectionType getDirection() {
/* 59 */     return this.direction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirection(DirectionType value) {
/* 71 */     this.direction = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getAllowedCmdSet() {
/* 83 */     return this.allowedCmdSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAllowedCmdSet(int[] value) {
/* 95 */     this.allowedCmdSet = value;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdParamsRefElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */