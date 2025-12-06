package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.PdDeviceStatusRecord;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PdDeviceStatusRecordRepository extends JpaRepository<PdDeviceStatusRecord, String> {
  @Query("select id from PdDeviceStatusRecord")
  Set<PdDeviceStatusRecord> findAllKeys();
  
  List<PdDeviceStatusRecord> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2);
  
  List<PdDeviceStatusRecord> findTop1ByDeviceNameAndDataTimeLessThanEqualOrderByDataTimeDesc(String paramString, Date paramDate);
  
  PdDeviceStatusRecord findTopByDeviceNameOrderByDataTimeDesc(String paramString);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\PdDeviceStatusRecordRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */