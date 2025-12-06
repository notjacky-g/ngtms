package com.hwacom.ngtms.pd.fm.repository;

import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PdLoopDeviceConfigRepository extends JpaRepository<PdLoopDeviceConfig, Long> {
  @Query("select id from #{#entityName} ")
  Set<Long> findAllKeys();
  
  @Query("from PdLoopDeviceConfig v where v.deviceName = :deviceName")
  PdLoopDeviceConfig findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\repository\PdLoopDeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */