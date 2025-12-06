package com.hwacom.ngtms.room.service;

public interface RoomCardReaderConnectorService {
  Boolean issueCard(String paramString1, String paramString2);
  
  Boolean deleteCard(String paramString1, String paramString2);
  
  Boolean openDoor(String paramString, Integer paramInteger);
  
  Boolean putOpenDoorPassword(String paramString, Integer paramInteger);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\service\RoomCardReaderConnectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */