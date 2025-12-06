package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadLine;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface RoadLineRepository
  extends JpaRepository<RoadLine, String>
{
  @Query("select lineId from RoadLine")
  public abstract Set<String> findAllKeys();
  
  @Query("from RoadLine l where l.lineId in :lineIds")
  public abstract List<RoadLine> findByLineIds(@Param("lineIds") List<String> paramList);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadLineRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */