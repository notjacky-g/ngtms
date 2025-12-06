package com.hwacom.ngtms.base.hazelcast.serializer;

import com.hazelcast.nio.serialization.Serializer;

public abstract interface HzSerializer
  extends Serializer
{
  public abstract void setTypeId(int paramInt);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */