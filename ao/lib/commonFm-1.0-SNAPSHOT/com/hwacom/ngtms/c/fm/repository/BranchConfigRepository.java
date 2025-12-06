package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.BranchConfig;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface BranchConfigRepository
  extends JpaRepository<BranchConfig, String>
{
  @Query("select id from BranchConfig")
  public abstract Set<String> findAllKeys();
  
  @Query("select e from BranchConfig e where e.branchName = :branchName")
  public abstract List<BranchConfig> findByBranchName(@Param("branchName") String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\BranchConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */