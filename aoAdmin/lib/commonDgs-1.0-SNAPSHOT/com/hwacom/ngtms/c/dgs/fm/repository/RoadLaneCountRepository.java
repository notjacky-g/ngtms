package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RoadLaneCount;
import com.hwacom.ngtms.c.shared.Direction;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadLaneCountRepository extends JpaRepository<RoadLaneCount, String> {
  @Query("select id from RoadLaneCount")
  Set<String> findAllKeys();
  
  @Query("from RoadLaneCount l where l.lineId = :lineId and l.direction = :direction and (  (startMile <= :mileage and endMile >= :mileage)  or (endMile <= :mileage and startMile >= :mileage) )")
  List<RoadLaneCount> findWithEvent(@Param("lineId") String paramString, @Param("direction") Direction paramDirection, @Param("mileage") int paramInt);
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RoadLaneCountRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */