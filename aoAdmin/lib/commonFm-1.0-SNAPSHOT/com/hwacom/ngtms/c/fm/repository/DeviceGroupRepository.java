package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceGroup;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, String> {
  @Query("select c from DeviceGroup c , DeviceGroupDeviceConfig d where c.groupId = d.groupId and d.deviceName=?1")
  List<DeviceGroup> findByDeviceName(String paramString);
  
  @Query("select groupId from DeviceGroup")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceGroupRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */