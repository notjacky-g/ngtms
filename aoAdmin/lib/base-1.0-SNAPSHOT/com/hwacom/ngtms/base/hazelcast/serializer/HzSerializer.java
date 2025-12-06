package com.hwacom.ngtms.base.hazelcast.serializer;

import com.hazelcast.nio.serialization.Serializer;

public interface HzSerializer extends Serializer {
  void setTypeId(int paramInt);
}


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */