package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.TunnelConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TunnelConfigRepository extends JpaRepository<TunnelConfig, String> {
  @Query("select tunnelId from TunnelConfig")
  Set<String> findAllKeys();
  
  @Query("from TunnelConfig v where v.tunnelName = :tunnelName")
  TunnelConfig findByTunnelName(@Param("tunnelName") String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\TunnelConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */