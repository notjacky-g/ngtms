package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.BranchConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchConfigRepository extends JpaRepository<BranchConfig, String> {
  @Query("select id from BranchConfig")
  Set<String> findAllKeys();
  
  @Query("select e from BranchConfig e where e.branchName = :branchName")
  List<BranchConfig> findByBranchName(@Param("branchName") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\BranchConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */