package com.hwacom.ngtms.c.fm.repository;

import com.hwacom.ngtms.c.fm.model.DeviceTcHardwareStatus;
import com.hwacom.ngtms.c.shared.TcProtocolType;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public abstract interface DeviceTcHardwareStatusRepository
  extends JpaRepository<DeviceTcHardwareStatus, String>, JpaSpecificationExecutor<DeviceTcHardwareStatus>
{
  @Query("select id from DeviceTcHardwareStatus")
  public abstract Set<String> findAllKeys();
  
  public abstract List<DeviceTcHardwareStatus> findByDeviceType(String paramString);
  
  public abstract List<DeviceTcHardwareStatus> findByProtocolTypeAndDeviceTypeIn(TcProtocolType paramTcProtocolType, List<String> paramList);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\repository\DeviceTcHardwareStatusRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */