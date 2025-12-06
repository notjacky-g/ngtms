package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceLocationMappingConfigRepository extends JpaRepository<DeviceLocationMappingConfig, String> {
  @Query("select id from #{#entityName} ")
  Set<String> findAllKeys();
  
  @Query("from DeviceLocationMappingConfig d where d.locationName = :location and d.deviceName in :deviceNames")
  List<DeviceLocationMappingConfig> findByLocationNameAndDeviceNames(@Param("location") String paramString, @Param("deviceNames") List<String> paramList);
  
  @Query("from DeviceLocationMappingConfig d where d.deviceName = :deviceName")
  DeviceLocationMappingConfig findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceLocationMappingConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */