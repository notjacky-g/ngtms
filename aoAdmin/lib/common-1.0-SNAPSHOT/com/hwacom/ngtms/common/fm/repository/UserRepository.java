package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
  @Query("select login from User")
  Set<String> findAllKeys();
  
  @Query("select u.name from User u where u.login = :login")
  Set<String> findUserName(@Param("login") String paramString);
  
  List<User> findByLoginIn(List<String> paramList);
  
  List<User> findByRoleNames(String paramString);
  
  List<User> findByRoleNamesIn(List<String> paramList);
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\UserRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */