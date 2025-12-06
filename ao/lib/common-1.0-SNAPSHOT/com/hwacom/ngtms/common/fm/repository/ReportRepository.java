package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.Report;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface ReportRepository
  extends JpaRepository<Report, String>
{
  @Query("select distinct r.category from Report r where r.module = :module")
  public abstract Set<String> findAllCategoryByModule(@Param("module") String paramString);
  
  @Query("select distinct r.subCategory from Report r where r.module = :module and r.category = :category")
  public abstract Set<String> findAllSubCategoryByModuleAndCategory(@Param("module") String paramString1, @Param("category") String paramString2);
  
  public abstract List<Report> findByModuleAndCategoryAndSubCategoryOrderByNameAsc(String paramString1, String paramString2, String paramString3);
  
  public abstract Report findFirstByModuleAndName(String paramString1, String paramString2);
  
  public abstract List<Report> findByModule(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\ReportRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */