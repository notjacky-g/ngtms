package com.hwacom.ngtms.hcce.nodelog.repository;

import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NodeMonitorLogRepository extends JpaRepository<NodeMonitorLog, String>, JpaSpecificationExecutor<NodeMonitorLog> {}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\nodelog\repository\NodeMonitorLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */