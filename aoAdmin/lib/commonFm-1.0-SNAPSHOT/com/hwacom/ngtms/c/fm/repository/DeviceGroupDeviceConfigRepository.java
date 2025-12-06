package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceGroupDeviceConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceGroupDeviceConfigRepository extends JpaRepository<DeviceGroupDeviceConfig, String> {
  @Query("select id from DeviceGroupDeviceConfig")
  Set<String> findAllKeys();
  
  Set<DeviceGroupDeviceConfig> findByGroupId(String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceGroupDeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */