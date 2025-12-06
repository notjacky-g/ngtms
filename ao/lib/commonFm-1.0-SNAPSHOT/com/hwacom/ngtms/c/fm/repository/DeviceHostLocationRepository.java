package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DeviceHostLocationRepository
  extends JpaRepository<DeviceHostLocation, Integer>
{
  @Query("select id from DeviceHostLocation")
  public abstract Set<Integer> findAllKeys();
  
  @Query("select max(h.id) from DeviceHostLocation h")
  public abstract Integer findMaxId();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceHostLocationRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */