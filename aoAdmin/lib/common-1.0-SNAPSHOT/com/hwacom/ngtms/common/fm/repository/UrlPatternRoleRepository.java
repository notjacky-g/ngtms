package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.UrlPatternRole;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlPatternRoleRepository extends JpaRepository<UrlPatternRole, Long> {
  @Query("select id from UrlPatternRole")
  Set<Long> findAllKeys();
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\UrlPatternRoleRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */