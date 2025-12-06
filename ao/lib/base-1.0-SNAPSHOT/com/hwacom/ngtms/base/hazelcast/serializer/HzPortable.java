/*     */ package com.hwacom.ngtms.base.hazelcast.serializer;
/*     */ 
/*     */ import com.hazelcast.nio.serialization.Portable;
/*     */ import com.hazelcast.nio.serialization.PortableReader;
/*     */ import com.hazelcast.nio.serialization.PortableWriter;
/*     */ import com.ibm.icu.impl.IllegalIcuArgumentException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javassist.Modifier;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HzPortable
/*     */   implements Portable
/*     */ {
/*  34 */   private static final Logger logger = LoggerFactory.getLogger(HzPortable.class);
/*     */   
/*     */   private static final String NULL_POSTFIX = "_IS_NULL";
/*     */   
/*  38 */   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
/*     */   
/*  40 */   private static final Map<String, Integer> classIdMap = new HashMap();
/*     */   
/*     */   public static void registerClass(int classId, Class<? extends Portable> clazz) {
/*  43 */     if (!classIdMap.containsKey(clazz.getName())) {
/*  44 */       classIdMap.put(clazz.getName(), Integer.valueOf(classId));
/*     */     } else {
/*  46 */       throw new IllegalIcuArgumentException("Duplicated register class, class:" + clazz.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getFactoryId()
/*     */   {
/*  52 */     return 1;
/*     */   }
/*     */   
/*     */   public int getClassId()
/*     */   {
/*  57 */     if (classIdMap.containsKey(getClass().getName())) {
/*  58 */       return ((Integer)classIdMap.get(getClass().getName())).intValue();
/*     */     }
/*  60 */     return 0;
/*     */   }
/*     */   
/*     */   public void writePortable(PortableWriter writer)
/*     */     throws IOException
/*     */   {
/*  66 */     for (Field field : getClass().getDeclaredFields())
/*     */     {
/*  68 */       if (!Modifier.isFinal(field.getModifiers()))
/*     */       {
/*     */ 
/*  71 */         if (!Modifier.isTransient(field.getModifiers()))
/*     */         {
/*     */ 
/*  74 */           field.setAccessible(true);
/*  75 */           String fieldName = field.getName();
/*     */           try {
/*  77 */             obj = field.get(this);
/*     */           } catch (IllegalArgumentException|IllegalAccessException e) { Object obj;
/*  79 */             logger.warn("Portable serializer failed! class:'{}', field:'{}'", new Object[] {
/*     */             
/*  81 */               getClass(), field
/*  82 */               .getName(), e });
/*     */             
/*  84 */             throw new IOException(e); }
/*     */           Object obj;
/*  86 */           Class<?> type = field.getType();
/*  87 */           if (obj == null) {
/*  88 */             writer.writeBoolean(fieldName + "_IS_NULL", true);
/*  89 */           } else if (!type.isPrimitive()) {
/*  90 */             writer.writeBoolean(fieldName + "_IS_NULL", false);
/*     */           }
/*  92 */           if ((type.equals(Boolean.TYPE)) || (type.equals(Boolean.class))) {
/*  93 */             if (obj != null) writer.writeBoolean(fieldName, ((Boolean)obj).booleanValue()); else
/*  94 */               writer.writeBoolean(fieldName, false);
/*  95 */           } else if ((type.equals(Byte.TYPE)) || (type.equals(Byte.class))) {
/*  96 */             if (obj != null) writer.writeByte(fieldName, ((Byte)obj).byteValue()); else
/*  97 */               writer.writeByte(fieldName, (byte)0);
/*  98 */           } else if ((type.equals(Character.TYPE)) || (type.equals(Character.class))) {
/*  99 */             if (obj != null) writer.writeChar(fieldName, ((Character)obj).charValue()); else
/* 100 */               writer.writeChar(fieldName, 32);
/* 101 */           } else if ((type.equals(Integer.TYPE)) || (type.equals(Integer.class))) {
/* 102 */             if (obj != null) writer.writeInt(fieldName, ((Integer)obj).intValue()); else
/* 103 */               writer.writeInt(fieldName, 0);
/* 104 */           } else if ((type.equals(Short.TYPE)) || (type.equals(Short.class))) {
/* 105 */             if (obj != null) writer.writeShort(fieldName, ((Short)obj).shortValue()); else
/* 106 */               writer.writeShort(fieldName, (short)0);
/* 107 */           } else if ((type.equals(Long.TYPE)) || (type.equals(Long.class))) {
/* 108 */             if (obj != null) writer.writeLong(fieldName, ((Long)obj).longValue()); else
/* 109 */               writer.writeLong(fieldName, 0L);
/* 110 */           } else if ((type.equals(Float.TYPE)) || (type.equals(Float.class))) {
/* 111 */             if (obj != null) writer.writeFloat(fieldName, ((Float)obj).floatValue()); else
/* 112 */               writer.writeFloat(fieldName, 0.0F);
/* 113 */           } else if ((type.equals(Double.TYPE)) || (type.equals(Double.class))) {
/* 114 */             if (obj != null) writer.writeDouble(fieldName, ((Double)obj).doubleValue()); else
/* 115 */               writer.writeDouble(fieldName, 0.0D);
/* 116 */           } else if (Portable.class.isAssignableFrom(type)) {
/* 117 */             if (obj != null) writer.writePortable(fieldName, (Portable)obj); else
/*     */               try
/*     */               {
/* 120 */                 writer.writePortable(fieldName, (Portable)type.newInstance());
/*     */               } catch (Exception e) {
/* 122 */                 logger.warn("write protable field '{}' have exception!", fieldName, e);
/*     */               }
/* 124 */           } else if (type.equals(Date.class)) {
/* 125 */             if (obj != null) writer.writeUTF(fieldName, dateFormat.format((Date)obj)); else
/* 126 */               writer.writeUTF(fieldName, "");
/* 127 */           } else if (type.equals(Calendar.class)) {
/* 128 */             if (obj != null) writer.writeUTF(fieldName, dateFormat.format(((Calendar)obj).getTime())); else
/* 129 */               writer.writeUTF(fieldName, "");
/* 130 */           } else if (List.class.isAssignableFrom(type)) {
/* 131 */             ParameterizedType pType = (ParameterizedType)field.getGenericType();
/* 132 */             Class<?> gClass = (Class)pType.getActualTypeArguments()[0];
/* 133 */             if (obj != null) {
/* 134 */               List<Object> list = (List)obj;
/* 135 */               writeArray(writer, field.getName(), gClass, list.toArray());
/* 136 */             } else { writeArray(writer, field.getName(), gClass, (Object[])Array.newInstance(gClass, 0));
/* 137 */             } } else if (Set.class.isAssignableFrom(type)) {
/* 138 */             ParameterizedType pType = (ParameterizedType)field.getGenericType();
/* 139 */             Class<?> gClass = (Class)pType.getActualTypeArguments()[0];
/* 140 */             if (obj != null) {
/* 141 */               Set<Object> set = (Set)obj;
/* 142 */               writeArray(writer, fieldName, gClass, set.toArray());
/* 143 */             } else { writeArray(writer, field.getName(), gClass, (Object[])Array.newInstance(gClass, 0));
/* 144 */             } } else if (Map.class.isAssignableFrom(type)) {
/* 145 */             ParameterizedType pType = (ParameterizedType)field.getGenericType();
/* 146 */             Class<?> gClass1 = (Class)pType.getActualTypeArguments()[0];
/* 147 */             Class<?> gClass2 = (Class)pType.getActualTypeArguments()[1];
/* 148 */             if (obj != null) {
/* 149 */               Map<Object, Object> map = (Map)obj;
/* 150 */               writeArray(writer, fieldName + "_Key", gClass1, map.keySet().toArray());
/* 151 */               writeArray(writer, fieldName + "_Values", gClass2, map.values().toArray());
/*     */             } else {
/* 153 */               writeArray(writer, fieldName + "_Key", gClass1, (Object[])Array.newInstance(gClass1, 0));
/* 154 */               writeArray(writer, fieldName + "_Values", gClass2, 
/* 155 */                 (Object[])Array.newInstance(gClass2, 0));
/*     */             }
/*     */           }
/* 158 */           else if (obj != null) { writer.writeUTF(fieldName, obj.toString());
/* 159 */           } else { writer.writeUTF(fieldName, "");
/*     */           }
/*     */         } }
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeArray(PortableWriter writer, String fieldName, Class<?> clazz, Object[] array) throws IOException {
/* 166 */     if (Portable.class.isAssignableFrom(clazz)) {
/* 167 */       Portable[] protableArray = (Portable[])Arrays.copyOf(array, array.length, Portable[].class);
/* 168 */       writer.writePortableArray(fieldName, (Portable[])protableArray);
/* 169 */     } else if ((clazz.equals(Integer.class)) || (clazz.equals(Integer.TYPE))) {
/* 170 */       int[] values = new int[array.length];
/* 171 */       int index = 0;
/* 172 */       for (Object obj : array) {
/* 173 */         if (obj != null) {
/* 174 */           values[index] = ((Integer)obj).intValue();
/*     */         }
/* 176 */         index++;
/*     */       }
/* 178 */       writer.writeIntArray(fieldName, values);
/* 179 */     } else if ((clazz.equals(Long.class)) || (clazz.equals(Long.TYPE))) {
/* 180 */       long[] values = new long[array.length];
/* 181 */       int index = 0;
/* 182 */       for (Object obj : array) {
/* 183 */         if (obj != null) {
/* 184 */           values[index] = ((Long)obj).longValue();
/*     */         }
/* 186 */         index++;
/*     */       }
/* 188 */       writer.writeLongArray(fieldName, values);
/*     */     } else {
/* 190 */       String[] values = new String[array.length];
/* 191 */       int index = 0;
/* 192 */       for (Object obj : array) {
/* 193 */         if (obj != null) {
/* 194 */           values[index] = obj.toString();
/*     */         }
/* 196 */         index++;
/*     */       }
/* 198 */       writer.writeUTFArray(fieldName, values);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readPortable(PortableReader reader)
/*     */     throws IOException
/*     */   {
/* 205 */     for (Field field : getClass().getDeclaredFields()) {
/* 206 */       if (!Modifier.isFinal(field.getModifiers()))
/*     */       {
/*     */ 
/* 209 */         if (!Modifier.isTransient(field.getModifiers()))
/*     */         {
/*     */ 
/* 212 */           String fieldName = field.getName();
/* 213 */           Class<?> type = field.getType();
/* 214 */           field.setAccessible(true);
/* 215 */           if ((!type.isPrimitive()) && 
/* 216 */             (reader.readBoolean(field.getName() + "_IS_NULL"))) {
/*     */             try {
/* 218 */               field.set(this, null);
/*     */             } catch (IllegalArgumentException|IllegalAccessException e) {
/* 220 */               logger.warn("Portable deserializer check null failed! class:'{}', field:'{}'", new Object[] {
/*     */               
/* 222 */                 getClass(), field
/* 223 */                 .getName(), e });
/*     */               
/* 225 */               throw new IOException(e);
/*     */             }
/*     */           }
/*     */           else {
/*     */             Object valueObj;
/*     */             Object valueObj;
/* 231 */             if (Portable.class.isAssignableFrom(type)) {
/* 232 */               valueObj = reader.readPortable(fieldName); } else { Object valueObj;
/* 233 */               if (((type instanceof Class)) && (type.isEnum())) {
/* 234 */                 String valueStr = reader.readUTF(fieldName);
/* 235 */                 valueObj = Enum.valueOf(type, valueStr); } else { Object valueObj;
/* 236 */                 if ((type.equals(Date.class)) || (type.equals(Calendar.class)))
/*     */                 {
/* 238 */                   String valueStr = reader.readUTF(fieldName);
/*     */                   try {
/* 240 */                     date = dateFormat.parse(valueStr);
/*     */                   } catch (ParseException e) { Date date;
/* 242 */                     logger.warn("Portable deserializer failed! class:'{}', field:'{}'", new Object[] {
/*     */                     
/* 244 */                       getClass(), field
/* 245 */                       .getName(), e });
/*     */                     
/* 247 */                     throw new IOException(e); }
/*     */                   Date date;
/* 249 */                   Object valueObj; if (type.equals(Calendar.class)) {
/* 250 */                     Calendar c = Calendar.getInstance();
/* 251 */                     c.setTime(date);
/* 252 */                     valueObj = c;
/*     */                   } else {
/* 254 */                     valueObj = date;
/*     */                   } } else { Object valueObj;
/* 256 */                   if ((type.equals(Boolean.class)) || (type.equals(Boolean.TYPE))) {
/* 257 */                     valueObj = Boolean.valueOf(reader.readBoolean(fieldName)); } else { Object valueObj;
/* 258 */                     if ((type.equals(Character.class)) || (type.equals(Character.TYPE))) {
/* 259 */                       valueObj = Character.valueOf(reader.readChar(fieldName)); } else { Object valueObj;
/* 260 */                       if ((type.equals(Integer.class)) || (type.equals(Integer.TYPE))) {
/* 261 */                         valueObj = Integer.valueOf(reader.readInt(fieldName)); } else { Object valueObj;
/* 262 */                         if ((type.equals(Long.class)) || (type.equals(Long.TYPE))) {
/* 263 */                           valueObj = Long.valueOf(reader.readLong(fieldName)); } else { Object valueObj;
/* 264 */                           if ((type.equals(Double.class)) || (type.equals(Double.TYPE))) {
/* 265 */                             valueObj = Double.valueOf(reader.readDouble(fieldName)); } else { Object valueObj;
/* 266 */                             if ((type.equals(Short.class)) || (type.equals(Short.TYPE))) {
/* 267 */                               valueObj = Short.valueOf(reader.readShort(fieldName)); } else { Object valueObj;
/* 268 */                               if ((type.equals(Float.class)) || (type.equals(Float.TYPE))) {
/* 269 */                                 valueObj = Float.valueOf(reader.readFloat(fieldName)); } else { Object valueObj;
/* 270 */                                 if ((type.equals(Byte.class)) || (type.equals(Byte.TYPE))) {
/* 271 */                                   valueObj = Byte.valueOf(reader.readByte(fieldName));
/* 272 */                                 } else if ((List.class.isAssignableFrom(type)) || (Set.class.isAssignableFrom(type))) {
/*     */                                   try {
/* 274 */                                     valueObj = readArray(reader, field);
/*     */                                   } catch (InstantiationException|IllegalAccessException e) { Object valueObj;
/* 276 */                                     logger.warn("Portable deserializer failed! class:'{}', field:'{}'", new Object[] {
/*     */                                     
/* 278 */                                       getClass(), field
/* 279 */                                       .getName(), e });
/*     */                                     
/* 281 */                                     throw new IOException(e);
/*     */                                   }
/* 283 */                                 } else if (Map.class.isAssignableFrom(type)) {
/*     */                                   try {
/* 285 */                                     valueObj = readMap(reader, field);
/*     */                                   } catch (InstantiationException|IllegalAccessException e) { Object valueObj;
/* 287 */                                     logger.warn("Portable deserializer failed! class:'{}', field:'{}'", new Object[] {
/*     */                                     
/* 289 */                                       getClass(), field
/* 290 */                                       .getName(), e });
/*     */                                     
/* 292 */                                     throw new IOException(e);
/*     */                                   }
/*     */                                 } else
/* 295 */                                   valueObj = reader.readUTF(field.getName());
/*     */                               }
/*     */                             } } } } } } } } }
/* 298 */             field.setAccessible(true);
/*     */             try {
/* 300 */               field.set(this, valueObj);
/*     */             } catch (IllegalArgumentException|IllegalAccessException e) {
/* 302 */               logger.warn("Portable deserializer failed! class:'{}', field:'{}'", new Object[] {
/*     */               
/* 304 */                 getClass(), field
/* 305 */                 .getName(), e });
/*     */               
/* 307 */               throw new IOException(e);
/*     */             }
/*     */           }
/*     */         } }
/*     */     }
/*     */   }
/*     */   
/*     */   private Object readArray(PortableReader reader, Field field) throws IOException, InstantiationException, IllegalAccessException {
/* 315 */     Class<?> clazz = field.getType();
/* 316 */     ParameterizedType type = (ParameterizedType)field.getGenericType();
/* 317 */     Class<?> gClass = (Class)type.getActualTypeArguments()[0];
/* 318 */     Object[] itemArray = readArray(reader, field.getName(), gClass);
/* 319 */     if (List.class.isAssignableFrom(field.getType())) {
/* 320 */       if (clazz.equals(List.class)) {
/* 321 */         return Arrays.asList(itemArray);
/*     */       }
/* 323 */       List<Object> objList = (List)clazz.newInstance();
/* 324 */       objList.addAll(Arrays.asList(itemArray));
/* 325 */       return objList; }
/* 326 */     if (clazz.equals(Set.class)) { Set<Object> resultSet;
/*     */       Set<Object> resultSet;
/* 328 */       if (clazz.equals(Set.class)) {
/* 329 */         resultSet = new HashSet(itemArray.length);
/*     */       } else {
/* 331 */         resultSet = (Set)clazz.newInstance();
/*     */       }
/* 333 */       for (int i = 0; i < itemArray.length; i++) {
/* 334 */         resultSet.add(itemArray[i]);
/*     */       }
/* 336 */       return resultSet;
/*     */     }
/* 338 */     throw new IOException("this collection type deseriailizer not implement!");
/*     */   }
/*     */   
/*     */ 
/*     */   private Map<Object, Object> readMap(PortableReader reader, Field field)
/*     */     throws IOException, InstantiationException, IllegalAccessException
/*     */   {
/* 345 */     ParameterizedType type = (ParameterizedType)field.getGenericType();
/* 346 */     Class<?> gClass1 = (Class)type.getActualTypeArguments()[0];
/* 347 */     Class<?> gClass2 = (Class)type.getActualTypeArguments()[1];
/* 348 */     Object[] keys = readArray(reader, field.getName() + "_Key", gClass1);
/* 349 */     Object[] values = readArray(reader, field.getName() + "_Values", gClass2);
/* 350 */     if (keys.length != values.length)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 355 */       throw new IOException("deseriailiz class:" + field.getClass() + " field:" + field.getName() + " , amount of key and value is different in map!");
/*     */     }
/*     */     Map<Object, Object> map;
/*     */     Map<Object, Object> map;
/* 359 */     if (field.getType().equals(Map.class)) {
/* 360 */       map = new HashMap(keys.length);
/*     */     } else {
/* 362 */       map = (Map)field.getType().newInstance();
/*     */     }
/* 364 */     for (int i = 0; i < keys.length; i++) {
/* 365 */       map.put(keys[i], values[i]);
/*     */     }
/* 367 */     return map;
/*     */   }
/*     */   
/*     */   private Object[] readArray(PortableReader reader, String fieldName, Class<?> clazz) throws IOException
/*     */   {
/* 372 */     if (Portable.class.isAssignableFrom(clazz)) {
/* 373 */       Portable[] portableArray = reader.readPortableArray(fieldName);
/* 374 */       return portableArray; }
/* 375 */     if (clazz.equals(Long.class)) {
/* 376 */       long[] array = reader.readLongArray(fieldName);
/* 377 */       Long[] arrayLong = new Long[array.length];
/* 378 */       for (int i = 0; i < array.length; i++) {
/* 379 */         arrayLong[i] = Long.valueOf(array[i]);
/*     */       }
/* 381 */       return arrayLong;
/*     */     }
/*     */     
/* 384 */     return reader.readUTFArray(fieldName);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzPortable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */