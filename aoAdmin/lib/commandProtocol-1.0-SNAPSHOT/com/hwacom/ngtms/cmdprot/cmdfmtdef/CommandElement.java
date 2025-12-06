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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @XmlRootElement(name = "command")
/*     */ public class CommandElement
/*     */   extends XmlElement
/*     */ {
/*     */   @XmlElements({@XmlElement(name = "value", type = ValueElement.class), @XmlElement(name = "byteArray", type = ByteArrayElement.class), @XmlElement(name = "bitArray", type = BitArrayElement.class), @XmlElement(name = "fixBytes", type = FixBytesElement.class), @XmlElement(name = "list", type = ListElement.class), @XmlElement(name = "ct3VehicleList", type = Ct3VehicleListElement.class), @XmlElement(name = "paramsRef", type = ParamsRefElement.class), @XmlElement(name = "cmdParamsRef", type = CmdParamsRefElement.class)})
/*     */   protected List<XmlElement> valueOrByteArrayOrBitArray;
/*     */   @XmlAttribute(name = "id", required = true)
/*     */   @XmlJavaTypeAdapter(Adapter2.class)
/*     */   protected Integer id;
/*     */   @XmlAttribute(name = "name", required = true)
/*     */   protected String name;
/*     */   @XmlAttribute(name = "type")
/*     */   protected CmdType type;
/*     */   @XmlAttribute(name = "direction")
/*     */   protected DirectionType direction;
/*     */   @XmlAttribute(name = "responseId")
/*     */   @XmlJavaTypeAdapter(Adapter2.class)
/*     */   protected Integer responseId;
/*     */   @XmlAttribute(name = "override")
/*     */   protected Boolean override;
/*     */   
/*     */   public List<XmlElement> getValueOrByteArrayOrBitArray() {
/* 119 */     if (this.valueOrByteArrayOrBitArray == null) {
/* 120 */       this.valueOrByteArrayOrBitArray = new ArrayList<>();
/*     */     }
/* 122 */     return this.valueOrByteArrayOrBitArray;
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
/*     */   public Integer getId() {
/* 134 */     return this.id;
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
/*     */   public void setId(Integer value) {
/* 146 */     this.id = value;
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
/* 158 */     return this.name;
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
/* 170 */     this.name = value;
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
/*     */   public CmdType getType() {
/* 182 */     return this.type;
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
/*     */   public void setType(CmdType value) {
/* 194 */     this.type = value;
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
/*     */   public DirectionType getDirection() {
/* 206 */     return this.direction;
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
/*     */   public void setDirection(DirectionType value) {
/* 218 */     this.direction = value;
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
/*     */   public Integer getResponseId() {
/* 230 */     return this.responseId;
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
/*     */   public void setResponseId(Integer value) {
/* 242 */     this.responseId = value;
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
/*     */   public boolean isOverride() {
/* 254 */     if (this.override == null) {
/* 255 */       return false;
/*     */     }
/* 257 */     return this.override.booleanValue();
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
/*     */   public void setOverride(Boolean value) {
/* 270 */     this.override = value;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CommandElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */