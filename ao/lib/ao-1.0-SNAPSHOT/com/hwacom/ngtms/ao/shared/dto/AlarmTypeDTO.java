package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.ao.shared.AlarmType;
import java.io.Serializable;

public class AlarmTypeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 780891056851970178L;

  private AlarmType alarmType;

  private boolean all;

  public AlarmType getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(AlarmType alarmType) {
    this.alarmType = alarmType;
  }

  public boolean isAll() {
    return all;
  }

  public void setAll(boolean all) {
    this.all = all;
  }
}
