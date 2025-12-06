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
/*     */ @XmlRootElement(name="ct3VehicleList")
/*     */ public class Ct3VehicleListElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="value", type=ValueElement.class), @javax.xml.bind.annotation.XmlElement(name="byteArray", type=ByteArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="bitArray", type=BitArrayElement.class), @javax.xml.bind.annotation.XmlElement(name="paramsRef", type=ParamsRefElement.class)})
/*     */   protected List<XmlElement> valueOrByteArrayOrBitArray;
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */   @XmlAttribute(name="vehicleNo", required=true)
/*     */   protected int vehicleNo;
/*     */   @XmlAttribute(name="vehicleNoLength", required=true)
/*     */   protected int vehicleNoLength;
/*     */   @XmlAttribute(name="vehicleNoName", required=true)
/*     */   protected String vehicleNoName;
/*     */   @XmlAttribute(name="lenByteNo", required=true)
/*     */   protected int lenByteNo;
/*     */   @XmlAttribute(name="lenByteName", required=true)
/*     */   protected String lenByteName;
/*     */   @XmlAttribute(name="useMarshalHelper")
/*     */   protected Boolean useMarshalHelper;
/*     */   
/*     */   public List<XmlElement> getValueOrByteArrayOrBitArray()
/*     */   {
/* 107 */     if (this.valueOrByteArrayOrBitArray == null) {
/* 108 */       this.valueOrByteArrayOrBitArray = new ArrayList();
/*     */     }
/* 110 */     return this.valueOrByteArrayOrBitArray;
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
/* 122 */     return this.name;
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
/* 134 */     this.name = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getVehicleNo()
/*     */   {
/* 142 */     return this.vehicleNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVehicleNo(int value)
/*     */   {
/* 150 */     this.vehicleNo = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getVehicleNoLength()
/*     */   {
/* 158 */     return this.vehicleNoLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVehicleNoLength(int value)
/*     */   {
/* 166 */     this.vehicleNoLength = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getVehicleNoName()
/*     */   {
/* 178 */     return this.vehicleNoName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVehicleNoName(String value)
/*     */   {
/* 190 */     this.vehicleNoName = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLenByteNo()
/*     */   {
/* 198 */     return this.lenByteNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLenByteNo(int value)
/*     */   {
/* 206 */     this.lenByteNo = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLenByteName()
/*     */   {
/* 218 */     return this.lenByteName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLenByteName(String value)
/*     */   {
/* 230 */     this.lenByteName = value;
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
/* 242 */     return this.useMarshalHelper;
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
/* 254 */     this.useMarshalHelper = value;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\Ct3VehicleListElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */