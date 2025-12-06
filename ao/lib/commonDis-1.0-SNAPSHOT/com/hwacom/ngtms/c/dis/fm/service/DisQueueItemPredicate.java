package com.hwacom.ngtms.c.dis.fm.service;

import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
import java.util.List;

public abstract interface DisQueueItemPredicate
{
  public abstract List<DisQueueItem> getSortedQueue(List<DisQueueItem> paramList, String paramString1, String paramString2);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisQueueItemPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */