/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"valueOrByteArrayOrBitArray"})
/*     */ @XmlRootElement(name="params")
/*     */ public class ParamsElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="value", type=ValueElement.class), @javax.xml.bind.annotation.XmlElement(name="byteArray", type=ByteArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="bitArray", type=BitArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="fixBytes", type=FixBytesElement.class), @javax.xml.bind.annotation.XmlElement(name="list", type=ListElement.class), @javax.xml.bind.annotation.XmlElement(name="paramsRef", type=ParamsRefElement.class)})
/*     */   protected List<XmlElement> valueOrByteArrayOrBitArray;
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */   
/*     */   public List<XmlElement> getValueOrByteArrayOrBitArray()
/*     */   {
/*  95 */     if (this.valueOrByteArrayOrBitArray == null) {
/*  96 */       this.valueOrByteArrayOrBitArray = new ArrayList();
/*     */     }
/*  98 */     return this.valueOrByteArrayOrBitArray;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 110 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String value)
/*     */   {
/* 122 */     this.name = value;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\ParamsElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */