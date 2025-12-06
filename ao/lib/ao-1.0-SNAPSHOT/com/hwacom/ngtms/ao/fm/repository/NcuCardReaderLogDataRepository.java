package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NcuCardReaderLogDataRepository extends JpaRepository<NcuCardReaderLogData, String> {
  @Query("select id from NcuCardReaderLogData")
  Set<String> findAllKeys();
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndNcuIdAndDeviceIdOrderByDataTimeDesc(Date paramDate1, Date paramDate2, String paramString1, String paramString2);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndNcuIdOrderByDataTimeDesc(Date paramDate1, Date paramDate2, String paramString);
  
  List<NcuCardReaderLogData> findByDataTimeBetween(Date paramDate1, Date paramDate2, Sort paramSort);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndNcuId(Date paramDate1, Date paramDate2, String paramString, Sort paramSort);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndDeviceId(Date paramDate1, Date paramDate2, String paramString, Sort paramSort);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndDeviceIdNot(Date paramDate1, Date paramDate2, String paramString, Sort paramSort);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndNcuIdAndDeviceId(Date paramDate1, Date paramDate2, String paramString1, String paramString2);
  
  List<NcuCardReaderLogData> findByDataTimeBetweenAndNcuIdAndDeviceIdNot(Date paramDate1, Date paramDate2, String paramString1, String paramString2);
  
  NcuCardReaderLogData findByDataTimeAndNcuIdAndDeviceIdAndCardNumber(Date paramDate, String paramString1, String paramString2, String paramString3);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\NcuCardReaderLogDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */