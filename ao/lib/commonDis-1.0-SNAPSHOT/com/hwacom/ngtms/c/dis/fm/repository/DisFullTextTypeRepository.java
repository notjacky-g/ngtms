package com.hwacom.ngtms.c.dis.fm.repository;

import com.hwacom.ngtms.c.dis.fm.model.DisFullTextType;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DisFullTextTypeRepository
  extends JpaRepository<DisFullTextType, String>
{
  @Query("select id from DisFullTextType ")
  public abstract Set<String> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\repository\DisFullTextTypeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */