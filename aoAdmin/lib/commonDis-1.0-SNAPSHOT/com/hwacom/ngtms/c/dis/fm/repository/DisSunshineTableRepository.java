package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisSunshineTable;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisSunshineTableRepository extends JpaRepository<DisSunshineTable, String> {
  @Query("select sunshineDate from  DisSunshineTable ")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisSunshineTableRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */