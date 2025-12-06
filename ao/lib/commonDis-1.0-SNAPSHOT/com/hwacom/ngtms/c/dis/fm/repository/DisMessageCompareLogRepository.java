package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisMessageCompareLog;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DisMessageCompareLogRepository
  extends JpaRepository<DisMessageCompareLog, Long>
{
  @Query("select id from DisMessageCompareLog ")
  public abstract Set<Long> findAllKeys();
  
  public abstract List<DisMessageCompareLog> findByDeviceNameInAndDataTimeBetween(List<String> paramList, Date paramDate1, Date paramDate2, Sort paramSort);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisMessageCompareLogRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */