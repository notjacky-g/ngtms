package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface DeviceStatusRepository
  extends JpaRepository<DeviceStatus, String>
{
  public abstract DeviceStatus findTopByIdOrderByDataTimeDesc(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */