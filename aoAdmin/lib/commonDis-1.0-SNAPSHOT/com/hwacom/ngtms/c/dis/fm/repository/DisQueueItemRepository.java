package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisQueueItemRepository extends JpaRepository<DisQueueItem, String> {
  @Query("select id from DisQueueItem ")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisQueueItemRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */