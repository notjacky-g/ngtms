package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadSection;
import com.hwacom.ngtms.c.shared.AreaType;
import com.hwacom.ngtms.c.shared.Direction;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadSectionRepository extends JpaRepository<RoadSection, String> {
  @Query("select sectionId from RoadSection")
  Set<String> findAllKeys();
  
  @Query("select s from RoadSection s, RoadDivision d1, RoadDivision d2 where s.startDivisionId = d1.divisionId and s.endDivisionId = d2.divisionId and s.lineId = :lineId and s.direction = :direction and ((d1.mileage <= :mileage and d2.mileage >= :mileage) or (d1.mileage >= :mileage and d2.mileage <= :mileage))")
  List<RoadSection> findWithLineIdAndDirectionAndMileage(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("mileage") int paramInt);
  
  @Query("select s.sectionId from RoadSection s where s.startDivisionId = :divisionId or s.endDivisionId = :divisionId")
  Set<String> findSectionIdsWithDivisionId(@Param("divisionId") String paramString);
  
  @Query("select s.sectionId from RoadSection s where s.direction = :direction and (s.startDivisionId = :divisionId or s.endDivisionId = :divisionId)")
  Set<String> findSectionIdsWithDivisionIdAndDirection(@Param("divisionId") String paramString, @Param("direction") Direction paramDirection);
  
  @Query("from RoadSection s where s.sectionId in :sectionIds")
  List<RoadSection> findBySectionIds(@Param("sectionIds") List<String> paramList);
  
  List<RoadSection> findByAreaType(@Param("areaType") AreaType paramAreaType);
  
  RoadSection findBySectionId(@Param("sectionId") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadSectionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */