package com.hwacom.ngtms.hcce.nodelog.repository;

import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public abstract interface NodeMonitorLogRepository
  extends JpaRepository<NodeMonitorLog, String>, JpaSpecificationExecutor<NodeMonitorLog>
{}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\nodelog\repository\NodeMonitorLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */