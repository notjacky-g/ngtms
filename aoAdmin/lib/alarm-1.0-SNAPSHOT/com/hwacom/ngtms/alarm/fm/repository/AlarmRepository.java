package com.hwacom.ngtms.alarm.fm.repository;

import com.hwacom.ngtms.alarm.fm.model.Alarm;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlarmRepository extends JpaRepository<Alarm, String> {
  @Query("select id from Alarm")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\repository\AlarmRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */