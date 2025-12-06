package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface DeviceTcStatusRepository
  extends JpaRepository<DeviceTcStatus, String>
{
  @Query("from DeviceTcStatus d where d.id in :deviceNames")
  public abstract List<DeviceTcStatus> findByIds(@Param("deviceNames") List<String> paramList);
  
  @Query("select id from DeviceTcStatus")
  public abstract Set<String> findAllKeys();
  
  public abstract DeviceTcStatus findTopByIdOrderByDataTimeDesc(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceTcStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */