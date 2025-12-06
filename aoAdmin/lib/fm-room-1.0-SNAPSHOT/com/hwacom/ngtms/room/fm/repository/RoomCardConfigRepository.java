package com.hwacom.ngtms.room.fm.repository;

import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
import com.hwacom.ngtms.room.shared.CardType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomCardConfigRepository extends JpaRepository<RoomCardConfig, String> {
  @Query("select id from #{#entityName} ")
  Set<String> findAllKeys();
  
  @Query("select s from RoomCardConfig s where s.cardType = :cardType and s.startDate >= :startDate and s.startDate <= :endDate ")
  List<RoomCardConfig> findWithCardTypeAndStartDateBetween(@Param("cardType") CardType paramCardType, @Param("startDate") Date paramDate1, @Param("endDate") Date paramDate2);
  
  @Query("select s from RoomCardConfig s where s.cardType = :cardType and s.endDate >= :startDate and s.endDate <= :endDate ")
  List<RoomCardConfig> findWithCardTypeAndEndDateBetween(@Param("cardType") CardType paramCardType, @Param("startDate") Date paramDate1, @Param("endDate") Date paramDate2);
  
  List<RoomCardConfig> findByCardTypeAndStartDateGreaterThanEqual(CardType paramCardType, Date paramDate);
  
  List<RoomCardConfig> findByCardTypeAndEndDateLessThanEqual(CardType paramCardType, Date paramDate);
  
  List<RoomCardConfig> findByCardType(CardType paramCardType);
  
  List<RoomCardConfig> findByCardTypeInAndReturnDateIsNullAndExpectedReturnDateLessThan(List<CardType> paramList, Date paramDate);
}


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\repository\RoomCardConfigRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */