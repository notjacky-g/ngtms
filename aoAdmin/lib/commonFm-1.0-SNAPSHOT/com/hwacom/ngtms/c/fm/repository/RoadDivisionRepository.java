package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadDivision;
import com.hwacom.ngtms.c.shared.AreaType;
import com.hwacom.ngtms.c.shared.DivisionType;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadDivisionRepository extends JpaRepository<RoadDivision, String> {
  @Query("select divisionId from RoadDivision")
  Set<String> findAllKeys();
  
  @Query("from RoadDivision d where d.lineId = :lineId and d.mileage < :mileage and d.divisionType in :divisionTypes order by d.mileage desc")
  List<RoadDivision> findPreviousWithLineIdAndMileage(@Param("lineId") String paramString, @Param("mileage") int paramInt, @Param("divisionTypes") List<DivisionType> paramList, Pageable paramPageable);
  
  @Query("from RoadDivision d where d.lineId = :lineId and d.mileage > :mileage and d.divisionType in :divisionTypes order by d.mileage")
  List<RoadDivision> findNextWithLineIdAndMileage(@Param("lineId") String paramString, @Param("mileage") int paramInt, @Param("divisionTypes") List<DivisionType> paramList, Pageable paramPageable);
  
  @Query("from RoadDivision d where d.divisionId in :divisionIds")
  List<RoadDivision> findWithDivisionIds(@Param("divisionIds") List<String> paramList, Pageable paramPageable);
  
  @Query("from RoadDivision d where d.lineId = :lineId and d.divisionType in :divisionTypes")
  List<RoadDivision> findWithLineIdAndDivisionTypes(@Param("lineId") String paramString, @Param("divisionTypes") List<DivisionType> paramList);
  
  @Query("from RoadDivision d where d.lineId = :lineId and d.mileage >= :startMileage and d.mileage <= :endMileage and (d.divisionType = 'I' or d.divisionType = 'C')")
  List<RoadDivision> findWithLineIdAndRnage(@Param("lineId") String paramString, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2, Pageable paramPageable);
  
  @Query(" from RoadDivision d where d.lineId = :lineId  and d.mileage <= :mileage  and (d.divisionType = 'I' or d.divisionType = 'C') ")
  List<RoadDivision> findInterChangeWithLineIdAndMileageLessThan(@Param("lineId") String paramString, @Param("mileage") Integer paramInteger, Pageable paramPageable);
  
  @Query("from RoadDivision d where d.lineId = :lineId  and d.mileage >= :mileage  and (d.divisionType = 'I' or d.divisionType = 'C') ")
  List<RoadDivision> findInterChangeWithLineIdAndMileageGreaterThan(@Param("lineId") String paramString, @Param("mileage") Integer paramInteger, Pageable paramPageable);
  
  List<RoadDivision> findByDivisionIdIn(List<String> paramList);
  
  List<RoadDivision> findByAreaTypeAndDivisionTypeNot(@Param("areaType") AreaType paramAreaType, @Param("divisionType") DivisionType paramDivisionType);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadDivisionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */