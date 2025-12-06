package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RingRoadConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RingRoadConfigRepository extends JpaRepository<RingRoadConfig, String> {
  @Query("select r.id from RingRoadConfig r")
  Set<String> findAllKeys();
  
  @Query("SELECT c.id FROM RingRoadConfig c, RoadDivision d WHERE c.startDivisionId = d.divisionId AND (c.id LIKE :startLineId% AND c.id LIKE %:startDirection% AND c.id LIKE %:endLineId% AND c.id LIKE %:endDirection AND d.divisionName LIKE %:divisionName%) ORDER BY c.id ")
  List<String> findByStartLineIdDirectionAndEndLineIdDirection(@Param("startLineId") String paramString1, @Param("startDirection") String paramString2, @Param("endLineId") String paramString3, @Param("endDirection") String paramString4, @Param("divisionName") String paramString5);
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RingRoadConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */