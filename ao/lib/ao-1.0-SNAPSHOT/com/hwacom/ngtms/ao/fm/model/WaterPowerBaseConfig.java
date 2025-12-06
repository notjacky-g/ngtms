/*     */ package com.hwacom.ngtms.ao.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class WaterPowerBaseConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7026806896031916207L;
/*     */   @Id
/*     */   @Comment("機房序號")
/*     */   private String id;
/*     */   @Comment("機房名稱")
/*     */   private String displayName;
/*     */   @Comment("對應的RTU編號")
/*     */   private String rtuDeviceName;
/*     */   @Comment("用水_警報上限值(%)")
/*     */   private Integer waterAlarmUper;
/*     */   @Comment("用水_警報下限值(%)")
/*     */   private Integer waterAlarmLimit;
/*     */   @Comment("用電_警報上限值(%)")
/*     */   private Integer powerAlarmUper;
/*     */   @Comment("用電_警報下限值(%)")
/*     */   private Integer powerAlarmLimit;
/*     */   @Comment("用水_1月基準值")
/*     */   private Double water1base;
/*     */   @Comment("用水_2月基準值")
/*     */   private Double water2base;
/*     */   @Comment("用水_3月基準值")
/*     */   private Double water3base;
/*     */   @Comment("用水_4月基準值")
/*     */   private Double water4base;
/*     */   @Comment("用水_5月基準值")
/*     */   private Double water5base;
/*     */   @Comment("用水_6月基準值")
/*     */   private Double water6base;
/*     */   @Comment("用水_7月基準值")
/*     */   private Double water7base;
/*     */   @Comment("用水_8月基準值")
/*     */   private Double water8base;
/*     */   @Comment("用水_9月基準值")
/*     */   private Double water9base;
/*     */   @Comment("用水_10月基準值")
/*     */   private Double water10base;
/*     */   @Comment("用水_11月基準值")
/*     */   private Double water11base;
/*     */   @Comment("用水_12月基準值")
/*     */   private Double water12base;
/*     */   @Comment("用電_1月基準值")
/*     */   private Integer power1base;
/*     */   @Comment("用電_2月基準值")
/*     */   private Integer power2base;
/*     */   @Comment("用電_3月基準值")
/*     */   private Integer power3base;
/*     */   @Comment("用電_4月基準值")
/*     */   private Integer power4base;
/*     */   @Comment("用電_5月基準值")
/*     */   private Integer power5base;
/*     */   @Comment("用電_6月基準值")
/*     */   private Integer power6base;
/*     */   @Comment("用電_7月基準值")
/*     */   private Integer power7base;
/*     */   @Comment("用電_8月基準值")
/*     */   private Integer power8base;
/*     */   @Comment("用電_9月基準值")
/*     */   private Integer power9base;
/*     */   @Comment("用電_10月基準值")
/*     */   private Integer power10base;
/*     */   @Comment("用電_11月基準值")
/*     */   private Integer power11base;
/*     */   @Comment("用電_12月基準值")
/*     */   private Integer power12base;
/*     */   
/*     */   public String getId() {
/* 108 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 112 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 116 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) {
/* 120 */     this.displayName = displayName;
/*     */   }
/*     */   
/*     */   public String getRtuDeviceName() {
/* 124 */     return this.rtuDeviceName;
/*     */   }
/*     */   
/*     */   public void setRtuDeviceName(String rtuDeviceName) {
/* 128 */     this.rtuDeviceName = rtuDeviceName;
/*     */   }
/*     */   
/*     */   public Integer getWaterAlarmUper() {
/* 132 */     return this.waterAlarmUper;
/*     */   }
/*     */   
/*     */   public void setWaterAlarmUper(Integer waterAlarmUper) {
/* 136 */     this.waterAlarmUper = waterAlarmUper;
/*     */   }
/*     */   
/*     */   public Integer getWaterAlarmLimit() {
/* 140 */     return this.waterAlarmLimit;
/*     */   }
/*     */   
/*     */   public void setWaterAlarmLimit(Integer waterAlarmLimit) {
/* 144 */     this.waterAlarmLimit = waterAlarmLimit;
/*     */   }
/*     */   
/*     */   public Integer getPowerAlarmUper() {
/* 148 */     return this.powerAlarmUper;
/*     */   }
/*     */   
/*     */   public void setPowerAlarmUper(Integer powerAlarmUper) {
/* 152 */     this.powerAlarmUper = powerAlarmUper;
/*     */   }
/*     */   
/*     */   public Integer getPowerAlarmLimit() {
/* 156 */     return this.powerAlarmLimit;
/*     */   }
/*     */   
/*     */   public void setPowerAlarmLimit(Integer powerAlarmLimit) {
/* 160 */     this.powerAlarmLimit = powerAlarmLimit;
/*     */   }
/*     */   
/*     */   public Double getWater1base() {
/* 164 */     return this.water1base;
/*     */   }
/*     */   
/*     */   public void setWater1base(Double water1base) {
/* 168 */     this.water1base = water1base;
/*     */   }
/*     */   
/*     */   public Double getWater2base() {
/* 172 */     return this.water2base;
/*     */   }
/*     */   
/*     */   public void setWater2base(Double water2base) {
/* 176 */     this.water2base = water2base;
/*     */   }
/*     */   
/*     */   public Double getWater3base() {
/* 180 */     return this.water3base;
/*     */   }
/*     */   
/*     */   public void setWater3base(Double water3base) {
/* 184 */     this.water3base = water3base;
/*     */   }
/*     */   
/*     */   public Double getWater4base() {
/* 188 */     return this.water4base;
/*     */   }
/*     */   
/*     */   public void setWater4base(Double water4base) {
/* 192 */     this.water4base = water4base;
/*     */   }
/*     */   
/*     */   public Double getWater5base() {
/* 196 */     return this.water5base;
/*     */   }
/*     */   
/*     */   public void setWater5base(Double water5base) {
/* 200 */     this.water5base = water5base;
/*     */   }
/*     */   
/*     */   public Double getWater6base() {
/* 204 */     return this.water6base;
/*     */   }
/*     */   
/*     */   public void setWater6base(Double water6base) {
/* 208 */     this.water6base = water6base;
/*     */   }
/*     */   
/*     */   public Double getWater7base() {
/* 212 */     return this.water7base;
/*     */   }
/*     */   
/*     */   public void setWater7base(Double water7base) {
/* 216 */     this.water7base = water7base;
/*     */   }
/*     */   
/*     */   public Double getWater8base() {
/* 220 */     return this.water8base;
/*     */   }
/*     */   
/*     */   public void setWater8base(Double water8base) {
/* 224 */     this.water8base = water8base;
/*     */   }
/*     */   
/*     */   public Double getWater9base() {
/* 228 */     return this.water9base;
/*     */   }
/*     */   
/*     */   public void setWater9base(Double water9base) {
/* 232 */     this.water9base = water9base;
/*     */   }
/*     */   
/*     */   public Double getWater10base() {
/* 236 */     return this.water10base;
/*     */   }
/*     */   
/*     */   public void setWater10base(Double water10base) {
/* 240 */     this.water10base = water10base;
/*     */   }
/*     */   
/*     */   public Double getWater11base() {
/* 244 */     return this.water11base;
/*     */   }
/*     */   
/*     */   public void setWater11base(Double water11base) {
/* 248 */     this.water11base = water11base;
/*     */   }
/*     */   
/*     */   public Double getWater12base() {
/* 252 */     return this.water12base;
/*     */   }
/*     */   
/*     */   public void setWater12base(Double water12base) {
/* 256 */     this.water12base = water12base;
/*     */   }
/*     */   
/*     */   public Integer getPower1base() {
/* 260 */     return this.power1base;
/*     */   }
/*     */   
/*     */   public void setPower1base(Integer power1base) {
/* 264 */     this.power1base = power1base;
/*     */   }
/*     */   
/*     */   public Integer getPower2base() {
/* 268 */     return this.power2base;
/*     */   }
/*     */   
/*     */   public void setPower2base(Integer power2base) {
/* 272 */     this.power2base = power2base;
/*     */   }
/*     */   
/*     */   public Integer getPower3base() {
/* 276 */     return this.power3base;
/*     */   }
/*     */   
/*     */   public void setPower3base(Integer power3base) {
/* 280 */     this.power3base = power3base;
/*     */   }
/*     */   
/*     */   public Integer getPower4base() {
/* 284 */     return this.power4base;
/*     */   }
/*     */   
/*     */   public void setPower4base(Integer power4base) {
/* 288 */     this.power4base = power4base;
/*     */   }
/*     */   
/*     */   public Integer getPower5base() {
/* 292 */     return this.power5base;
/*     */   }
/*     */   
/*     */   public void setPower5base(Integer power5base) {
/* 296 */     this.power5base = power5base;
/*     */   }
/*     */   
/*     */   public Integer getPower6base() {
/* 300 */     return this.power6base;
/*     */   }
/*     */   
/*     */   public void setPower6base(Integer power6base) {
/* 304 */     this.power6base = power6base;
/*     */   }
/*     */   
/*     */   public Integer getPower7base() {
/* 308 */     return this.power7base;
/*     */   }
/*     */   
/*     */   public void setPower7base(Integer power7base) {
/* 312 */     this.power7base = power7base;
/*     */   }
/*     */   
/*     */   public Integer getPower8base() {
/* 316 */     return this.power8base;
/*     */   }
/*     */   
/*     */   public void setPower8base(Integer power8base) {
/* 320 */     this.power8base = power8base;
/*     */   }
/*     */   
/*     */   public Integer getPower9base() {
/* 324 */     return this.power9base;
/*     */   }
/*     */   
/*     */   public void setPower9base(Integer power9base) {
/* 328 */     this.power9base = power9base;
/*     */   }
/*     */   
/*     */   public Integer getPower10base() {
/* 332 */     return this.power10base;
/*     */   }
/*     */   
/*     */   public void setPower10base(Integer power10base) {
/* 336 */     this.power10base = power10base;
/*     */   }
/*     */   
/*     */   public Integer getPower11base() {
/* 340 */     return this.power11base;
/*     */   }
/*     */   
/*     */   public void setPower11base(Integer power11base) {
/* 344 */     this.power11base = power11base;
/*     */   }
/*     */   
/*     */   public Integer getPower12base() {
/* 348 */     return this.power12base;
/*     */   }
/*     */   
/*     */   public void setPower12base(Integer power12base) {
/* 352 */     this.power12base = power12base;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 357 */     int prime = 31;
/* 358 */     int result = 1;
/* 359 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 360 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 365 */     if (this == obj) return true; 
/* 366 */     if (obj == null) return false; 
/* 367 */     if (getClass() != obj.getClass()) return false; 
/* 368 */     WaterPowerBaseConfig other = (WaterPowerBaseConfig)obj;
/* 369 */     if (this.id == null)
/* 370 */     { if (other.id != null) return false;  }
/* 371 */     else if (!this.id.equals(other.id)) { return false; }
/* 372 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 377 */     return "WaterPowerBaseConfig [id=" + this.id + ", displayName=" + this.displayName + ", rtuDeviceName=" + this.rtuDeviceName + ", waterAlarmUper=" + this.waterAlarmUper + ", waterAlarmLimit=" + this.waterAlarmLimit + ", powerAlarmUper=" + this.powerAlarmUper + ", powerAlarmLimit=" + this.powerAlarmLimit + ", water1base=" + this.water1base + ", water2base=" + this.water2base + ", water3base=" + this.water3base + ", water4base=" + this.water4base + ", water5base=" + this.water5base + ", water6base=" + this.water6base + ", water7base=" + this.water7base + ", water8base=" + this.water8base + ", water9base=" + this.water9base + ", water10base=" + this.water10base + ", water11base=" + this.water11base + ", water12base=" + this.water12base + ", power1base=" + this.power1base + ", power2base=" + this.power2base + ", power3base=" + this.power3base + ", power4base=" + this.power4base + ", power5base=" + this.power5base + ", power6base=" + this.power6base + ", power7base=" + this.power7base + ", power8base=" + this.power8base + ", power9base=" + this.power9base + ", power10base=" + this.power10base + ", power11base=" + this.power11base + ", power12base=" + this.power12base + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\WaterPowerBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */