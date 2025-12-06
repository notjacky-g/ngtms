package com.hwacom.ngtms.hcce.core.coordinator;

import com.hazelcast.core.Member;
import com.hazelcast.core.MembershipEvent;

public abstract interface LeaderCallback
{
  public abstract void onElectedLeader(Member paramMember);
  
  public abstract void memberAdded(MembershipEvent paramMembershipEvent);
  
  public abstract void memberRemoved(MembershipEvent paramMembershipEvent);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\coordinator\LeaderCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */