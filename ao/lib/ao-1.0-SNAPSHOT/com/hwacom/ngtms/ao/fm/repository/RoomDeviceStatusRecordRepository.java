package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.RoomDeviceStatusRecord;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomDeviceStatusRecordRepository extends JpaRepository<RoomDeviceStatusRecord, String> {
  @Query("select id from RoomDeviceStatusRecord")
  Set<RoomDeviceStatusRecord> findAllKeys();
  
  List<RoomDeviceStatusRecord> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2);
  
  List<RoomDeviceStatusRecord> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2, Sort paramSort);
  
  List<RoomDeviceStatusRecord> findTop1ByDeviceNameAndDataTimeLessThanEqualOrderByDataTimeDesc(String paramString, Date paramDate);
  
  RoomDeviceStatusRecord findTopByDeviceNameOrderByDataTimeDesc(String paramString);
  
  List<RoomDeviceStatusRecord> findByDataTimeBetween(Date paramDate1, Date paramDate2, Sort paramSort);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\RoomDeviceStatusRecordRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */