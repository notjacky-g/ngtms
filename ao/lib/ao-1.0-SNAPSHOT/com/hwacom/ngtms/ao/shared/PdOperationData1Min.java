package com.hwacom.ngtms.ao.shared;

import java.util.List;

public class PdOperationData1Min {

  private String fileName;

  private String controlCenterId;

  private String time;

  private List<Pd> pdList;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getControlCenterId() {
    return controlCenterId;
  }

  public void setControlCenterId(String controlCenterId) {
    this.controlCenterId = controlCenterId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public List<Pd> getPdList() {
    return pdList;
  }

  public void setPdList(List<Pd> pdList) {
    this.pdList = pdList;
  }

  public static class Pd {
    private String id;
    private String pdCommStatus;
    private Primary primary;
    private Secondary secondary;
    private LoopList loops;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getPdCommonStatus() {
      return pdCommStatus;
    }

    public void setPdCommonStatus(String pdCommStatus) {
      this.pdCommStatus = pdCommStatus;
    }

    public Primary getPrimary() {
      return primary;
    }

    public void setPrimary(Primary primary) {
      this.primary = primary;
    }

    public Secondary getSecondary() {
      return secondary;
    }

    public void setSecondary(Secondary secondary) {
      this.secondary = secondary;
    }

    public LoopList getLoopList() {
      return loops;
    }

    public void setLoopList(LoopList loops) {
      this.loops = loops;
    }

    public static class Primary {
      String r;
      String s;
      String t;

      public String getR() {
        return r;
      }

      public void setR(String r) {
        this.r = r;
      }

      public String getS() {
        return s;
      }

      public void setS(String s) {
        this.s = s;
      }

      public String getT() {
        return t;
      }

      public void setT(String t) {
        this.t = t;
      }
    }

    public static class Loop {
      String id;
      String usage;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getUsage() {
        return usage;
      }

      public void setUsage(String usage) {
        this.usage = usage;
      }
    }

    public static class Secondary {
      String r;
      String s;
      String t;

      public String getR() {
        return r;
      }

      public void setR(String r) {
        this.r = r;
      }

      public String getS() {
        return s;
      }

      public void setS(String s) {
        this.s = s;
      }

      public String getT() {
        return t;
      }

      public void setT(String t) {
        this.t = t;
      }
    }

    public static class LoopList {
      String capacity;
      List<Loop> loops;

      public String getCapacity() {
        return capacity;
      }

      public void setCapacity(String capacity) {
        this.capacity = capacity;
      }

      public List<Loop> getLoops() {
        return loops;
      }

      public void setLoops(List<Loop> loops) {
        this.loops = loops;
      }
    }
  }
}
