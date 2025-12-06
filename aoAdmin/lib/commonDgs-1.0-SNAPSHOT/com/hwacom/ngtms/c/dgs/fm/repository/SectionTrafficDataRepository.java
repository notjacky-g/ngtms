package com.hwacom.ngtms.c.dgs.fm.repository;

import com.hwacom.ngtms.c.dgs.fm.model.SectionTrafficData;
import com.hwacom.ngtms.c.shared.DateClassType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SectionTrafficDataRepository extends JpaRepository<SectionTrafficData, String> {
  List<SectionTrafficData> findByDataTimeBetweenAndSectionId(Date paramDate1, Date paramDate2, String paramString);
  
  List<SectionTrafficData> findByDataTimeBetweenAndSectionIdOrderByDataTimeAsc(Date paramDate1, Date paramDate2, String paramString);
  
  List<SectionTrafficData> findByDataTimeBetweenAndSectionIdIn(Date paramDate1, Date paramDate2, Collection<String> paramCollection);
  
  List<SectionTrafficData> findByDataTimeBetweenAndSectionIdAndDataType(Date paramDate1, Date paramDate2, String paramString, DateClassType paramDateClassType);
  
  @Query("select s from SectionTrafficData s where s.dataTime >= :start and s.dataTime < :end  and sectionId = :sectionId and dataType = :type and MONTH(dataTime) in :months")
  List<SectionTrafficData> findByDataTimeAndSectionAndTypeAndMonth(@Param("start") Date paramDate1, @Param("end") Date paramDate2, @Param("sectionId") String paramString, @Param("type") DateClassType paramDateClassType, @Param("months") Collection<Integer> paramCollection);
  
  @Query("select sum(s.carVolume) from SectionTrafficData s where s.sectionId = :sectionId and s.dataTime between :startDateTime and :endDateTime and s.carVolume <> -1")
  Long findCarVolumeSumBySectionIdAndTime(@Param("sectionId") String paramString, @Param("startDateTime") Date paramDate1, @Param("endDateTime") Date paramDate2);
  
  List<SectionTrafficData> findByDataTimeAndSectionIdIn(@Param("dataTime") Date paramDate, @Param("sectionIds") List<String> paramList);
  
  SectionTrafficData findFirstByOrderByDataTimeDesc();
}


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\repository\SectionTrafficDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */