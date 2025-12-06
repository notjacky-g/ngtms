/*     */ package com.hwacom.ngtms.ao.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileUtils
/*     */ {
/*     */   public static String readFileAsString(String filename) throws IOException {
/*  33 */     BufferedReader reader = new BufferedReader(new FileReader(filename));
/*     */     
/*  35 */     StringBuilder sb = new StringBuilder(); String line;
/*  36 */     while ((line = reader.readLine()) != null) {
/*  37 */       sb.append(line + "\n");
/*     */     }
/*  39 */     reader.close();
/*  40 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> readFileAsListOfStrings(String filename) throws Exception {
/*  45 */     List<String> records = new ArrayList<>();
/*  46 */     BufferedReader reader = new BufferedReader(new FileReader(filename));
/*     */     String line;
/*  48 */     while ((line = reader.readLine()) != null) {
/*  49 */       records.add(line);
/*     */     }
/*  51 */     reader.close();
/*  52 */     return records;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> readFileAsListOfStrings(String filename, String replacement) throws Exception {
/*  58 */     List<String> records = new ArrayList<>();
/*  59 */     BufferedReader reader = new BufferedReader(new FileReader(filename));
/*  60 */     String line = null;
/*  61 */     while ((line = reader.readLine()) != null) {
/*  62 */       if (line.trim().startsWith("#"))
/*     */         continue; 
/*  64 */       line = line.replaceAll("@@", replacement);
/*  65 */       records.add(line.trim());
/*     */     } 
/*  67 */     reader.close();
/*  68 */     return records;
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
/*     */   public static Map<String, String> readPropertiesFileAsMap(String filename, String delimiter) throws Exception {
/*  81 */     Map<String, String> map = new HashMap<>();
/*  82 */     BufferedReader reader = new BufferedReader(new FileReader(filename));
/*     */     String line;
/*  84 */     while ((line = reader.readLine()) != null) {
/*  85 */       if (line.trim().length() == 0 || 
/*  86 */         line.charAt(0) == '#') {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  91 */       int delimPosition = line.indexOf(delimiter);
/*  92 */       String key = line.substring(0, delimPosition - 1).trim();
/*  93 */       String value = line.substring(delimPosition + 1).trim();
/*  94 */       map.put(key, value);
/*     */     } 
/*  96 */     reader.close();
/*  97 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Properties readPropertiesFile(String canonicalFilename) throws IOException {
/* 102 */     Properties properties = new Properties();
/* 103 */     properties.load(new FileInputStream(canonicalFilename));
/* 104 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeFile(String canonicalFilename, String text) throws IOException {
/* 115 */     writeFile(canonicalFilename, text, StandardCharsets.UTF_8);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeFile(String canonicalFilename, String text, Charset encoded) throws IOException {
/* 120 */     Path writeFile = Paths.get(canonicalFilename, new String[0]);
/*     */     
/* 122 */     try (BufferedWriter writer = Files.newBufferedWriter(writeFile, encoded, new java.nio.file.OpenOption[0])) {
/* 123 */       writer.write(text);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException {
/* 132 */     OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
/* 133 */     InputStream inputStream = new ByteArrayInputStream(bytes);
/* 134 */     int token = -1;
/*     */     
/* 136 */     while ((token = inputStream.read()) != -1) {
/* 137 */       bufferedOutputStream.write(token);
/*     */     }
/* 139 */     bufferedOutputStream.flush();
/* 140 */     bufferedOutputStream.close();
/* 141 */     inputStream.close();
/*     */   }
/*     */   
/*     */   public static void copyFile(String sourceFileName, String targetFileName) throws IOException {
/* 145 */     Path source = Paths.get(sourceFileName, new String[0]);
/* 146 */     Path target = Paths.get(targetFileName, new String[0]);
/*     */     try {
/* 148 */       Files.copy(source, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/* 149 */     } catch (IOException e) {
/*     */       
/* 151 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyFile(File source, File destination) throws IOException {
/* 158 */     byte[] buffer = new byte[100000];
/*     */     
/* 160 */     BufferedInputStream bufferedInputStream = null;
/* 161 */     BufferedOutputStream bufferedOutputStream = null;
/*     */     try {
/* 163 */       bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
/* 164 */       bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destination));
/*     */       int size;
/* 166 */       while ((size = bufferedInputStream.read(buffer)) > -1) {
/* 167 */         bufferedOutputStream.write(buffer, 0, size);
/*     */       }
/* 169 */     } catch (IOException e) {
/*     */       
/* 171 */       throw e;
/*     */     } finally {
/*     */       try {
/* 174 */         if (bufferedInputStream != null) {
/* 175 */           bufferedInputStream.close();
/*     */         }
/* 177 */         if (bufferedOutputStream != null) {
/* 178 */           bufferedOutputStream.flush();
/* 179 */           bufferedOutputStream.close();
/*     */         } 
/* 181 */       } catch (IOException ioe) {
/*     */         
/* 183 */         throw ioe;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\a\\util\FileUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */