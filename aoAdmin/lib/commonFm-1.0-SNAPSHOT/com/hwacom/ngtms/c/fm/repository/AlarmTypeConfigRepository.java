package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.AlarmTypeConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AlarmTypeConfigRepository extends JpaRepository<AlarmTypeConfig, String>, JpaSpecificationExecutor<AlarmTypeConfig> {
  @Query("select id from AlarmTypeConfig")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\AlarmTypeConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */