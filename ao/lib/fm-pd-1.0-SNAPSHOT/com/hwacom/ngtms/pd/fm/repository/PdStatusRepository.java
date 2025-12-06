package com.hwacom.ngtms.pd.fm.repository;

import com.hwacom.ngtms.pd.fm.model.PdStatus;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface PdStatusRepository
  extends JpaRepository<PdStatus, String>
{
  @Query("select deviceName from #{#entityName} ")
  public abstract Set<String> findAllKeys();
  
  @Query("from PdStatus v where v.deviceName = :deviceName")
  public abstract List<PdStatus> findByDeviceName(@Param("deviceName") String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\repository\PdStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */