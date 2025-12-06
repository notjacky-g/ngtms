package com.hwacom.ngtms.room.fm.repository;

import com.hwacom.ngtms.room.fm.model.RoomDeviceStatus;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomDeviceStatusRepository extends JpaRepository<RoomDeviceStatus, String> {
  @Query("select id from RoomDeviceStatus")
  Set<String> findAllKeys();
  
  @Query("from RoomDeviceStatus d where d.deviceName = :deviceName ")
  RoomDeviceStatus findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\repository\RoomDeviceStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */