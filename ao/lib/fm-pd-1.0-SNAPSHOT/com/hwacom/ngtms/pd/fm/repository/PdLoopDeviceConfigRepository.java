package com.hwacom.ngtms.pd.fm.repository;

import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface PdLoopDeviceConfigRepository
  extends JpaRepository<PdLoopDeviceConfig, Long>
{
  @Query("select id from #{#entityName} ")
  public abstract Set<Long> findAllKeys();
  
  @Query("from PdLoopDeviceConfig v where v.deviceName = :deviceName")
  public abstract PdLoopDeviceConfig findByDeviceName(@Param("deviceName") String paramString);
  
  public abstract List<PdLoopDeviceConfig> findByPdDeviceNameIn(List<String> paramList);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\repository\PdLoopDeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */