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
/*     */ @XmlRootElement(name="list")
/*     */ public class ListElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="value", type=ValueElement.class), @javax.xml.bind.annotation.XmlElement(name="byteArray", type=ByteArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="bitArray", type=BitArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="paramsRef", type=ParamsRefElement.class), @javax.xml.bind.annotation.XmlElement(name="list", type=ListElement.class)})
/*     */   protected List<XmlElement> valueOrByteArrayOrBitArray;
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */   @XmlAttribute(name="lenByteNo", required=true)
/*     */   protected int lenByteNo;
/*     */   @XmlAttribute(name="lenFieldName")
/*     */   protected String lenFieldName;
/*     */   @XmlAttribute(name="useMarshalHelper")
/*     */   protected Boolean useMarshalHelper;
/*     */   
/*     */   public List<XmlElement> getValueOrByteArrayOrBitArray()
/*     */   {
/* 101 */     if (this.valueOrByteArrayOrBitArray == null) {
/* 102 */       this.valueOrByteArrayOrBitArray = new ArrayList();
/*     */     }
/* 104 */     return this.valueOrByteArrayOrBitArray;
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
/* 116 */     return this.name;
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
/* 128 */     this.name = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLenByteNo()
/*     */   {
/* 136 */     return this.lenByteNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLenByteNo(int value)
/*     */   {
/* 144 */     this.lenByteNo = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLenFieldName()
/*     */   {
/* 156 */     return this.lenFieldName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLenFieldName(String value)
/*     */   {
/* 168 */     this.lenFieldName = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean isUseMarshalHelper()
/*     */   {
/* 180 */     return this.useMarshalHelper;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUseMarshalHelper(Boolean value)
/*     */   {
/* 192 */     this.useMarshalHelper = value;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\ListElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */