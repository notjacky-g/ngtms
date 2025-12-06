/*    */ package com.hwacom.ngtms.c.serializer;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.ByteArraySerializer;
/*    */ import com.hwacom.ngtms.c.ncc.TcReportWrapper;
/*    */ import com.hwacom.ngtms.common.serializer.HzObjectSerializer;
/*    */ import java.io.IOException;
/*    */ import org.nustaq.serialization.FSTConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcReportWrapperSerializer
/*    */   extends HzObjectSerializer
/*    */   implements ByteArraySerializer<TcReportWrapper>
/*    */ {
/*    */   public int getTypeId()
/*    */   {
/* 19 */     return this.typeId;
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */   
/*    */   public byte[] write(TcReportWrapper object)
/*    */     throws IOException
/*    */   {
/* 27 */     return ((FSTConfiguration)conf.get()).asByteArray(object);
/*    */   }
/*    */   
/*    */   public TcReportWrapper read(byte[] buffer) throws IOException
/*    */   {
/* 32 */     return (TcReportWrapper)((FSTConfiguration)conf.get()).asObject(buffer);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\serializer\TcReportWrapperSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */