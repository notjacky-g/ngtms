package com.hwacom.ngtms.alarm.fm.repository;

import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public abstract interface AlarmLevelConfigRepository
  extends JpaRepository<AlarmLevelConfig, String>, JpaSpecificationExecutor<AlarmLevelConfig>
{
  @Query("select id from AlarmLevelConfig")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\repository\AlarmLevelConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */