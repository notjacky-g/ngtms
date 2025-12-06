/*     */ package com.hwacom.ngtms.base.hazelcast.serializer;
/*     */ 
/*     */ import com.hazelcast.nio.serialization.Portable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class HzSerializerLoader
/*     */ {
/*  26 */   private static Logger logger = LoggerFactory.getLogger(HzSerializerLoader.class);
/*     */   
/*     */   private static final String RootElementName = "HzSerializerDeclare";
/*     */   
/*     */   private static final String PortableElementName = "HzPortableSerializerDeclare";
/*     */   
/*  32 */   private HzSerializerMeta[] hzSerializerMetas = new HzSerializerMeta[0];
/*     */   
/*  34 */   private HzPortableSerializerMeta[] hzPortableSerializerMetas = new HzPortableSerializerMeta[0];
/*     */   
/*     */   public void load() {
/*  37 */     loadFstSerializer();
/*     */   }
/*     */   
/*     */   public void loadPortableSerializer()
/*     */   {
/*  42 */     InputStream is = null;
/*     */     try {
/*  44 */       is = getClass().getResourceAsStream("/HzPortableSerializer.xml");
/*  45 */       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/*  46 */       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
/*  47 */       Document doc = dBuilder.parse(is);
/*  48 */       doc.getDocumentElement().normalize();
/*  49 */       if (!"HzPortableSerializerDeclare".equals(doc.getDocumentElement().getNodeName())) {
/*  50 */         throw new Exception("Unknown root element: " + doc.getDocumentElement().getNodeName());
/*     */       }
/*  52 */       TreeMap<Integer, HzPortableSerializerMeta> hzPortableMetaMap = new TreeMap();
/*     */       
/*  54 */       NodeList nodeList = doc.getElementsByTagName("HzSerializer");
/*  55 */       Node node; for (int i = 0; i < nodeList.getLength(); i++) {
/*  56 */         node = nodeList.item(i);
/*  57 */         if (node.getNodeType() == 1) {
/*  58 */           Element element = (Element)node;
/*  59 */           int id = Integer.parseInt(element.getAttribute("id"));
/*  60 */           String typeClassName = element.getElementsByTagName("TypeClass").item(0).getTextContent();
/*  61 */           Class<? extends Portable> typeClass = null;
/*     */           try {
/*  63 */             typeClass = Class.forName(typeClassName);
/*     */           } catch (Exception ex) {
/*  65 */             if (logger.isTraceEnabled()) logger.trace("Failed to load class ", ex);
/*  66 */             continue;
/*     */           }
/*  68 */           HzPortableSerializerMeta hzPortableMeta = new HzPortableSerializerMeta(id, typeClass);
/*  69 */           if (hzPortableMetaMap.put(Integer.valueOf(id), hzPortableMeta) != null) {
/*  70 */             throw new Exception("Duplicated Hz Portable Serializer id: " + id);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  75 */       this.hzPortableSerializerMetas = new HzPortableSerializerMeta[hzPortableMetaMap.size()];
/*  76 */       int i = 0;
/*  77 */       for (HzPortableSerializerMeta hzPortableMeta : hzPortableMetaMap.values()) {
/*  78 */         this.hzPortableSerializerMetas[i] = hzPortableMeta;
/*  79 */         logger.debug("[ID: {}] FacotryId Assign {} ", 
/*  80 */           Integer.valueOf(hzPortableMeta.getId()), hzPortableMeta.getTypeClass());
/*  81 */         i++;
/*     */       }
/*     */       return;
/*  84 */     } catch (Exception ex) { logger.error("Failed to load Hz Serializer declaration: HzPortableSerializer.xml", ex
/*  85 */         .getMessage());
/*     */     } finally {
/*     */       try {
/*  88 */         if (is != null) { is.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException2) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadFstSerializer()
/*     */   {
/*  97 */     InputStream is = null;
/*     */     try {
/*  99 */       is = getClass().getResourceAsStream("/HzSerializer.xml");
/* 100 */       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/* 101 */       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
/* 102 */       Document doc = dBuilder.parse(is);
/* 103 */       doc.getDocumentElement().normalize();
/* 104 */       if (!"HzSerializerDeclare".equals(doc.getDocumentElement().getNodeName())) {
/* 105 */         throw new Exception("Unknown root element: " + doc.getDocumentElement().getNodeName());
/*     */       }
/* 107 */       TreeMap<Integer, HzSerializerMeta> hzSerializerMetaMap = new TreeMap();
/*     */       
/* 109 */       NodeList nodeList = doc.getElementsByTagName("HzSerializer");
/* 110 */       Node node; for (int i = 0; i < nodeList.getLength(); i++) {
/* 111 */         node = nodeList.item(i);
/* 112 */         if (node.getNodeType() == 1) {
/* 113 */           Element element = (Element)node;
/* 114 */           int id = Integer.parseInt(element.getAttribute("id"));
/* 115 */           String typeClassName = element.getElementsByTagName("TypeClass").item(0).getTextContent();
/*     */           
/* 117 */           String hzSerializerClassName = element.getElementsByTagName("HzSerializerClass").item(0).getTextContent();
/* 118 */           Class<?> typeClass = null;
/* 119 */           Class<? extends HzSerializer> hzSerializerClass = null;
/*     */           try {
/* 121 */             typeClass = Class.forName(typeClassName);
/*     */             
/* 123 */             hzSerializerClass = Class.forName(hzSerializerClassName);
/*     */           } catch (Exception ex) {
/* 125 */             if (logger.isTraceEnabled()) logger.trace("Failed to load class ", ex);
/* 126 */             continue;
/*     */           }
/* 128 */           HzSerializerMeta hzSerializerMeta = new HzSerializerMeta(id, typeClass, hzSerializerClass);
/*     */           
/* 130 */           if (hzSerializerMetaMap.put(Integer.valueOf(id), hzSerializerMeta) != null) {
/* 131 */             throw new Exception("Duplicated Hz Serializer id: " + id);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 136 */       this.hzSerializerMetas = new HzSerializerMeta[hzSerializerMetaMap.size()];
/* 137 */       int i = 0;
/* 138 */       for (HzSerializerMeta hzSerializerMeta : hzSerializerMetaMap.values()) {
/* 139 */         this.hzSerializerMetas[i] = hzSerializerMeta;
/* 140 */         logger.debug("[ID: {}] Assign {} as the serializer of {}", new Object[] {
/*     */         
/* 142 */           Integer.valueOf(hzSerializerMeta.getId()), hzSerializerMeta
/* 143 */           .getHzSerializerClass(), hzSerializerMeta
/* 144 */           .getTypeClass() });
/* 145 */         i++;
/*     */       }
/*     */       return;
/* 148 */     } catch (Exception ex) { logger.error("Failed to load Hz Serializer declaration: HzSerializer.xml", ex.getMessage());
/*     */     } finally {
/*     */       try {
/* 151 */         if (is != null) is.close();
/*     */       }
/*     */       catch (IOException localIOException2) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public HzSerializerMeta[] getHzSerializerMetas()
/*     */   {
/* 159 */     return this.hzSerializerMetas;
/*     */   }
/*     */   
/*     */   public HzPortableSerializerMeta[] getHzPortableSerializerMetas() {
/* 163 */     return this.hzPortableSerializerMetas;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzSerializerLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */