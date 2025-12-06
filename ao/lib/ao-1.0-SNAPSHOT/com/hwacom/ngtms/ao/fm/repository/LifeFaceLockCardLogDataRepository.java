package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardLogData;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LifeFaceLockCardLogDataRepository extends JpaRepository<LifeFaceLockCardLogData, String> {
  @Query("select id from LifeFaceLockCardLogData")
  Set<String> findAllKeys();
  
  List<LifeFaceLockCardLogData> findByDataTimeBetweenAndNcuIdOrderByDataTimeDesc(Date paramDate1, Date paramDate2, String paramString);
  
  List<LifeFaceLockCardLogData> findByNcuIdAndLockCard(String paramString1, String paramString2);
  
  @Modifying
  @Transactional("oldbTransactionManager")
  @Query("update LifeFaceLockCardLogData d set d.isLockCard = :isLockCard where d.ncuId = :ncuId And d.lockCard = :lockCard")
  void updateIsLock(@Param("isLockCard") boolean paramBoolean, @Param("ncuId") String paramString1, @Param("lockCard") String paramString2);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\LifeFaceLockCardLogDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */