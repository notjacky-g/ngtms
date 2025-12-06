package com.hwacom.ngtms.c.dis.fm.service;

import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
import java.util.List;

public interface DisQueueItemPredicate {
  List<DisQueueItem> getSortedQueue(List<DisQueueItem> paramList, String paramString1, String paramString2);
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisQueueItemPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */