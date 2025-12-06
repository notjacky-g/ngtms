/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlType(name = "")
/*     */ @XmlRootElement(name = "value")
/*     */ public class ValueElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlAttribute(name = "name", required = true)
/*     */   protected String name;
/*     */   @XmlAttribute(name = "type", required = true)
/*     */   protected ValueType type;
/*     */   @XmlAttribute(name = "minValue")
/*     */   protected Integer minValue;
/*     */   @XmlAttribute(name = "maxValue")
/*     */   protected Integer maxValue;
/*     */   @XmlAttribute(name = "minFloatValue")
/*     */   protected Double minFloatValue;
/*     */   @XmlAttribute(name = "maxFloatValue")
/*     */   protected Double maxFloatValue;
/*     */   @XmlAttribute(name = "maxLength")
/*     */   protected Integer maxLength;
/*     */   @XmlAttribute(name = "allowedValueSet")
/*     */   @XmlJavaTypeAdapter(Adapter1.class)
/*     */   protected int[] allowedValueSet;
/*     */   @XmlAttribute(name = "optional")
/*     */   protected Boolean optional;
/*     */   @XmlAttribute(name = "useMarshalHelper")
/*     */   protected Boolean useMarshalHelper;
/*     */   @XmlAttribute(name = "lengthOfNextList")
/*     */   protected Boolean lengthOfNextList;
/*     */   @XmlAttribute(name = "lengthOfNextByteArray")
/*     */   protected Boolean lengthOfNextByteArray;
/*     */   @XmlAttribute(name = "lengthField")
/*     */   protected Boolean lengthField;
/*     */   @XmlAttribute(name = "defaultValue")
/*     */   protected String defaultValue;
/*     */   
/*     */   public String getName() {
/*  95 */     return this.name;
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
/* 107 */     this.name = value;
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
/*     */   public ValueType getType() {
/* 119 */     return this.type;
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
/*     */   public void setType(ValueType value) {
/* 131 */     this.type = value;
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
/*     */   public Integer getMinValue() {
/* 143 */     return this.minValue;
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
/*     */   public void setMinValue(Integer value) {
/* 155 */     this.minValue = value;
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
/*     */   public Integer getMaxValue() {
/* 167 */     return this.maxValue;
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
/*     */   public void setMaxValue(Integer value) {
/* 179 */     this.maxValue = value;
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
/*     */   public Double getMinFloatValue() {
/* 191 */     return this.minFloatValue;
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
/*     */   public void setMinFloatValue(Double value) {
/* 203 */     this.minFloatValue = value;
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
/*     */   public Double getMaxFloatValue() {
/* 215 */     return this.maxFloatValue;
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
/*     */   public void setMaxFloatValue(Double value) {
/* 227 */     this.maxFloatValue = value;
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
/*     */   public Integer getMaxLength() {
/* 239 */     return this.maxLength;
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
/*     */   public void setMaxLength(Integer value) {
/* 251 */     this.maxLength = value;
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
/*     */   public int[] getAllowedValueSet() {
/* 263 */     return this.allowedValueSet;
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
/*     */   public void setAllowedValueSet(int[] value) {
/* 275 */     this.allowedValueSet = value;
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
/*     */   public Boolean isOptional() {
/* 287 */     return this.optional;
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
/*     */   public void setOptional(Boolean value) {
/* 299 */     this.optional = value;
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
/*     */   public Boolean isUseMarshalHelper() {
/* 311 */     return this.useMarshalHelper;
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
/*     */   public void setUseMarshalHelper(Boolean value) {
/* 323 */     this.useMarshalHelper = value;
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
/*     */   public boolean isLengthOfNextList() {
/* 335 */     if (this.lengthOfNextList == null) {
/* 336 */       return false;
/*     */     }
/* 338 */     return this.lengthOfNextList.booleanValue();
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
/*     */   public void setLengthOfNextList(Boolean value) {
/* 351 */     this.lengthOfNextList = value;
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
/*     */   public boolean isLengthOfNextByteArray() {
/* 363 */     if (this.lengthOfNextByteArray == null) {
/* 364 */       return false;
/*     */     }
/* 366 */     return this.lengthOfNextByteArray.booleanValue();
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
/*     */   public void setLengthOfNextByteArray(Boolean value) {
/* 379 */     this.lengthOfNextByteArray = value;
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
/*     */   public boolean isLengthField() {
/* 391 */     if (this.lengthField == null) {
/* 392 */       return false;
/*     */     }
/* 394 */     return this.lengthField.booleanValue();
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
/*     */   public void setLengthField(Boolean value) {
/* 407 */     this.lengthField = value;
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
/*     */   public String getDefaultValue() {
/* 419 */     return this.defaultValue;
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
/*     */   public void setDefaultValue(String value) {
/* 431 */     this.defaultValue = value;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\ValueElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */