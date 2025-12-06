package com.hwacom.ngtms.ao.fm.repository;

import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WaterPowerBaseConfigRepository extends JpaRepository<WaterPowerBaseConfig, String> {
  @Query("select id from WaterPowerBaseConfig")
  Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\repository\WaterPowerBaseConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */