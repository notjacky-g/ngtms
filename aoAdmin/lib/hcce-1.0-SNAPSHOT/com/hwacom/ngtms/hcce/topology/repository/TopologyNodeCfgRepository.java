package com.hwacom.ngtms.hcce.topology.repository;

import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfgPk;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopologyNodeCfgRepository extends JpaRepository<TopologyNodeCfg, TopologyNodeCfgPk> {
  List<TopologyNodeCfg> findByGroupNameAndNodeNameIn(String paramString, Set<String> paramSet);
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\topology\repository\TopologyNodeCfgRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */