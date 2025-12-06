package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface AlarmSubTypeConfigRepository
  extends JpaRepository<AlarmSubTypeConfig, Integer>, JpaSpecificationExecutor<AlarmSubTypeConfig>
{
  @Query("select max(a.id) from AlarmSubTypeConfig a")
  public abstract int getMaxId();
  
  @Query("select id from AlarmSubTypeConfig")
  public abstract Set<Integer> findAllKeys();
  
  public abstract List<AlarmSubTypeConfig> findByIdIn(List<Integer> paramList);
  
  @Query("select v from AlarmSubTypeConfig v where v.alarmType = :alarmType")
  public abstract List<AlarmSubTypeConfig> findByAlarmType(@Param("alarmType") String paramString);
  
  @Query("select v from AlarmSubTypeConfig v where v.id = :alarmSubTypeId")
  public abstract AlarmSubTypeConfig findByAlarmSubTypeId(@Param("alarmSubTypeId") Integer paramInteger);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\AlarmSubTypeConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */