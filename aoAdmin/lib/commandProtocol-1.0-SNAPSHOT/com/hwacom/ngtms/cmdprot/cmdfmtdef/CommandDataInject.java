package com.hwacom.ngtms.cmdprot.cmdfmtdef;

import java.io.InputStream;

public interface CommandDataInject {
  void setCmdBindingObj(Object paramObject);
  
  void setCmdDef(CommandElement paramCommandElement);
  
  void setEncodeData(InputStream paramInputStream);
}


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CommandDataInject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */