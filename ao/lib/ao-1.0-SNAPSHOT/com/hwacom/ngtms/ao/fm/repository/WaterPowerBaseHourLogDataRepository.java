package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseHourLogData;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WaterPowerBaseHourLogDataRepository extends JpaRepository<WaterPowerBaseHourLogData, String> {
  @Query("select id from WaterPowerBaseHourLogData")
  Set<String> findAllKeys();
  
  WaterPowerBaseHourLogData findTopByRoomIdOrderByDataTimeDesc(String paramString);
  
  List<WaterPowerBaseHourLogData> findByRoomIdAndDataTimeBetween(String paramString, Date paramDate1, Date paramDate2);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\WaterPowerBaseHourLogDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */