package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadGps;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoadGpsRepository extends JpaRepository<RoadGps, Long> {
  @Query("select no from RoadGps")
  Set<Long> findAllKeys();
  
  RoadGps findFirstByLineIdAndKm1GreaterThanEqualOrderByKm1Asc(String paramString, Double paramDouble);
  
  RoadGps findFirstByLineIdAndKm1LessThanEqualOrderByKm1Desc(String paramString, Double paramDouble);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadGpsRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */