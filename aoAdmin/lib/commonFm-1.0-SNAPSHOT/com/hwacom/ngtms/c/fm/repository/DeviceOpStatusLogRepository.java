package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceOpStatusLog;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceOpStatusLogRepository extends JpaRepository<DeviceOpStatusLog, Long> {
  @Query("select id from DeviceOpStatusLog ")
  Set<Long> findAllKeys();
  
  List<DeviceOpStatusLog> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2);
  
  List<DeviceOpStatusLog> findByDeviceNameInAndUserIdInAndDataTimeBetween(List<String> paramList1, List<String> paramList2, Date paramDate1, Date paramDate2);
  
  DeviceOpStatusLog findFirstByDeviceNameAndDataTimeBeforeOrderByDataTimeDesc(String paramString, Date paramDate);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceOpStatusLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */