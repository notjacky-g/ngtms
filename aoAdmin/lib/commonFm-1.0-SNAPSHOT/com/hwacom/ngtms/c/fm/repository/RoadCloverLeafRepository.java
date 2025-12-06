package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadCloverLeaf;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.DivisionType;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadCloverLeafRepository extends JpaRepository<RoadCloverLeaf, String> {
  @Query("select id from RoadCloverLeaf")
  Set<String> findAllKeys();
  
  @Query("from RoadCloverLeaf c where c.divisionType = :divisionType and c.lineId1 = :lineId and c.direction1 = :direction and c.mileage1 = :mileage")
  List<RoadCloverLeaf> findWithDivisionTypeAndLineIdAndDirectionAndMileage(@Param("divisionType") DivisionType paramDivisionType, @Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("mileage") int paramInt);
  
  List<RoadCloverLeaf> findByLineId1AndDirection1AndLineId2AndDivisionType(String paramString1, Direction paramDirection, String paramString2, DivisionType paramDivisionType);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadCloverLeafRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */