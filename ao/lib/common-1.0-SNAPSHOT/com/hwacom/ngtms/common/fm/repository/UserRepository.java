package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface UserRepository
  extends JpaRepository<User, String>
{
  @Query("select login from User")
  public abstract Set<String> findAllKeys();
  
  @Query("select u.name from User u where u.login = :login")
  public abstract Set<String> findUserName(@Param("login") String paramString);
  
  public abstract List<User> findByLoginIn(List<String> paramList);
  
  public abstract List<User> findByRoleNames(String paramString);
  
  public abstract List<User> findByRoleNamesIn(List<String> paramList);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\UserRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */