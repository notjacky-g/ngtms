package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisGraphicConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisGraphicConfigRepository extends JpaRepository<DisGraphicConfig, String> {
  @Query("select id from DisGraphicConfig ")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisGraphicConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */