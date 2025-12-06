package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceGroup;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DeviceGroupRepository
  extends JpaRepository<DeviceGroup, String>
{
  @Query("select c from DeviceGroup c , DeviceGroupDeviceConfig d where c.groupId = d.groupId and d.deviceName=?1")
  public abstract List<DeviceGroup> findByDeviceName(String paramString);
  
  @Query("select groupId from DeviceGroup")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceGroupRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */