package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceType;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, String> {
  @Query("select id from DeviceType")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceTypeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */