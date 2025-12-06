package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisDisplayChangeLog;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DisDisplayChangeLogRepository extends JpaRepository<DisDisplayChangeLog, Long> {
  @Query("from DisDisplayChangeLog d where d.deviceType = :deviceType  and ( d.dataTime >= :startTime  and d.dataTime <= :endTime)  and d.deviceName in :deviceNames")
  List<DisDisplayChangeLog> findByDeviceTypeAndDataTimeBetweenAndDeviceNameIn(@Param("deviceType") String paramString, @Param("startTime") Date paramDate1, @Param("endTime") Date paramDate2, @Param("deviceNames") Set<String> paramSet);
  
  @Query("from DisDisplayChangeLog d where d.deviceType = :deviceType  and ( d.dataTime >= :startTime  and d.dataTime <= :endTime) ")
  List<DisDisplayChangeLog> findByDeviceTypeAndDataTimeBetween(@Param("deviceType") String paramString, @Param("startTime") Date paramDate1, @Param("endTime") Date paramDate2);
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisDisplayChangeLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */