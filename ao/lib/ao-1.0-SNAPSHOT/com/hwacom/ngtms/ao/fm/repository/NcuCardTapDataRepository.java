package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.NcuCardTapData;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NcuCardTapDataRepository extends JpaRepository<NcuCardTapData, String> {
  @Query("select id from NcuCardTapData")
  Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\NcuCardTapDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */