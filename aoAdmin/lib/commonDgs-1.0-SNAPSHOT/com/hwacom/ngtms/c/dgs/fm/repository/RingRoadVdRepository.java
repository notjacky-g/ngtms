package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RingRoadVd;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RingRoadVdRepository extends JpaRepository<RingRoadVd, String> {
  @Query("select r.id from RingRoadVd r")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RingRoadVdRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */