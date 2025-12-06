package com.hwacom.ngtms.hcce.config.repository;

import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface DynamicConfigRepository
  extends JpaRepository<DynamicConfig, DynamicConfigPk>
{
  @Query("select d.id from DynamicConfig d")
  public abstract Set<DynamicConfigPk> findAllKeys();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\config\repository\DynamicConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */