package com.hwacom.ngtms.room.service;

public interface RoomCardReaderConnectorService {
  Boolean issueCard(String paramString1, String paramString2);
  
  Boolean deleteCard(String paramString1, String paramString2);
  
  Boolean openDoor(String paramString, Integer paramInteger);
  
  Boolean putOpenDoorPassword(String paramString, Integer paramInteger);
}


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\service\RoomCardReaderConnectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */