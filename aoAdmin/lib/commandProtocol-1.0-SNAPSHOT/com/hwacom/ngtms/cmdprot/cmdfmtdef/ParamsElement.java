/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ @XmlType(name = "", propOrder = {"valueOrByteArrayOrBitArray"})
/*     */ @XmlRootElement(name = "params")
/*     */ public class ParamsElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlElements({@XmlElement(name = "value", type = ValueElement.class), @XmlElement(name = "byteArray", type = ByteArrayElement.class), @XmlElement(name = "bitArray", type = BitArrayElement.class), @XmlElement(name = "fixBytes", type = FixBytesElement.class), @XmlElement(name = "list", type = ListElement.class), @XmlElement(name = "paramsRef", type = ParamsRefElement.class)})
/*     */   protected List<XmlElement> valueOrByteArrayOrBitArray;
/*     */   @XmlAttribute(name = "name", required = true)
/*     */   protected String name;
/*     */   
/*     */   public List<XmlElement> getValueOrByteArrayOrBitArray() {
/*  95 */     if (this.valueOrByteArrayOrBitArray == null) {
/*  96 */       this.valueOrByteArrayOrBitArray = new ArrayList<>();
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
/*     */   
/*     */   public String getName() {
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
/*     */   
/*     */   public void setName(String value) {
/* 122 */     this.name = value;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\ParamsElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */