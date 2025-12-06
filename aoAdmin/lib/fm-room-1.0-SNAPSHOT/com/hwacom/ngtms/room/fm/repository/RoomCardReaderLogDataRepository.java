package com.hwacom.ngtms.room.fm.repository;

import com.hwacom.ngtms.room.fm.model.RoomCardReaderLogData;
import com.hwacom.ngtms.room.shared.EventCode;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCardReaderLogDataRepository extends JpaRepository<RoomCardReaderLogData, Long> {
  List<RoomCardReaderLogData> findByTimeBetweenAndDeviceNameAndEventCodeAndDataValidTrue(Date paramDate1, Date paramDate2, String paramString, EventCode paramEventCode);
}


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\repository\RoomCardReaderLogDataRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */