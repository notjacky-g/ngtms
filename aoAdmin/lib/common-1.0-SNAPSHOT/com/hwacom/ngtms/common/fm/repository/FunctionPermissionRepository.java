package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.FunctionPermission;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FunctionPermissionRepository extends JpaRepository<FunctionPermission, String> {
  @Query("select id from FunctionPermission")
  Set<String> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\FunctionPermissionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */