package com.hwacom.ngtms.base.oplog.repository;

import com.hwacom.ngtms.base.oplog.model.OperationLog;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface OperationLogRepository
  extends JpaRepository<OperationLog, Long>, JpaSpecificationExecutor<OperationLog>
{
  public abstract List<OperationLog> findByOperationTimeBetween(Date paramDate1, Date paramDate2);
  
  public abstract List<OperationLog> findByUserIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  public abstract List<OperationLog> findBySchIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  public abstract List<OperationLog> findByOperationItemAndSubSysNameAndOperationTimeBetween(String paramString1, String paramString2, Date paramDate1, Date paramDate2, Sort paramSort);
  
  public abstract List<OperationLog> findByOperationItemAndSubSysNameAndDeviceNameAndOperationTimeBetween(String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.deviceName in :deviceNames and v.userId in :userIds and (v.operationItem <> :operationItem or v.operationItem is null) and v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findByDeviceNamesAndUserIdAndOpTime(@Param("deviceNames") List<String> paramList1, @Param("userIds") List<String> paramList2, @Param("operationItem") String paramString, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.userId in :userIds and v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findByUserIdsAndOpTime(@Param("userIds") List<String> paramList, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findByOpTime(@Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.deviceName in :deviceNames and (v.operationItem <> :operationItem or v.operationItem is null) and v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findByDeviceNamesAndOpTime(@Param("deviceNames") List<String> paramList, @Param("operationItem") String paramString, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.subSysName in :subSysNames and v.userId in :userIds and (v.operationItem <> :operationItem or v.operationItem is null) and v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findBySubSysNamesAndUserIdAndOpTime(@Param("subSysNames") List<String> paramList1, @Param("userIds") List<String> paramList2, @Param("operationItem") String paramString, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from OperationLog v where v.subSysName in :subSysNames and (v.operationItem <> :operationItem or v.operationItem is null) and v.operationTime between :startDateTime and :endDateTime")
  public abstract List<OperationLog> findBySubSysNamesAndOpTime(@Param("subSysNames") List<String> paramList, @Param("operationItem") String paramString, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\repository\OperationLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */