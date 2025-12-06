package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadGps;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface RoadGpsRepository
  extends JpaRepository<RoadGps, Long>
{
  @Query("select no from RoadGps")
  public abstract Set<Long> findAllKeys();
  
  public abstract RoadGps findFirstByLineIdAndKm1GreaterThanEqualOrderByKm1Asc(String paramString, Double paramDouble);
  
  public abstract RoadGps findFirstByLineIdAndKm1LessThanEqualOrderByKm1Desc(String paramString, Double paramDouble);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadGpsRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */