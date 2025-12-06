package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface DeviceLocationMappingConfigRepository
  extends JpaRepository<DeviceLocationMappingConfig, String>
{
  public abstract List<DeviceLocationMappingConfig> findByLocationName(String paramString);
  
  @Query("select id from #{#entityName} ")
  public abstract Set<String> findAllKeys();
  
  @Query("from DeviceLocationMappingConfig d where d.locationName = :location and d.deviceName in :deviceNames")
  public abstract List<DeviceLocationMappingConfig> findByLocationNameAndDeviceNames(@Param("location") String paramString, @Param("deviceNames") List<String> paramList);
  
  @Query("from DeviceLocationMappingConfig d where d.deviceName = :deviceName")
  public abstract DeviceLocationMappingConfig findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceLocationMappingConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */