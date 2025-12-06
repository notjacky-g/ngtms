package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadLine;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadLineRepository extends JpaRepository<RoadLine, String> {
  @Query("select lineId from RoadLine")
  Set<String> findAllKeys();
  
  @Query("from RoadLine l where l.lineId in :lineIds")
  List<RoadLine> findByLineIds(@Param("lineIds") List<String> paramList);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadLineRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */