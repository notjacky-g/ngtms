/*     */ package com.hwacom.ngtms.ncc.tc.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteArrayUtils
/*     */ {
/*     */   public static final int INT_LEN = 4;
/*     */   public static final int SHORT_LEN = 2;
/*     */   
/*     */   public static byte[] shortToByteArray(int value)
/*     */   {
/*  29 */     byte[] result = new byte[2];
/*  30 */     shortToByteArray(value, result, 0);
/*  31 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void shortToByteArray(int value, byte[] dest, int offset)
/*     */   {
/*  42 */     for (int i = 0; i < 2; i++) {
/*  43 */       dest[(i + offset)] = ((byte)(value >> 8 * (2 - i - 1) & 0xFF));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static short shortValue(byte[] data)
/*     */   {
/*  55 */     return shortValue(data, 0, 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static short shortValue(byte[] data, int offset)
/*     */   {
/*  67 */     return shortValue(data, offset, 2);
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
/*     */   public static short shortValue(byte[] data, int offset, int length)
/*     */   {
/*  80 */     short result = 0;
/*  81 */     for (int i = 0; i < length; i++) {
/*  82 */       result = (short)(result | (data[(i + offset)] & 0xFF) << 8 * (length - i - 1));
/*     */     }
/*  84 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] intToByteArray(int value)
/*     */   {
/*  94 */     byte[] result = new byte[4];
/*  95 */     intToByteArray(value, result, 0);
/*  96 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void intToByteArray(int value, byte[] dest, int offset)
/*     */   {
/* 107 */     for (int i = 0; i < 4; i++) {
/* 108 */       dest[(i + offset)] = ((byte)(value >> 8 * (4 - i - 1) & 0xFF));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int intValue(byte[] data)
/*     */   {
/* 120 */     return intValue(data, 0, 4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int intValue(byte[] data, int offset)
/*     */   {
/* 131 */     return intValue(data, offset, 4);
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
/*     */   public static int intValue(byte[] data, int offset, int length)
/*     */   {
/* 144 */     int result = 0;
/* 145 */     for (int i = 0; i < length; i++) {
/* 146 */       result |= (data[(i + offset)] & 0xFF) << 8 * (length - i - 1);
/*     */     }
/*     */     
/* 149 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte[] data)
/*     */   {
/* 159 */     if (data == null) return "";
/* 160 */     return toHexString(data, 0, data.length, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte[] data, String delim)
/*     */   {
/* 171 */     return toHexString(data, 0, data.length, delim);
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
/*     */   public static String toHexString(byte[] data, int offset, int len, String delim)
/*     */   {
/* 184 */     if (data == null) { return "";
/*     */     }
/* 186 */     StringBuffer buf = new StringBuffer("");
/* 187 */     for (int i = 0; i < len; i++) {
/* 188 */       if ((0 <= data[(i + offset)]) && (data[(i + offset)] <= 15)) {
/* 189 */         buf.append("0");
/* 190 */         buf.append(Integer.toHexString(data[(i + offset)] & 0xF));
/*     */       } else {
/* 192 */         buf.append(Integer.toHexString(data[(i + offset)] & 0xFF));
/*     */       }
/* 194 */       if ((i != len - 1) && 
/* 195 */         (delim != null)) { buf.append(delim);
/*     */       }
/*     */     }
/* 198 */     return buf.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte data)
/*     */   {
/* 208 */     return toHexString(new byte[] { data }, 0, 1, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] hexToByteArray(String hex)
/*     */   {
/* 218 */     if (hex == null) return null;
/* 219 */     hex = hex.trim();
/*     */     try
/*     */     {
/* 222 */       byte[] data = new byte[hex.length() / 2 + hex.length() % 2];
/* 223 */       int i = 0; for (int j = 0; i < hex.length(); i += 2) {
/* 224 */         data[(j++)] = ((byte)Integer.parseInt(hex.substring(i, Math.min(hex.length(), i + 2)), 16));
/*     */       }
/* 226 */       return data;
/*     */     } catch (NumberFormatException e) {}
/* 228 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] hexToByteArray(String hex, String delim)
/*     */   {
/* 240 */     if ((delim == null) || (delim.length() == 0)) { return hexToByteArray(hex);
/*     */     }
/* 242 */     String[] str = hex.trim().split(delim);
/* 243 */     byte[] data = new byte[str.length];
/*     */     try {
/* 245 */       for (int i = 0; i < str.length; i++) {
/* 246 */         if (str[i].length() > 2) str[i] = str[i].substring(0, 2);
/* 247 */         data[i] = ((byte)Integer.parseInt(str[i], 16));
/*     */       }
/* 249 */       return data;
/*     */     } catch (NumberFormatException e) {}
/* 251 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] concat(byte[][] sub)
/*     */   {
/* 262 */     byte[] ret = null;
/* 263 */     int len = 0;
/*     */     
/* 265 */     for (int i = 0; i < sub.length; i++) {
/* 266 */       if (sub[i] != null) {
/* 267 */         len += sub[i].length;
/*     */       }
/*     */     }
/*     */     
/* 271 */     ret = new byte[len];
/* 272 */     int idx = 0;
/* 273 */     for (int i = 0; i < sub.length; i++) {
/* 274 */       if (sub[i] != null) {
/* 275 */         System.arraycopy(sub[i], 0, ret, idx, sub[i].length);
/* 276 */         idx += sub[i].length;
/*     */       }
/*     */     }
/*     */     
/* 280 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[][] splitByNumber(byte[] data, int number)
/*     */   {
/* 291 */     number = Math.min(data.length, number);
/* 292 */     int length = (int)Math.ceil(data.length / number);
/* 293 */     return split(data, 0, number, length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[][] splitByLength(byte[] data, int length)
/*     */   {
/* 304 */     int number = (int)Math.ceil(data.length / length);
/* 305 */     return split(data, 0, number, length);
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
/*     */   public static byte[][] split(byte[] data, int offset, int number, int length)
/*     */   {
/* 320 */     byte[][] ret = new byte[number][];
/* 321 */     for (int i = 0; i < ret.length; offset += length) {
/* 322 */       ret[i] = Arrays.copyOfRange(data, offset, offset + length);i++;
/*     */     }
/* 324 */     return ret;
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
/*     */   public static boolean compare(byte[] a, int aoffset, byte[] b, int boffset, int length)
/*     */   {
/* 338 */     if ((a.length < aoffset + length) || (b.length < boffset + length)) return false;
/* 339 */     for (int i = 0; i < length; i++) {
/* 340 */       if (a[(i + aoffset)] != b[(i + boffset)]) return false;
/*     */     }
/* 342 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] hexStringToFixLenByteArray(String hexString, int fixLength)
/*     */   {
/* 353 */     byte[] values = new byte[fixLength];
/* 354 */     int strIndex = hexString.length();
/*     */     try {
/* 356 */       while (fixLength > 0) {
/* 357 */         fixLength--;
/* 358 */         if (strIndex >= 2)
/*     */         {
/*     */ 
/*     */ 
/* 362 */           values[fixLength] = Integer.valueOf(hexString.substring(strIndex - 2, strIndex), 16).byteValue();
/* 363 */           strIndex -= 2;
/* 364 */         } else if (strIndex == 1)
/*     */         {
/*     */ 
/* 367 */           values[fixLength] = Integer.valueOf(hexString.substring(strIndex - 1, strIndex), 16).byteValue();
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 374 */       values = null;
/*     */     }
/* 376 */     return values;
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
/*     */   public static boolean safeCopy(byte[] src, int srcPos, byte[] dest, int destPos, int length)
/*     */   {
/* 390 */     if (!isArrayCopySafe(src, srcPos, length)) return false;
/* 391 */     if (!isArrayCopySafe(dest, destPos, length)) { return false;
/*     */     }
/* 393 */     System.arraycopy(src, srcPos, dest, destPos, length);
/* 394 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isArrayCopySafe(byte[] array, int pos, int length) {
/* 398 */     if (array == null) {
/* 399 */       return false;
/*     */     }
/* 401 */     if (pos < 0) {
/* 402 */       return false;
/*     */     }
/* 404 */     if (length < 0) {
/* 405 */       return false;
/*     */     }
/* 407 */     if (pos + length > array.length) {
/* 408 */       return false;
/*     */     }
/* 410 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\util\ByteArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */