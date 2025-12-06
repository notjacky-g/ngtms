package com.hwacom.ngtms.cmdprot.cmdfmtdef;

import java.io.InputStream;

public abstract interface CommandDataInject
{
  public abstract void setCmdBindingObj(Object paramObject);
  
  public abstract void setCmdDef(CommandElement paramCommandElement);
  
  public abstract void setEncodeData(InputStream paramInputStream);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CommandDataInject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */