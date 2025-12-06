package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceHostLocationRepository extends JpaRepository<DeviceHostLocation, Integer> {
  @Query("select id from DeviceHostLocation")
  Set<Integer> findAllKeys();
  
  @Query("select max(h.id) from DeviceHostLocation h")
  Integer findMaxId();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceHostLocationRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */