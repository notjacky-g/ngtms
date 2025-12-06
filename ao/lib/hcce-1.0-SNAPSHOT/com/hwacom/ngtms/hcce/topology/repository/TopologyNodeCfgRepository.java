package com.hwacom.ngtms.hcce.topology.repository;

import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfgPk;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface TopologyNodeCfgRepository
  extends JpaRepository<TopologyNodeCfg, TopologyNodeCfgPk>
{
  public abstract List<TopologyNodeCfg> findByGroupNameAndNodeNameIn(String paramString, Set<String> paramSet);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\topology\repository\TopologyNodeCfgRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */