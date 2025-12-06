package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.NcuDeviceStatusRecord;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NcuDeviceStatusRecordRespository extends JpaRepository<NcuDeviceStatusRecord, String> {
  @Query("select id from NcuDeviceStatusRecord")
  Set<String> findAllKeys();
  
  NcuDeviceStatusRecord findTopByDeviceNameOrderByDataTimeDesc(String paramString);
  
  List<NcuDeviceStatusRecord> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\NcuDeviceStatusRecordRespository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */