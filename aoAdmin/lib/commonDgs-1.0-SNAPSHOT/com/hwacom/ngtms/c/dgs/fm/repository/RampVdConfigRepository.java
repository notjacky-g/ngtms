package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RampVdConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RampVdConfigRepository extends JpaRepository<RampVdConfig, Integer> {
  @Query("select id from RampVdConfig")
  Set<Integer> findAllKeys();
  
  @Query("select max(h.id) from RampVdConfig h")
  Integer findMaxId();
  
  @Query("from RampVdConfig r where r.degreeVd1 = :degreeVd or r.degreeVd2 = :degreeVd or r.degreeVd3 = :degreeVd or r.degreeVd4 = :degreeVd or r.degreeVd5 = :degreeVd or r.degreeVd6 = :degreeVd")
  List<RampVdConfig> findWithDegreeVd(@Param("degreeVd") String paramString);
  
  List<RampVdConfig> findByDivisionIdIn(List<String> paramList, Sort paramSort);
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RampVdConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */