package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface RoleRepository
  extends JpaRepository<Role, String>
{
  @Query("select name from Role")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\RoleRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */