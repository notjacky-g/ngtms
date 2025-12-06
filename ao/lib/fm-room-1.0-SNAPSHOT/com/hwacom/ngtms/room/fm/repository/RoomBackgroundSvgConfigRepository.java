package com.hwacom.ngtms.room.fm.repository;

import com.hwacom.ngtms.room.fm.model.RoomBackgroundSvgConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomBackgroundSvgConfigRepository extends JpaRepository<RoomBackgroundSvgConfig, String> {
  @Query("select id from RoomBackgroundSvgConfig")
  Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\repository\RoomBackgroundSvgConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */