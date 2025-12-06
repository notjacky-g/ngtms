package com.hwacom.ngtms.rtu.fm.repository;

import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModbusPinMappingRepository extends JpaRepository<ModbusPinMapping, Long> {
  @Query("select id from #{#entityName} ")
  Set<Long> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\repository\ModbusPinMappingRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */