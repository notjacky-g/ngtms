package com.hwacom.ngtms.alarm.fm.repository;

import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
import com.hwacom.ngtms.alarm.shared.AlarmState;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Long>, JpaSpecificationExecutor<AlarmLog> {
  @Query("select v from AlarmLog v where v.timestamp between :startDateTime and :endDateTime and v.alarmSubType in :alarmSubTypeIds")
  List<AlarmLog> findByTimestampBetweenAndAlarmSubTypeIds(@Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, @Param("alarmSubTypeIds") List<String> paramList, Sort paramSort);
  
  @Query("select v from AlarmLog v where v.timestamp between :startDateTime and :endDateTime")
  List<AlarmLog> findByTimestampBetween(@Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from AlarmLog a where a.timestamp between :startDateTime and :endDateTime")
  List<AlarmLog> findByTimestamp(@Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from AlarmLog a where a.deviceName in :deviceNames and a.timestamp between :startDateTime and :endDateTime")
  List<AlarmLog> findByDeviceNamesAndTimestamp(@Param("deviceNames") List<String> paramList, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from AlarmLog a where a.deviceName in :deviceNames and a.alarmSubType in :alarmSubTypeIds and a.timestamp between :startDateTime and :endDateTime")
  List<AlarmLog> findByDeviceNamesAndTimestampAndAlarmSubTypeIds(@Param("deviceNames") List<String> paramList1, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, @Param("alarmSubTypeIds") List<String> paramList2, Sort paramSort);
  
  Optional<AlarmLog> findFirstByDeviceNameAndAlarmSubTypeAndDegreeAndAlarmStateNotOrderByTimestampDesc(String paramString1, String paramString2, Integer paramInteger, AlarmState paramAlarmState);
  
  Optional<AlarmLog> findFirstByDeviceNameAndAlarmSubTypeAndDegreeOrderByTimestampDesc(String paramString1, String paramString2, Integer paramInteger);
  
  List<AlarmLog> findTop250ByTimestampBetweenOrderByTimestampDesc(Date paramDate1, Date paramDate2);
  
  List<AlarmLog> findTop250ByAlarmSubTypeInAndTimestampBetweenOrderByTimestampDesc(List<String> paramList, Date paramDate1, Date paramDate2);
}


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\repository\AlarmLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */