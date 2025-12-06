package com.hwacom.ngtms.common.fm.repository;

import com.hwacom.ngtms.common.fm.model.Report;
import com.hwacom.ngtms.common.fm.model.ReportExportFile;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public abstract interface ReportExportFileRepository
  extends JpaRepository<ReportExportFile, String>
{
  @Query("select distinct f.category from ReportExportFile f")
  public abstract Set<String> findAllCategory();
  
  @Query("select distinct f.subCategory from ReportExportFile f where f.category = :category")
  public abstract Set<String> findAllSubCategoryByCategory(@Param("category") String paramString);
  
  public abstract List<ReportExportFile> findByCategoryAndSubCategory(String paramString1, String paramString2);
  
  @Query("select f from ReportExportFile f where f.end < :end")
  public abstract List<ReportExportFile> findRemoveCandidate(Date paramDate);
  
  public abstract ReportExportFile findFirstByNameAndScheduleStart(String paramString, Date paramDate);
  
  public abstract ReportExportFile findFirstByFileIsNullAndReport_ModuleAndScheduleStartBetween(String paramString, Date paramDate1, Date paramDate2, Sort paramSort);
  
  public abstract List<ReportExportFile> findByReport(Report paramReport, Sort paramSort);
  
  public abstract List<ReportExportFile> findByReport_Module(String paramString, Sort paramSort);
  
  public abstract List<ReportExportFile> findByReportAndName(Report paramReport, String paramString, Sort paramSort);
  
  public abstract List<ReportExportFile> findByReportAndEndAfter(Report paramReport, Date paramDate, Sort paramSort);
  
  public abstract List<ReportExportFile> findByReportAndScheduleStart(Report paramReport, Date paramDate, Sort paramSort);
  
  @Query("select s from ReportExportFile s where s.scheduleStart >= :startTime and s.scheduleStart <= :endTime and s.schedulerConfigId = :schedulerConfigId")
  public abstract List<ReportExportFile> findWithStartTimeAndEndTimeAndSchedulerConfigId(@Param("startTime") Date paramDate1, @Param("endTime") Date paramDate2, @Param("schedulerConfigId") String paramString);
  
  public abstract List<ReportExportFile> findBySchedulerConfigId(Long paramLong);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\ReportExportFileRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */