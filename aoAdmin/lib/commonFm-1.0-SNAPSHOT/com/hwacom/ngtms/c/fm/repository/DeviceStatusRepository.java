package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, String> {
  DeviceStatus findTopByIdOrderByDataTimeDesc(String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */