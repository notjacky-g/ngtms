package com.hwacom.ngtms.room.fm.repository;

import com.hwacom.ngtms.room.fm.model.RoomCardReaderLog;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomCardReaderLogRepository extends JpaRepository<RoomCardReaderLog, String> {
  @Query("select id from #{#entityName} ")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\repository\RoomCardReaderLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */