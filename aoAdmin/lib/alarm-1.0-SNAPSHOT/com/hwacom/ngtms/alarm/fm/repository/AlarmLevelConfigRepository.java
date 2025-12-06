package com.hwacom.ngtms.alarm.fm.repository;

import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AlarmLevelConfigRepository extends JpaRepository<AlarmLevelConfig, String>, JpaSpecificationExecutor<AlarmLevelConfig> {
  @Query("select id from AlarmLevelConfig")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\repository\AlarmLevelConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */