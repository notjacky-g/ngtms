/*     */ package com.hwacom.ngtms.ao.am.util;
/*     */ 
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.i18n.client.NumberFormat;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceCategoryDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.DirectionDTO;
/*     */ import com.hwacom.ngtms.room.shared.EventCode;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringConverter
/*     */ {
/*     */   public static String mileageToString(int mileage, DeviceCategoryDTO deviceCategoryDTO) {
/*  28 */     if (deviceCategoryDTO == null)
/*  29 */       return ""; 
/*  30 */     if (deviceCategoryDTO.getId().equals("HOST"))
/*  31 */       return ""; 
/*  32 */     if (mileage == 0) {
/*  33 */       return "";
/*     */     }
/*  35 */     String suffix = NumberFormat.getFormat("000").format((mileage % 1000));
/*  36 */     return (mileage / 1000) + "k+" + suffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTypeFromEventCode(EventCode eventCode) {
/*  41 */     switch (eventCode) {
/*     */       case ALL:
/*  43 */         return "卡片開啟";
/*     */       case PRIMARY_ABNORMAL_R:
/*  45 */         return "密碼開啟";
/*     */     } 
/*  47 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFormattedTime(Date time) {
/*  52 */     if (time == null) {
/*  53 */       return "";
/*     */     }
/*  55 */     DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
/*  56 */     return formatter.format(time);
/*     */   }
/*     */   
/*     */   public static String getAlarmState(Integer degree) {
/*  60 */     switch (degree.intValue()) {
/*     */       case 0:
/*  62 */         return "正常";
/*     */     } 
/*  64 */     return "異常";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDirectionName(DirectionDTO direction) {
/*  69 */     if (direction.getDirection() == null) {
/*  70 */       return "全部";
/*     */     }
/*  72 */     switch (direction.getDirection()) {
/*     */       case ALL:
/*  74 */         return "東";
/*     */       case PRIMARY_ABNORMAL_R:
/*  76 */         return "東西";
/*     */       case PRIMARY_ABNORMAL_S:
/*  78 */         return "北";
/*     */       case PRIMARY_ABNORMAL_T:
/*  80 */         return "南北";
/*     */       case SECONDARY_ABNORMAL_R:
/*  82 */         return "南";
/*     */       case SECONDARY_ABNORMAL_S:
/*  84 */         return "西";
/*     */     } 
/*  86 */     return direction.getDirection().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getAlarmTypeName(AlarmTypeDTO alarmType) {
/*  91 */     if (alarmType == null || alarmType.getAlarmType() == null) {
/*  92 */       return "";
/*     */     }
/*  94 */     if (alarmType.isAll()) {
/*  95 */       return "全部";
/*     */     }
/*  97 */     switch (alarmType.getAlarmType()) {
/*     */       case ALL:
/*  99 */         return "全部";
/*     */       case PRIMARY_ABNORMAL_R:
/* 101 */         return "一次側(R)";
/*     */       case PRIMARY_ABNORMAL_S:
/* 103 */         return "一次側(S)";
/*     */       case PRIMARY_ABNORMAL_T:
/* 105 */         return "一次側(T)";
/*     */       case SECONDARY_ABNORMAL_R:
/* 107 */         return "二次側(R)";
/*     */       case SECONDARY_ABNORMAL_S:
/* 109 */         return "二次側(S)";
/*     */       case SECONDARY_ABNORMAL_T:
/* 111 */         return "二次側(T)";
/*     */       case BRANCH_CIRCUIT_ABNORMAL_1:
/* 113 */         return "分迴路(1)";
/*     */       case BRANCH_CIRCUIT_ABNORMAL_2:
/* 115 */         return "分迴路(2)";
/*     */       case BRANCH_CIRCUIT_ABNORMAL_3:
/* 117 */         return "分迴路(3)";
/*     */       case BRANCH_CIRCUIT_ABNORMAL_4:
/* 119 */         return "分迴路(4)";
/*     */       case BRANCH_CIRCUIT_ABNORMAL_5:
/* 121 */         return "分迴路(5)";
/*     */     } 
/* 123 */     return alarmType.getAlarmType().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMappingCctvDevice(String backgroundId) {
/* 128 */     if (backgroundId == null) {
/* 129 */       return null;
/*     */     }
/* 131 */     String resultName = null;
/* 132 */     switch (backgroundId) {
/*     */       case "caotunRoom":
/* 134 */         resultName = "3030231";
/*     */         break;
/*     */       case "centralControlCenterRoom3F":
/* 137 */         resultName = "3030137";
/*     */         break;
/*     */       case "centralControlCenterRoom4F":
/* 140 */         resultName = "3030133";
/*     */         break;
/*     */       case "changhuaRoom1F":
/* 143 */         resultName = "3030153";
/*     */         break;
/*     */       case "changhuaRoom2F":
/* 146 */         resultName = "3030152";
/*     */         break;
/*     */       case "changhuaSystemRoom":
/* 149 */         resultName = "3030313";
/*     */         break;
/*     */       case "dajiaRoom1F":
/* 152 */         resultName = "3030283";
/*     */         break;
/*     */       case "dajiaRoom2F":
/* 155 */         resultName = "3030282";
/*     */         break;
/*     */       case "dounanRoom1F":
/* 158 */         resultName = "3030175";
/*     */         break;
/*     */       case "dounanRoom2F":
/* 161 */         resultName = "3030174";
/*     */         break;
/*     */       case "eastCaotunRoom":
/* 164 */         resultName = "3030369";
/*     */         break;
/*     */       case "guoxingNO1WestExitRoom1F":
/* 167 */         resultName = "3030377";
/*     */         break;
/*     */       case "guoxingNO2WestExitRoom3F":
/* 170 */         resultName = "3030451";
/*     */         break;
/*     */       case "houlongRoom1F":
/* 173 */         resultName = "3030264";
/*     */         break;
/*     */       case "houlongRoom2F":
/* 176 */         resultName = "3030263";
/*     */         break;
/*     */       case "hsihuRoom1F":
/* 179 */         resultName = "3030272";
/*     */         break;
/*     */       case "hsihuRoom2F":
/* 182 */         resultName = "3030271";
/*     */         break;
/*     */       case "linneiRoom1F":
/* 185 */         resultName = "3030338";
/*     */         break;
/*     */       case "linneiRoom2F":
/* 188 */         resultName = "3030337";
/*     */         break;
/*     */       case "miaoliRoom":
/* 191 */         resultName = "3030023";
/*     */         break;
/*     */       case "mingjianRoom1F":
/* 194 */         resultName = "3030242";
/*     */         break;
/*     */       case "mingjianRoom2F":
/* 197 */         resultName = "3030241";
/*     */         break;
/*     */       case "nantouRoom":
/* 200 */         resultName = "3030237";
/*     */         break;
/*     */       case "puilEastExitRoom3F":
/* 203 */         resultName = "3030471";
/*     */         break;
/*     */       case "shimizuRoom1F":
/* 206 */         resultName = "3030295";
/*     */         break;
/*     */       case "shimizuRoom2F":
/* 209 */         resultName = "3030294";
/*     */         break;
/*     */       case "taianRoom1F":
/* 212 */         resultName = "3030039";
/*     */         break;
/*     */       case "taianRoom2F":
/* 215 */         resultName = "3030038";
/*     */         break;
/*     */       case "yuanlinRoom1F":
/* 218 */         resultName = "3030069";
/*     */         break;
/*     */       case "yuanlinRoom2F":
/* 221 */         resultName = "3030068";
/*     */         break;
/*     */       case "zhonggangCreekRoom1F":
/* 224 */         resultName = "3030014";
/*     */         break;
/*     */       case "zhonggangCreekRoom2F":
/* 227 */         resultName = "3030013";
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 232 */     return resultName;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\a\\util\StringConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */