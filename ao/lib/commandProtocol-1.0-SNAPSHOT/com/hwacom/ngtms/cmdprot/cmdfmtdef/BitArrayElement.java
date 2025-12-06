/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"bits"})
/*     */ @XmlRootElement(name="bitArray")
/*     */ public class BitArrayElement
/*     */   extends XmlElement
/*     */ {
/*     */   @javax.xml.bind.annotation.XmlElement(required=true)
/*     */   protected List<Bits> bits;
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */   @XmlAttribute(name="byteNo", required=true)
/*     */   protected int byteNo;
/*     */   @XmlAttribute(name="useMarshalHelper")
/*     */   protected Boolean useMarshalHelper;
/*     */   
/*     */   public List<Bits> getBits()
/*     */   {
/*  92 */     if (this.bits == null) {
/*  93 */       this.bits = new ArrayList();
/*     */     }
/*  95 */     return this.bits;
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
/* 107 */     return this.name;
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
/* 119 */     this.name = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getByteNo()
/*     */   {
/* 127 */     return this.byteNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setByteNo(int value)
/*     */   {
/* 135 */     this.byteNo = value;
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
/* 147 */     return this.useMarshalHelper;
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
/* 159 */     this.useMarshalHelper = value;
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
/*     */ 
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name="")
/*     */   public static class Bits
/*     */     extends XmlElement
/*     */   {
/*     */     @XmlAttribute(name="name", required=true)
/*     */     protected String name;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @XmlAttribute(name="bitsLenth", required=true)
/*     */     protected int bitsLenth;
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
/*     */     public String getName()
/*     */     {
/* 201 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void setName(String value)
/*     */     {
/* 213 */       this.name = value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getBitsLenth()
/*     */     {
/* 221 */       return this.bitsLenth;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void setBitsLenth(int value)
/*     */     {
/* 229 */       this.bitsLenth = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\BitArrayElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */