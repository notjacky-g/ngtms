package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmSubTypeConfigRepository extends JpaRepository<AlarmSubTypeConfig, Integer>, JpaSpecificationExecutor<AlarmSubTypeConfig> {
  @Query("select max(a.id) from AlarmSubTypeConfig a")
  int getMaxId();
  
  @Query("select id from AlarmSubTypeConfig")
  Set<Integer> findAllKeys();
  
  List<AlarmSubTypeConfig> findByIdIn(List<Integer> paramList);
  
  @Query("select v from AlarmSubTypeConfig v where v.alarmType = :alarmType")
  List<AlarmSubTypeConfig> findByAlarmType(@Param("alarmType") String paramString);
  
  @Query("select v from AlarmSubTypeConfig v where v.id = :alarmSubTypeId")
  AlarmSubTypeConfig findByAlarmSubTypeId(@Param("alarmSubTypeId") Integer paramInteger);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\AlarmSubTypeConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */