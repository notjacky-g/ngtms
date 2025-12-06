package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.RoadUnitSection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoadUnitSectionRepository extends JpaRepository<RoadUnitSection, String> {
  @Query("select unitId from RoadUnitSection")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\RoadUnitSectionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */