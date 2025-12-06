package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardData;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LifeFaceLockCardDataRepository extends JpaRepository<LifeFaceLockCardData, String> {
  @Query("select id from LifeFaceLockCardData")
  Set<String> findAllKeys();
  
  List<LifeFaceLockCardData> findByDataTimeBetweenAndNcuIdOrderByDataTimeDesc(Date paramDate1, Date paramDate2, String paramString);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\LifeFaceLockCardDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */