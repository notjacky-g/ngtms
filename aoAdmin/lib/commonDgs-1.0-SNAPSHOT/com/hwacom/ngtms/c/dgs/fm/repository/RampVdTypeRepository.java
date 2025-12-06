package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RampVdType;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RampVdTypeRepository extends JpaRepository<RampVdType, Integer> {
  @Query("select id from RampVdType")
  Set<Integer> findAllKeys();
  
  @Query("select max(h.id) from RampVdType h")
  Integer findMaxId();
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RampVdTypeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */