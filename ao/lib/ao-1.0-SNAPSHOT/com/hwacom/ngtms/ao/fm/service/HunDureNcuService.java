package com.hwacom.ngtms.ao.fm.service;

import com.hwacom.ngtms.ao.shared.NCUCardData;
import com.hwacom.ngtms.ao.shared.NCUDateTime;
import com.hwacom.ngtms.ao.shared.NCUInfoData;
import com.hwacom.ngtms.ao.shared.NCUReceiveRecord;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface HunDureNcuService {
  Boolean checkNCUConnect(String paramString);
  
  NCUInfoData getNUCInfo(String paramString);
  
  NCUDateTime getNCUTime(String paramString);
  
  Boolean synchronizeNCUTime(String paramString);
  
  Boolean openDoor(String paramString1, String paramString2, HttpServletRequest paramHttpServletRequest);
  
  Boolean readDoor(String paramString1, String paramString2);
  
  int addCard(String paramString, NCUCardData paramNCUCardData);
  
  int modifyCard(String paramString, NCUCardData paramNCUCardData);
  
  int deleteCard(String paramString, NCUCardData paramNCUCardData);
  
  NCUCardData queryCard(String paramString, NCUCardData paramNCUCardData);
  
  int addAllCards(String paramString, List<NCUCardData> paramList);
  
  Boolean deleteAllCards(String paramString);
  
  List<NCUReceiveRecord> getNCUCardLog(String paramString);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\HunDureNcuService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */