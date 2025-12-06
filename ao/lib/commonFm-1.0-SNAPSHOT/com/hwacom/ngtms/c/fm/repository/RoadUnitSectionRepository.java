package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadUnitSection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface RoadUnitSectionRepository
  extends JpaRepository<RoadUnitSection, String>
{
  @Query("select unitId from RoadUnitSection")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadUnitSectionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */