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

public interface ReportExportFileRepository extends JpaRepository<ReportExportFile, String> {
  @Query("select distinct f.category from ReportExportFile f")
  Set<String> findAllCategory();
  
  @Query("select distinct f.subCategory from ReportExportFile f where f.category = :category")
  Set<String> findAllSubCategoryByCategory(@Param("category") String paramString);
  
  List<ReportExportFile> findByCategoryAndSubCategory(String paramString1, String paramString2);
  
  @Query("select f from ReportExportFile f where f.end < :end")
  List<ReportExportFile> findRemoveCandidate(Date paramDate);
  
  ReportExportFile findFirstByNameAndScheduleStart(String paramString, Date paramDate);
  
  ReportExportFile findFirstByFileIsNullAndReport_ModuleAndScheduleStartBetween(String paramString, Date paramDate1, Date paramDate2, Sort paramSort);
  
  List<ReportExportFile> findByReport(Report paramReport, Sort paramSort);
  
  List<ReportExportFile> findByReport_Module(String paramString, Sort paramSort);
  
  List<ReportExportFile> findByReportAndName(Report paramReport, String paramString, Sort paramSort);
  
  List<ReportExportFile> findByReportAndEndAfter(Report paramReport, Date paramDate, Sort paramSort);
  
  List<ReportExportFile> findByReportAndScheduleStart(Report paramReport, Date paramDate, Sort paramSort);
  
  @Query("select s from ReportExportFile s where s.scheduleStart >= :startTime and s.scheduleStart <= :endTime and s.schedulerConfigId = :schedulerConfigId")
  List<ReportExportFile> findWithStartTimeAndEndTimeAndSchedulerConfigId(@Param("startTime") Date paramDate1, @Param("endTime") Date paramDate2, @Param("schedulerConfigId") String paramString);
  
  List<ReportExportFile> findBySchedulerConfigId(Long paramLong);
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\repository\ReportExportFileRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */