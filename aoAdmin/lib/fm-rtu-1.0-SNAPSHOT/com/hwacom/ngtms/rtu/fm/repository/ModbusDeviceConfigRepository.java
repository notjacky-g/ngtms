package com.hwacom.ngtms.rtu.fm.repository;

import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModbusDeviceConfigRepository extends JpaRepository<ModbusDeviceConfig, String> {
  @Query("select deviceName from #{#entityName} ")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\repository\ModbusDeviceConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */