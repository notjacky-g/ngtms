package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisTravelTimeConfig;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DisTravelTimeConfigRepository
  extends JpaRepository<DisTravelTimeConfig, String>
{
  @Query("select id from DisTravelTimeConfig ")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisTravelTimeConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */