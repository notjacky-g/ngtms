package com.hwacom.ngtms.pd.fm.repository;

import com.hwacom.ngtms.pd.fm.model.PdConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PdConfigRepository extends JpaRepository<PdConfig, String> {
  @Query("select deviceName from #{#entityName} ")
  Set<String> findAllKeys();
  
  @Query("from PdConfig v where v.deviceName = :deviceName")
  PdConfig findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\repository\PdConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */