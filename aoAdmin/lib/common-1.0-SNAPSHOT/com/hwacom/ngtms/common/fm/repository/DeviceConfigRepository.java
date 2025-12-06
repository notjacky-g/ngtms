package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.DeviceConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceConfigRepository extends JpaRepository<DeviceConfig, String> {
  @Query("select deviceName from DeviceConfig")
  Set<String> findAllKeys();
  
  @Query("from DeviceConfig d where d.deviceName in :deviceNames")
  List<DeviceConfig> findByDeviceNames(@Param("deviceNames") List<String> paramList);
  
  @Query("from DeviceConfig d where d.deviceName in :deviceNames")
  List<DeviceConfig> findByDeviceNames(@Param("deviceNames") List<String> paramList, Sort paramSort);
  
  @Query("from DeviceConfig d where d.deviceType = :deviceType and d.deviceName in :deviceNames")
  List<DeviceConfig> findByDeviceTypeAndDeviceNamesWithSort(@Param("deviceType") String paramString, @Param("deviceNames") List<String> paramList, Sort paramSort);
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\DeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */