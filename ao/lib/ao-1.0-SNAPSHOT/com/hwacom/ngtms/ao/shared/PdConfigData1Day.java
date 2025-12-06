package com.hwacom.ngtms.ao.shared;

import java.util.List;

public class PdConfigData1Day {

  private String fileName;

  private String controlCenterId;

  private String time;

  private List<EngineRoom> rooms;

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

  public List<EngineRoom> getRooms() {
    return rooms;
  }

  public void setRooms(List<EngineRoom> rooms) {
    this.rooms = rooms;
  }

  public static class EngineRoom {
    private String id;
    private List<PdConfig> pdList;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public List<PdConfig> getPdList() {
      return pdList;
    }

    public void setPd(List<PdConfig> pdList) {
      this.pdList = pdList;
    }

    public static class PdConfig {
      private String id;
      private String freewayId;
      private String directionId;
      private String milepost;
      private String odhId;
      private String odhIp;
      private String longitude;
      private String latitude;
      private List<Loop> loops;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getFreewayId() {
        return freewayId;
      }

      public void setFreewayId(String freewayId) {
        this.freewayId = freewayId;
      }

      public String getDirectionId() {
        return directionId;
      }

      public void setDirectionId(String directionId) {
        this.directionId = directionId;
      }

      public String getMilepost() {
        return milepost;
      }

      public void setMilepost(String milepost) {
        this.milepost = milepost;
      }

      public String getOdhId() {
        return odhId;
      }

      public void setOdhId(String odhId) {
        this.odhId = odhId;
      }

      public String getOdhIp() {
        return odhIp;
      }

      public void setOdhIp(String odhIp) {
        this.odhIp = odhIp;
      }

      public String getLongitude() {
        return longitude;
      }

      public void setLongitude(String longitude) {
        this.longitude = longitude;
      }

      public String getLatitude() {
        return latitude;
      }

      public void setLatitude(String latitude) {
        this.latitude = latitude;
      }

      public List<Loop> getLoops() {
        return loops;
      }

      public void setLoops(List<Loop> loops) {
        this.loops = loops;
      }

      public static class Loop {
        private String id;
        private List<Eq> eqs;

        public String getId() {
          return id;
        }

        public void setId(String id) {
          this.id = id;
        }

        public List<Eq> getEqs() {
          return eqs;
        }

        public void setEqs(List<Eq> eqs) {
          this.eqs = eqs;
        }

        public static class Eq {
          private String eqId;

          public String getEqId() {
            return eqId;
          }

          public void setEqId(String eqId) {
            this.eqId = eqId;
          }
        }
      }
    }
  }
}
