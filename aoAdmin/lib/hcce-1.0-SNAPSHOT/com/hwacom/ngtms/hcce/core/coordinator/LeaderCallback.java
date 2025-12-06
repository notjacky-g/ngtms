package com.hwacom.ngtms.hcce.core.coordinator;

import com.hazelcast.core.Member;
import com.hazelcast.core.MembershipEvent;

public interface LeaderCallback {
  void onElectedLeader(Member paramMember);
  
  void memberAdded(MembershipEvent paramMembershipEvent);
  
  void memberRemoved(MembershipEvent paramMembershipEvent);
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\coordinator\LeaderCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */