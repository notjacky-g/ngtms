package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.RoadTunnelDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadTunnelDetailRepository extends JpaRepository<RoadTunnelDetail, String> {
  @Query("select t from RoadTunnelDetail t, RoadSection s where s.sectionId = t.sectionId and s.lineId = :lineId and t.startMileage <= :mileage and t.endMileage >= :mileage")
  List<RoadTunnelDetail> findWithLineIdAndMileageAsc(@Param("lineId") String paramString, @Param("mileage") int paramInt);
  
  @Query("select t from RoadTunnelDetail t, RoadSection s where s.sectionId = t.sectionId and s.lineId = :lineId and t.endMileage <= :mileage and t.startMileage >= :mileage")
  List<RoadTunnelDetail> findWithLineIdAndMileageDesc(@Param("lineId") String paramString, @Param("mileage") int paramInt);
  
  @Query("select t from RoadTunnelDetail t, RoadSection s where s.sectionId = t.sectionId and s.lineId = :lineId and t.startMileage < t.endMileage and ((t.startMileage <= :startMileage and t.endMileage >= :startMileage)      or (t.startMileage <= :endMileage and t.endMileage >= :endMileage)      or (t.startMileage >= :startMileage and t.endMileage <= :endMileage))")
  List<RoadTunnelDetail> findWithLineIdAndStartMileageAndEndMileageAsc(@Param("lineId") String paramString, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2);
  
  @Query("select t from RoadTunnelDetail t, RoadSection s where s.sectionId = t.sectionId and s.lineId = :lineId and t.startMileage > t.endMileage and ((t.endMileage <= :startMileage and t.startMileage >= :startMileage)      or (t.endMileage <= :endMileage and t.startMileage >= :endMileage)      or (t.endMileage >= :endMileage and t.startMileage <= :startMileage))")
  List<RoadTunnelDetail> findWithLineIdAndStartMileageAndEndMileageDesc(@Param("lineId") String paramString, @Param("startMileage") int paramInt1, @Param("endMileage") int paramInt2);
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\RoadTunnelDetailRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */