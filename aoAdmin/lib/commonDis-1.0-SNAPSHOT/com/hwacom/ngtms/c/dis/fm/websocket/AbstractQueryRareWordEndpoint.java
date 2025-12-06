/*     */ package com.hwacom.ngtms.c.dis.fm.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.c.dis.shared.QueryRareWordListMessage;
/*     */ import com.hwacom.ngtms.c.dis.shared.QueryRareWordMessage;
/*     */ import com.hwacom.ngtms.c.dis.shared.QueryRareWordResultMessage;
/*     */ import com.hwacom.ngtms.c.dis.util.DisJsonUtil;
/*     */ import com.hwacom.ngtms.c.dis.util.TransferHelper;
/*     */ import com.hwacom.ngtms.c.fm.websocket.AbstractServerEndpoint;
/*     */ import com.hwacom.ngtms.c.shared.DeviceListMessage;
/*     */ import com.hwacom.ngtms.c.shared.WebSocketCloseReason;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.json.Json;
/*     */ import javax.json.JsonArrayBuilder;
/*     */ import javax.json.JsonObject;
/*     */ import javax.json.JsonObjectBuilder;
/*     */ import javax.json.JsonWriter;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.DecodeException;
/*     */ import javax.websocket.Decoder;
/*     */ import javax.websocket.EncodeException;
/*     */ import javax.websocket.Encoder;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractQueryRareWordEndpoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*  42 */   private static final Logger logger = LoggerFactory.getLogger(AbstractQueryRareWordEndpoint.class);
/*     */   
/*     */   public abstract class QueryListCallback
/*     */     implements AmCallback, BiConsumer<AbstractMap.SimpleEntry<String, TcResponse>, List<Integer>>
/*     */   {
/*     */     private int count;
/*     */     
/*     */     public QueryListCallback(List<String> deviceNames) {
/*  50 */       this.count = deviceNames.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(AbstractMap.SimpleEntry<String, TcResponse> simpleEntry, List<Integer> codeIds) {
/*  55 */       String deviceName = simpleEntry.getKey();
/*  56 */       TcResponse response = simpleEntry.getValue();
/*  57 */       if (response.getResult() == TcResponse.Result.SUCCESS) {
/*  58 */         AbstractQueryRareWordEndpoint.this.send(new AbstractMap.SimpleEntry<>(deviceName, codeIds));
/*     */       }
/*  60 */       logResult(deviceName, response, new Object[0]);
/*  61 */       this.count--;
/*  62 */       if (this.count == 0) {
/*  63 */         AbstractQueryRareWordEndpoint.this.close(WebSocketCloseReason.FINISHED.getCode());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMessageId() {
/*  69 */       return "query.rareWord.list";
/*     */     }
/*     */   }
/*     */   
/*     */   protected void close(int closeCode) {
/*     */     try {
/*  75 */       this.session.close(new CloseReason(() -> paramInt, ""));
/*  76 */     } catch (IOException e) {
/*  77 */       logger.warn("Close websocket Session:'{}' failed!", this.session.getId(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract class QueryCallback
/*     */     implements AmCallback, BiConsumer<AbstractMap.SimpleEntry<String, TcResponse>, QueryRareWordResultMessage.QueryRareWordResult>
/*     */   {
/*     */     public void accept(AbstractMap.SimpleEntry<String, TcResponse> simpleEntry, QueryRareWordResultMessage.QueryRareWordResult queryRareWordResult) {
/*  87 */       String deviceName = simpleEntry.getKey();
/*  88 */       TcResponse response = simpleEntry.getValue();
/*  89 */       if (response.getResult() == TcResponse.Result.SUCCESS) {
/*  90 */         AbstractQueryRareWordEndpoint.this.send(queryRareWordResult);
/*  91 */       } else if (response.getResult() == TcResponse.Result.TIMEOUT) {
/*  92 */         AbstractQueryRareWordEndpoint.this.close(WebSocketCloseReason.FAILURE.getCode());
/*     */       } else {
/*  94 */         AbstractQueryRareWordEndpoint.this.close(WebSocketCloseReason.FAILURE.getCode());
/*     */       } 
/*  96 */       logResult(deviceName, response, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMessageId() {
/* 101 */       return "query.rareWord";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface QueryRareWordMarkerInterface {}
/*     */ 
/*     */   
/*     */   private static class QueryRareWordListMessageImpl
/*     */     extends DeviceListMessage.DeviceListMessageImpl
/*     */     implements QueryRareWordListMessage, QueryRareWordMarkerInterface
/*     */   {
/*     */     private QueryRareWordListMessageImpl() {}
/*     */     
/*     */     public String toString() {
/* 116 */       return "QueryRareWordListMessageImpl [getDeviceNames()=" + getDeviceNames() + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class QueryRareWordMessageImpl
/*     */     implements QueryRareWordMessage, QueryRareWordMarkerInterface
/*     */   {
/*     */     private String codeId;
/*     */     private String deviceName;
/*     */     
/*     */     private QueryRareWordMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 129 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {}
/*     */ 
/*     */     
/*     */     public String getDeviceName() {
/* 137 */       return this.deviceName;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceName(String deviceName) {
/* 142 */       this.deviceName = deviceName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCodeId() {
/* 147 */       return this.codeId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCodeId(String codeId) {
/* 152 */       this.codeId = codeId;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ListMessageDecoder
/*     */     implements Decoder.Text<QueryRareWordMarkerInterface>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public AbstractQueryRareWordEndpoint.QueryRareWordMarkerInterface decode(String s) throws DecodeException {
/* 166 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 167 */       AbstractQueryRareWordEndpoint.QueryRareWordListMessageImpl message = new AbstractQueryRareWordEndpoint.QueryRareWordListMessageImpl();
/* 168 */       message.setDeviceNames(DisJsonUtil.extractDevices(jsonObject));
/* 169 */       return message;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 175 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 176 */         return QueryRareWordListMessage.TYPE.equals(jsonObject.getString("type"));
/* 177 */       } catch (RuntimeException ex) {
/* 178 */         AbstractQueryRareWordEndpoint.logger.debug("QueryRareWordListMessage format Error, '{}' will not be decoded!", s);
/* 179 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MessageDecoder
/*     */     implements Decoder.Text<QueryRareWordMarkerInterface>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public AbstractQueryRareWordEndpoint.QueryRareWordMarkerInterface decode(String s) throws DecodeException {
/* 194 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 195 */       AbstractQueryRareWordEndpoint.QueryRareWordMessageImpl message = new AbstractQueryRareWordEndpoint.QueryRareWordMessageImpl();
/* 196 */       message.setDeviceName(jsonObject.getString("deviceName"));
/* 197 */       message.setCodeId(jsonObject.getString("codeId"));
/* 198 */       return message;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 204 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 205 */         return QueryRareWordMessage.TYPE.equals(jsonObject.getString("type"));
/* 206 */       } catch (RuntimeException ex) {
/* 207 */         AbstractQueryRareWordEndpoint.logger.debug("QueryRareWordMessage format Error, '{}' will not be decoded!", s);
/* 208 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ListMessageEncoder
/*     */     implements Encoder.Text<AbstractMap.SimpleEntry<String, List<Integer>>>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public String encode(AbstractMap.SimpleEntry<String, List<Integer>> simpleEntry) throws EncodeException {
/* 224 */       String deviceName = simpleEntry.getKey();
/* 225 */       List<Integer> charList = simpleEntry.getValue();
/* 226 */       JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
/* 227 */       AbstractQueryRareWordEndpoint.logger.debug("Encode GetCmsCharListRspPm deviceName:'{}', size:'{}'", deviceName, 
/* 228 */           Integer.valueOf(charList.size()));
/* 229 */       for (Integer each : charList) {
/* 230 */         JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
/* 231 */         jsonObjectBuilder.add("deviceName", deviceName);
/* 232 */         jsonObjectBuilder.add("codeId", Integer.toHexString(each.intValue()).toUpperCase());
/* 233 */         jsonArrayBuilder.add(jsonObjectBuilder);
/*     */       } 
/* 235 */       StringWriter stringWriter = new StringWriter();
/* 236 */       JsonWriter jsonWriter = Json.createWriter(stringWriter);
/* 237 */       jsonWriter.writeArray(jsonArrayBuilder.build());
/* 238 */       return stringWriter.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MessageEncoder
/*     */     implements Encoder.Text<QueryRareWordResultMessage.QueryRareWordResult>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public String encode(QueryRareWordResultMessage.QueryRareWordResult queryRareWordResult) throws EncodeException {
/* 252 */       JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
/* 253 */       objectBuilder.add("width", queryRareWordResult.getWidth());
/* 254 */       objectBuilder.add("height", queryRareWordResult.getHeight());
/* 255 */       JsonArrayBuilder trueIndices = Json.createArrayBuilder();
/* 256 */       BitSet bitSet = BitSet.valueOf(reverse(queryRareWordResult.getPattern())); int i;
/* 257 */       for (i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
/* 258 */         trueIndices.add(i);
/*     */       }
/* 260 */       objectBuilder.add("trueIndices", trueIndices);
/* 261 */       JsonObject model = objectBuilder.build();
/* 262 */       StringWriter stringWriter = new StringWriter();
/* 263 */       JsonWriter jsonWrtier = Json.createWriter(stringWriter);
/* 264 */       jsonWrtier.writeObject(model);
/* 265 */       AbstractQueryRareWordEndpoint.logger.debug("Encode SetCmsCharPatternReqPm. result:'{}'", stringWriter.toString());
/* 266 */       return stringWriter.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private byte[] reverse(byte[] source) {
/* 276 */       byte[] result = new byte[source.length];
/* 277 */       for (int i = 0; i < source.length; i++) {
/* 278 */         byte each = source[i];
/* 279 */         result[i] = TransferHelper.reverse(each);
/*     */       } 
/* 281 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\websocket\AbstractQueryRareWordEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */