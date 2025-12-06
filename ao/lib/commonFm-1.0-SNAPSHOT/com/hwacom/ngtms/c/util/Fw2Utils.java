/*     */ package com.hwacom.ngtms.c.util;
/*     */ 
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmsPm;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Calendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fw2Utils
/*     */ {
/*     */   public static Calendar dhm2Calendar(DhmPm dhmPm)
/*     */   {
/*  28 */     Calendar calendar = Calendar.getInstance();
/*  29 */     calendar.set(5, dhmPm.day);
/*  30 */     calendar.set(11, dhmPm.hour);
/*  31 */     calendar.set(12, dhmPm.minute);
/*  32 */     calendar.set(13, 0);
/*  33 */     calendar.set(14, 0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  43 */     if (System.currentTimeMillis() < calendar.getTimeInMillis() - 300000L) {
/*  44 */       calendar.add(2, -1);
/*     */     }
/*  46 */     return calendar;
/*     */   }
/*     */   
/*     */   public static Calendar dhms2Calendar(DhmsPm dhmsPm) {
/*  50 */     Calendar calendar = Calendar.getInstance();
/*  51 */     calendar.set(5, dhmsPm.day);
/*  52 */     calendar.set(11, dhmsPm.hour);
/*  53 */     calendar.set(12, dhmsPm.minute);
/*  54 */     calendar.set(13, dhmsPm.second);
/*  55 */     calendar.set(14, 0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  65 */     if (System.currentTimeMillis() < calendar.getTimeInMillis() - 300000L) {
/*  66 */       calendar.add(2, -1);
/*     */     }
/*  68 */     return calendar;
/*     */   }
/*     */   
/*     */   public static Date dhm2Date(DhmPm dhmPm) {
/*  72 */     return dhm2Calendar(dhmPm).getTime();
/*     */   }
/*     */   
/*     */   public static Date dhms2Date(DhmsPm dhmsPm) {
/*  76 */     return dhms2Calendar(dhmsPm).getTime();
/*     */   }
/*     */   
/*     */   public static DhmPm calendar2Dhm(Calendar calendar) {
/*  80 */     DhmPm dhmPm = new DhmPm();
/*  81 */     dhmPm.day = calendar.get(5);
/*  82 */     dhmPm.hour = calendar.get(11);
/*  83 */     dhmPm.minute = calendar.get(12);
/*  84 */     return dhmPm;
/*     */   }
/*     */   
/*     */   public static DhmPm date2Dhm(Date date) {
/*  88 */     Calendar calendar = Calendar.getInstance();
/*  89 */     calendar.setTime(date);
/*  90 */     return calendar2Dhm(calendar);
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
/*     */ 
/*     */   public static byte[][] image2ByteArrays(BufferedImage bufferedImage, int colorDepth, int maxArraySize)
/*     */   {
/* 105 */     int pixelNo = bufferedImage.getHeight() * bufferedImage.getWidth();
/* 106 */     int totalBytesNo = pixelNo * colorDepth / 8;
/* 107 */     if (pixelNo * colorDepth % 8 != 0) { totalBytesNo++;
/*     */     }
/* 109 */     int arrayNo = totalBytesNo / maxArraySize;
/* 110 */     if (totalBytesNo % maxArraySize != 0) { arrayNo++;
/*     */     }
/* 112 */     byte[][] arrays = new byte[arrayNo][];
/* 113 */     int i = 0; for (int bytesNo = 0; i < arrayNo; i++) {
/* 114 */       if (bytesNo + maxArraySize <= totalBytesNo) {
/* 115 */         arrays[i] = new byte[maxArraySize];
/* 116 */         bytesNo += maxArraySize;
/*     */       } else {
/* 118 */         arrays[i] = new byte[totalBytesNo - bytesNo];
/*     */       }
/*     */     }
/* 121 */     int offset = 0;
/* 122 */     int curArrayIndex = 0;
/* 123 */     byte[] curArray = arrays[0];
/* 124 */     int mask = 1;
/* 125 */     for (int y = 0; y < bufferedImage.getHeight(); y++) {
/* 126 */       for (int x = 0; x < bufferedImage.getWidth(); x++) {
/* 127 */         int color = bufferedImage.getRGB(x, y);
/*     */         
/*     */ 
/* 130 */         for (int c = 0; c < colorDepth; c++) {
/* 131 */           if (mask > 255) {
/* 132 */             mask = 1;
/* 133 */             offset++;
/* 134 */             if (offset >= maxArraySize) {
/* 135 */               offset = 0;
/* 136 */               curArrayIndex++;
/* 137 */               curArray = arrays[curArrayIndex];
/*     */             }
/*     */           }
/* 140 */           switch (c) {
/*     */           case 0: 
/* 142 */             if ((color & 0xFF0000) > 2097152) {
/* 143 */               curArray[offset] = ((byte)(curArray[offset] | mask));
/*     */             }
/*     */             break;
/*     */           case 1: 
/* 147 */             if ((color & 0xFF00) > 8192) {
/* 148 */               curArray[offset] = ((byte)(curArray[offset] | mask));
/*     */             }
/*     */             break;
/*     */           case 2: 
/* 152 */             if ((color & 0xFF) > 32) {
/* 153 */               curArray[offset] = ((byte)(curArray[offset] | mask));
/*     */             }
/*     */             
/*     */             break;
/*     */           }
/*     */           
/* 159 */           mask <<= 1;
/*     */         }
/*     */       }
/*     */     }
/* 163 */     return arrays;
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
/*     */ 
/*     */   public static BufferedImage byteArrays2Image(int width, int height, int colorDepth, byte[][] byteArrays)
/*     */   {
/* 178 */     BufferedImage bufferedImage = new BufferedImage(width, height, 5);
/* 179 */     int rgb = 0;
/* 180 */     int x = 0;
/* 181 */     int y = 0;
/* 182 */     int i = 0; for (int c = 0; i < byteArrays.length; i++) {
/* 183 */       byte[] array = byteArrays[i];
/* 184 */       for (int j = 0; j < array.length; j++) {
/* 185 */         int mask = 1;
/* 186 */         int value = array[j] & 0xFF;
/* 187 */         for (int k = 0; k < 8; k++) {
/* 188 */           switch (c) {
/*     */           case 0: 
/* 190 */             if ((value & mask) != 0) rgb |= 0xFF0000;
/*     */             break;
/*     */           case 1: 
/* 193 */             if ((value & mask) != 0) rgb |= 0xFF00;
/*     */             break;
/*     */           case 2: 
/* 196 */             if ((value & mask) != 0) { rgb |= 0xFF;
/*     */             }
/*     */             break;
/*     */           }
/*     */           
/* 201 */           mask <<= 1;
/* 202 */           c++;
/* 203 */           if (c >= colorDepth) {
/* 204 */             c = 0;
/* 205 */             bufferedImage.setRGB(x, y, rgb);
/* 206 */             rgb = 0;
/* 207 */             x++;
/* 208 */             if (x >= width) {
/* 209 */               x = 0;
/* 210 */               y++;
/*     */               
/* 212 */               if (y >= height) break;
/*     */             }
/*     */           }
/*     */         }
/* 216 */         if (y >= height) break;
/*     */       }
/*     */     }
/* 219 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public static int byteArrays2ImageCheckHeight(int width, int colorDepth, byte[][] byteArrays) {
/* 223 */     int length = 0;
/* 224 */     for (int i = 0; i < byteArrays.length; i++) {
/* 225 */       byte[] array = byteArrays[i];
/* 226 */       length += array.length;
/*     */     }
/* 228 */     int height = length * 8 / colorDepth / width;
/* 229 */     return height;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[][] fullColorImage2ByteArrays(BufferedImage bufferedImage, int maxArraySize)
/*     */   {
/* 240 */     int pixelNo = bufferedImage.getHeight() * bufferedImage.getWidth();
/* 241 */     int totalBytesNo = pixelNo * 3;
/*     */     
/* 243 */     int arrayNo = totalBytesNo / maxArraySize;
/* 244 */     if (totalBytesNo % maxArraySize != 0) { arrayNo++;
/*     */     }
/* 246 */     byte[][] arrays = new byte[arrayNo][];
/* 247 */     int i = 0; for (int bytesNo = 0; i < arrayNo; i++) {
/* 248 */       if (bytesNo + maxArraySize <= totalBytesNo) {
/* 249 */         arrays[i] = new byte[maxArraySize];
/* 250 */         bytesNo += maxArraySize;
/*     */       } else {
/* 252 */         arrays[i] = new byte[totalBytesNo - bytesNo];
/*     */       }
/*     */     }
/* 255 */     int offset = 0;
/* 256 */     int curArrayIndex = 0;
/* 257 */     byte[] curArray = arrays[0];
/* 258 */     for (int y = 0; y < bufferedImage.getHeight(); y++) {
/* 259 */       for (int x = 0; x < bufferedImage.getWidth(); x++) {
/* 260 */         int color = bufferedImage.getRGB(x, y);
/*     */         
/*     */ 
/* 263 */         for (int c = 0; c < 3; c++) {
/* 264 */           if (offset >= maxArraySize) {
/* 265 */             offset = 0;
/* 266 */             curArrayIndex++;
/* 267 */             curArray = arrays[curArrayIndex];
/*     */           }
/* 269 */           switch (c) {
/*     */           case 0: 
/* 271 */             curArray[offset] = ((byte)(color >> 16 & 0xFF));
/* 272 */             break;
/*     */           case 1: 
/* 274 */             curArray[offset] = ((byte)(color >> 8 & 0xFF));
/* 275 */             break;
/*     */           case 2: 
/* 277 */             curArray[offset] = ((byte)(color & 0xFF));
/* 278 */             break;
/*     */           }
/*     */           
/*     */           
/* 282 */           offset++;
/*     */         }
/*     */       }
/*     */     }
/* 286 */     return arrays;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BufferedImage byteArrays2FullColorImage(int width, int height, byte[][] byteArrays)
/*     */   {
/* 298 */     BufferedImage bufferedImage = new BufferedImage(width, height, 5);
/* 299 */     int rgb = 0;
/* 300 */     int x = 0;
/* 301 */     int y = 0;
/* 302 */     int i = 0; for (int c = 0; i < byteArrays.length; i++) {
/* 303 */       byte[] array = byteArrays[i];
/* 304 */       for (int j = 0; j < array.length; j++) {
/* 305 */         int value = array[j] & 0xFF;
/* 306 */         switch (c) {
/*     */         case 0: 
/* 308 */           rgb |= value << 16;
/* 309 */           break;
/*     */         case 1: 
/* 311 */           rgb |= value << 8;
/* 312 */           break;
/*     */         case 2: 
/* 314 */           rgb |= value;
/* 315 */           bufferedImage.setRGB(x, y, rgb);
/* 316 */           rgb = 0;
/* 317 */           x++;
/* 318 */           if (x >= width) {
/* 319 */             x = 0;
/* 320 */             y++;
/*     */             
/* 322 */             if (y >= height) {
/*     */               break;
/*     */             }
/*     */           }
/*     */           break;
/*     */         }
/* 328 */         c++;
/* 329 */         if (c >= 3) c = 0;
/*     */       }
/* 331 */       if (y >= height) break;
/*     */     }
/* 333 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public static int byteArrays2FullColorImageCheckHeight(int width, byte[][] byteArrays) {
/* 337 */     int length = 0;
/* 338 */     for (int i = 0; i < byteArrays.length; i++) {
/* 339 */       byte[] array = byteArrays[i];
/* 340 */       length += array.length;
/*     */     }
/* 342 */     int height = length / (3 * width);
/* 343 */     return height;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String byte2VersionNoString(int version_no)
/*     */   {
/* 354 */     int major = (version_no & 0xF0) >> 4;
/* 355 */     int minor = version_no & 0xF;
/*     */     
/* 357 */     return major + "." + minor;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\util\Fw2Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */