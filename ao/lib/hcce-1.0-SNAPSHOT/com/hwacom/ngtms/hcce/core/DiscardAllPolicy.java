/*    */ package com.hwacom.ngtms.hcce.core;
/*    */ 
/*    */ import com.hazelcast.core.EntryView;
/*    */ import com.hazelcast.map.merge.MapMergePolicy;
/*    */ import com.hazelcast.nio.ObjectDataInput;
/*    */ import com.hazelcast.nio.ObjectDataOutput;
/*    */ import com.hazelcast.nio.serialization.DataSerializable;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscardAllPolicy
/*    */   implements MapMergePolicy, DataSerializable
/*    */ {
/*    */   public Object merge(String mapName, EntryView mergingEntry, EntryView existingEntry)
/*    */   {
/* 28 */     if (existingEntry.getValue() != null) {
/* 29 */       return existingEntry.getValue();
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public void writeData(ObjectDataOutput out)
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public void readData(ObjectDataInput in)
/*    */     throws IOException
/*    */   {}
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\DiscardAllPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */