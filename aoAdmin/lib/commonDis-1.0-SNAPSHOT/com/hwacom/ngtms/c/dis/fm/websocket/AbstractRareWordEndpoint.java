/*    */ package com.hwacom.ngtms.c.dis.fm.websocket;
/*    */ 
/*    */ import com.hwacom.ngtms.c.dis.shared.RareWordMessage;
/*    */ import com.hwacom.ngtms.c.dis.util.DisJsonUtil;
/*    */ import com.hwacom.ngtms.c.dis.util.TransferHelper;
/*    */ import com.hwacom.ngtms.c.fm.websocket.AbstractServerEndpoint;
/*    */ import com.hwacom.ngtms.c.shared.WebSocketCloseReason;
/*    */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*    */ import java.io.IOException;
/*    */ import java.io.StringReader;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.json.Json;
/*    */ import javax.json.JsonObject;
/*    */ import javax.websocket.CloseReason;
/*    */ import javax.websocket.DecodeException;
/*    */ import javax.websocket.Decoder;
/*    */ import javax.websocket.EndpointConfig;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractRareWordEndpoint
/*    */   extends AbstractServerEndpoint
/*    */ {
/* 31 */   private static final Logger logger = LoggerFactory.getLogger(AbstractRareWordEndpoint.class);
/*    */   
/*    */   public abstract class Callback
/*    */     implements BiConsumer<String, TcResponse>, AmCallback {
/*    */     private String code;
/*    */     
/*    */     public Callback(String code) {
/* 38 */       this.code = code;
/*    */     }
/*    */ 
/*    */     
/*    */     public void accept(String deviceName, TcResponse tcResponse) {
/* 43 */       byte[] code = TransferHelper.transferHexToByteArray(this.code, ",");
/* 44 */       String args = Integer.toHexString(TransferHelper.transferToInt(Byte.valueOf(code[0]), Byte.valueOf(code[1])));
/* 45 */       if (tcResponse.getResult() == TcResponse.Result.SUCCESS) {
/* 46 */         AbstractRareWordEndpoint.this.close(WebSocketCloseReason.FINISHED.getCode());
/*    */       } else {
/* 48 */         AbstractRareWordEndpoint.this.close(WebSocketCloseReason.FAILURE.getCode());
/*    */       } 
/* 50 */       logResult(deviceName, tcResponse, new Object[] { args });
/*    */     }
/*    */ 
/*    */     
/*    */     public String getMessageId() {
/* 55 */       return "rareWord";
/*    */     }
/*    */   }
/*    */   
/*    */   private void close(int closeCode) {
/*    */     try {
/* 61 */       this.session.close(new CloseReason(() -> paramInt, ""));
/* 62 */     } catch (IOException e) {
/* 63 */       logger.warn("Close websocket Session:'{}' failed!", this.session.getId(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static class MessageDecoder
/*    */     implements Decoder.Text<RareWordMessage>
/*    */   {
/*    */     public void init(EndpointConfig config) {}
/*    */ 
/*    */     
/*    */     public void destroy() {}
/*    */     
/*    */     public RareWordMessage decode(String s) throws DecodeException {
/* 77 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 78 */       RareWordMessage.RareWordMessageImpl message = new RareWordMessage.RareWordMessageImpl();
/* 79 */       message.setDeviceNames(DisJsonUtil.extractDevices(jsonObject));
/* 80 */       message.setCode(jsonObject.getString("code"));
/* 81 */       message.setWord32(jsonObject.getString("word32"));
/* 82 */       message.setWord48(jsonObject.getString("word48"));
/* 83 */       message.setWord64(jsonObject.getString("word64"));
/* 84 */       return (RareWordMessage)message;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean willDecode(String s) {
/*    */       try {
/* 90 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 91 */         return RareWordMessage.TYPE.equals(jsonObject.getString("type"));
/* 92 */       } catch (RuntimeException ex) {
/* 93 */         AbstractRareWordEndpoint.logger.debug("RareWordMessage format Error, '{}' will not be decoded!", s);
/* 94 */         return false;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\websocket\AbstractRareWordEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */