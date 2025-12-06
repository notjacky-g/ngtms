package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
import com.hwacom.ngtms.c.shared.Direction;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface DeviceTcConfigRepository
  extends JpaRepository<DeviceTcConfig, String>
{
  @Query("from DeviceTcConfig d where d.deviceName in :deviceNames")
  public abstract List<DeviceTcConfig> findByDeviceNames(@Param("deviceNames") List<String> paramList);
  
  @Query("from DeviceTcConfig d where d.deviceName in :deviceNames")
  public abstract List<DeviceTcConfig> findByDeviceNames(@Param("deviceNames") List<String> paramList, Sort paramSort);
  
  @Query("select deviceName from DeviceTcConfig where deviceType = ?1 and enable = ?2 and location in (?3)")
  public abstract List<String> findByDeviceTypeAndEnableAndLocation(String paramString, boolean paramBoolean, List<String> paramList);
  
  public abstract long countByDeviceTypeAndEnableAndLocationIn(String paramString, Boolean paramBoolean, List<String> paramList);
  
  @Query("select deviceName from DeviceTcConfig")
  public abstract Set<String> findAllKeys();
  
  @Query("from DeviceTcConfig d where d.deviceType = :deviceType and d.deviceName in :deviceNames")
  public abstract List<DeviceTcConfig> findByDeviceTypeAndDeviceNamesWithSort(@Param("deviceType") String paramString, @Param("deviceNames") List<String> paramList, Sort paramSort);
  
  @Query("from DeviceTcConfig d where d.deviceType = 'CMS' and d.location = 'L' and d.lineId = :lineId and d.milepost >= :startMileage and d.milepost <= :endMileage and d.enable = :enable")
  public abstract List<DeviceTcConfig> findSurfaceCmsWithLineIdAndRangeAndEnable(@Param("lineId") String paramString, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2, @Param("enable") boolean paramBoolean);
  
  @Query("from DeviceTcConfig d where d.deviceType = 'CMS' and d.location <> 'L' and d.lineId = :lineId and d.direction = :direction and d.milepost >= :startMileage and d.milepost <= :endMileage and d.enable = :enable")
  public abstract List<DeviceTcConfig> findNonSurfaceCmsWithLineIdAndDirectionAndRangeAndEnable(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2, @Param("enable") boolean paramBoolean);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.direction = :direction and d.milepost < :milepost and d.enable = :enable and (d.deviceType = 'CMS' or d.deviceType = 'RGS') and d.location <> 'L' order by milepost desc")
  public abstract List<DeviceTcConfig> findPrevNonSurfaceCmsOrRgsWithLineIdAndDirectionAndMilepostAndEnable(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("milepost") int paramInt, @Param("enable") boolean paramBoolean, Pageable paramPageable);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.direction = :direction and d.milepost > :milepost and d.enable = :enable and (d.deviceType = 'CMS' or d.deviceType = 'RGS') and d.location <> 'L' order by milepost")
  public abstract List<DeviceTcConfig> findNextNonSurfaceCmsOrRgsWithLineIdAndDirectionAndMilepostAndEnable(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("milepost") int paramInt, @Param("enable") boolean paramBoolean, Pageable paramPageable);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.milepost >= :startMileage and d.milepost <= :endMileage and d.deviceType = :deviceType and d.enable = :enable")
  public abstract List<DeviceTcConfig> findWithLineIdAndRangeAndDeviceTypeAndEnable(@Param("lineId") String paramString1, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2, @Param("deviceType") String paramString2, @Param("enable") boolean paramBoolean);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.direction = :direction and d.milepost >= :startMileage and d.milepost <= :endMileage and d.deviceType = :deviceType and d.enable = :enable")
  public abstract List<DeviceTcConfig> findWithLineIdAndDirectionAndRangeAndDeviceTypeAndEnable(@Param("lineId") String paramString1, @Param("direction") Direction paramDirection, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2, @Param("deviceType") String paramString2, @Param("enable") boolean paramBoolean);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.direction = :direction and d.milepost < :milepost and d.deviceType in :deviceTypes and d.enable = :enable order by milepost desc")
  public abstract List<DeviceTcConfig> findPrevWithLineIdAndDirectionAndMilepostAndDeviceTypesAndEnable(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("milepost") int paramInt, @Param("deviceTypes") List<String> paramList, @Param("enable") boolean paramBoolean, Pageable paramPageable);
  
  @Query("from DeviceTcConfig d where d.lineId = :lineId and d.direction = :direction and d.milepost > :milepost and d.deviceType in :deviceTypes and d.enable = :enable order by milepost")
  public abstract List<DeviceTcConfig> findNextWithLineIdAndDirectionAndMilepostAndDeviceTypesAndEnable(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("milepost") int paramInt, @Param("deviceTypes") List<String> paramList, @Param("enable") boolean paramBoolean, Pageable paramPageable);
  
  public abstract List<DeviceTcConfig> findByDeviceTypeAndEnableAndSectionIdIn(String paramString, boolean paramBoolean, Set<String> paramSet);
  
  @Query("from DeviceTcConfig d where d.deviceType = 'CMS' and d.location = 'L' and d.lineId = :lineId and d.enable = :enable")
  public abstract List<DeviceTcConfig> findCmsByLineIdAndEnable(@Param("lineId") String paramString, @Param("enable") boolean paramBoolean);
  
  @Query("select d.deviceName, d.displayName, d.milepost from DeviceTcConfig d where d.deviceType = :deviceType and d.deviceName in :deviceNames")
  public abstract List<Object[]> findNameByDeviceTypeAndDeviceNames(@Param("deviceType") String paramString, @Param("deviceNames") List<String> paramList);
  
  public abstract List<DeviceTcConfig> findByDeviceTypeAndLineIdInAndLocationIn(@Param("deviceType") String paramString, @Param("lineIds") List<String> paramList1, @Param("locations") List<String> paramList2, Sort paramSort);
  
  public abstract DeviceTcConfig findFirstByLineIdAndDirectionAndDeviceTypeAndLocationInAndMilepostLessThanOrderByMilepostDesc(@Param("lineId") String paramString1, @Param("direction") Direction paramDirection, @Param("deviceType") String paramString2, @Param("locations") List<String> paramList, @Param("milepost") Integer paramInteger);
  
  public abstract DeviceTcConfig findFirstByLineIdAndDirectionAndDeviceTypeAndLocationInAndMilepostGreaterThanOrderByMilepostAsc(@Param("lineId") String paramString1, @Param("direction") Direction paramDirection, @Param("deviceType") String paramString2, @Param("locations") List<String> paramList, @Param("milepost") Integer paramInteger);
  
  @Query("select v from DeviceTcConfig v where v.lineId = :line")
  public abstract List<DeviceTcConfig> findByLine(@Param("line") String paramString, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.direction = :quiryDirection")
  public abstract List<DeviceTcConfig> findByDirection(@Param("quiryDirection") Direction paramDirection, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.milepost <= :endLocation and v.milepost >= :startLocation")
  public abstract List<DeviceTcConfig> findByMileageBetween(@Param("startLocation") Integer paramInteger1, @Param("endLocation") Integer paramInteger2, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.lineId = :line and v.direction = :quiryDirection")
  public abstract List<DeviceTcConfig> findByLineAndDirection(@Param("line") String paramString, @Param("quiryDirection") Direction paramDirection, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.lineId = :line and v.direction = :quiryDirection and v.milepost <= :endLocation and v.milepost >= :startLocation")
  public abstract List<DeviceTcConfig> findByLineAndDirectionAndMileageBetween(@Param("line") String paramString, @Param("quiryDirection") Direction paramDirection, @Param("startLocation") Integer paramInteger1, @Param("endLocation") Integer paramInteger2, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.lineId = :line and v.milepost <= :endLocation and v.milepost >= :startLocation")
  public abstract List<DeviceTcConfig> findByLineAndMileageBetween(@Param("line") String paramString, @Param("startLocation") Integer paramInteger1, @Param("endLocation") Integer paramInteger2, Sort paramSort);
  
  @Query("select v from DeviceTcConfig v where v.direction = :quiryDirection and v.milepost <= :endLocation and v.milepost >= :startLocation")
  public abstract List<DeviceTcConfig> findByDirectionAndMileageBetween(@Param("quiryDirection") Direction paramDirection, @Param("startLocation") Integer paramInteger1, @Param("endLocation") Integer paramInteger2, Sort paramSort);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceTcConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */