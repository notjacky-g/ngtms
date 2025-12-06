package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceCategory;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, String> {
  @Query("select id from DeviceCategory")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceCategoryRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */