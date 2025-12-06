package com.hwacom.ngtms.alarm.fm.repository;

import com.hwacom.ngtms.alarm.fm.model.Alarm;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface AlarmRepository
  extends JpaRepository<Alarm, String>
{
  @Query("select id from Alarm")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\repository\AlarmRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */