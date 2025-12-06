package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceGroupDeviceConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DeviceGroupDeviceConfigRepository
  extends JpaRepository<DeviceGroupDeviceConfig, String>
{
  @Query("select id from DeviceGroupDeviceConfig")
  public abstract Set<String> findAllKeys();
  
  public abstract Set<DeviceGroupDeviceConfig> findByGroupId(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceGroupDeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */