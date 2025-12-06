package com.hwacom.ngtms.node.performance.fm.repository;

import com.hwacom.ngtms.node.performance.fm.model.NodePerformanceLog;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface NodePerformanceLogRepository
  extends JpaRepository<NodePerformanceLog, Long>, JpaSpecificationExecutor<NodePerformanceLog>
{
  @Query("from NodePerformanceLog c where c.recordTime between :startDateTime and :endDateTime")
  public abstract List<NodePerformanceLog> findByDate(@Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
  
  @Query("from NodePerformanceLog c where c.nodeName in :nodeNames and c.recordTime between :startDateTime and :endDateTime")
  public abstract List<NodePerformanceLog> findByNodeNamesAndDate(@Param("nodeNames") List<String> paramList, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2, Sort paramSort);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\repository\NodePerformanceLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */