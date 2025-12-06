package com.hwacom.ngtms.hcce.fme.manager.repository;

import com.hwacom.ngtms.hcce.fme.manager.model.ProhibitNode;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value="oldbTransactionManager", readOnly=true)
public abstract interface ProhibitNodeRepository
  extends JpaRepository<ProhibitNode, Long>
{
  public abstract List<ProhibitNode> findByGroupNameAndFmeName(String paramString1, String paramString2);
  
  public abstract void deleteByGroupName(String paramString);
  
  @Modifying
  @Query("delete from ProhibitNode p where  p.groupName=(?1) and p.id not in (?2)")
  public abstract void deleteByIdNotInGroup(String paramString, Set<Long> paramSet);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\repository\ProhibitNodeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */