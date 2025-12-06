package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.TunnelConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface TunnelConfigRepository
  extends JpaRepository<TunnelConfig, String>
{
  @Query("select tunnelId from TunnelConfig")
  public abstract Set<String> findAllKeys();
  
  @Query("from TunnelConfig v where v.tunnelName = :tunnelName")
  public abstract TunnelConfig findByTunnelName(@Param("tunnelName") String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\TunnelConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */