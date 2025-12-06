package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.MfccConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MfccConfigRepository extends JpaRepository<MfccConfig, String> {
  @Query("select mfccId from MfccConfig")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\MfccConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */