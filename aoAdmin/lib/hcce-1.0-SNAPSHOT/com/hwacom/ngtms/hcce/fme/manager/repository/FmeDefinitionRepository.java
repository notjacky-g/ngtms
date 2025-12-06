package com.hwacom.ngtms.hcce.fme.manager.repository;

import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinitionPk;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FmeDefinitionRepository extends JpaRepository<FmeDefinition, FmeDefinitionPk> {
  List<FmeDefinition> findByGroupName(String paramString);
  
  void deleteByGroupName(String paramString);
  
  @Modifying
  @Query("delete from FmeDefinition f where f.groupName=(?1) and f.fmeName not in (?2)")
  void deleteByNameNotInGroup(String paramString, Set<String> paramSet);
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\repository\FmeDefinitionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */