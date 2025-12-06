package com.hwacom.ngtms.hcce.recovery.repository;

import com.hwacom.ngtms.hcce.frameworkcontext.DrRepository;
import com.hwacom.ngtms.hcce.recovery.model.HcceGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

@DrRepository
public abstract interface HcceGroupInfoRepository
  extends JpaRepository<HcceGroupInfo, String>
{}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\recovery\repository\HcceGroupInfoRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */