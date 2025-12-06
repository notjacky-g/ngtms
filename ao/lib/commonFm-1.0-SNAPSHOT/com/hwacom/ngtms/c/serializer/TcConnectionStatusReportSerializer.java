/*    */ package com.hwacom.ngtms.c.serializer;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.ByteArraySerializer;
/*    */ import com.hwacom.ngtms.c.ncc.TcConnectionStatusReport;
/*    */ import com.hwacom.ngtms.common.serializer.HzObjectSerializer;
/*    */ import java.io.IOException;
/*    */ import org.nustaq.serialization.FSTConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcConnectionStatusReportSerializer
/*    */   extends HzObjectSerializer
/*    */   implements ByteArraySerializer<TcConnectionStatusReport>
/*    */ {
/*    */   public int getTypeId()
/*    */   {
/* 19 */     return this.typeId;
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */   
/*    */   public byte[] write(TcConnectionStatusReport object)
/*    */     throws IOException
/*    */   {
/* 27 */     return ((FSTConfiguration)conf.get()).asByteArray(object);
/*    */   }
/*    */   
/*    */   public TcConnectionStatusReport read(byte[] buffer) throws IOException
/*    */   {
/* 32 */     return (TcConnectionStatusReport)((FSTConfiguration)conf.get()).asObject(buffer);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\serializer\TcConnectionStatusReportSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */