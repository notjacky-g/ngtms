/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared;

import com.hwacom.ngtms.c.shared.Direction;
import java.util.Date;
import java.util.List;

public class CctvDeviceConfigData {

  private Date updateTime;

  private List<NvrServer> nvrServers;

  private List<BroadcastServer> broadcastServers;

  private List<VideoEncoder> videoEncoders;

  private List<Camera> cameras;

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public List<NvrServer> getNvrServers() {
    return nvrServers;
  }

  public void setNvrServers(List<NvrServer> nvrServers) {
    this.nvrServers = nvrServers;
  }

  public List<BroadcastServer> getBroadcastServers() {
    return broadcastServers;
  }

  public void setBroadcastServers(List<BroadcastServer> broadcastServers) {
    this.broadcastServers = broadcastServers;
  }

  public List<VideoEncoder> getVideoEncoders() {
    return videoEncoders;
  }

  public void setVideoEncoders(List<VideoEncoder> videoEncoders) {
    this.videoEncoders = videoEncoders;
  }

  public List<Camera> getCameras() {
    return cameras;
  }

  public void setCameras(List<Camera> cameras) {
    this.cameras = cameras;
  }

  public static class Server {
    private String id;
    private String name;
    private String ip;
    private String port;
    private String onvifPort;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public String getPort() {
      return port;
    }

    public void setPort(String port) {
      this.port = port;
    }

    public String getOnvifPort() {
      return onvifPort;
    }

    public void setOnvifPort(String onvifPort) {
      this.onvifPort = onvifPort;
    }
  }

  public static class NvrServer extends Server {
    private List<Storage> storages;

    public List<Storage> getStorages() {
      return storages;
    }

    public void setStorages(List<Storage> storages) {
      this.storages = storages;
    }
  }

  public static class Storage {
    private String id;
    private String name;
    private String ip;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }
  }

  public static class BroadcastServer extends Server {}

  public static class VideoEncoder {
    private String id;
    private String name;
    private String ip;
    private int port;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }
  }

  public static class Camera {
    private String id;
    private String name;
    private String ip;
    private Integer port;
    private Integer cctvId;
    private String px;
    private String py;
    private String lineId;
    private Integer mileage;
    private Direction direction;
    private List<CameraUrl> urls;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public Integer getPort() {
      return port;
    }

    public void setPort(Integer port) {
      this.port = port;
    }

    public Integer getCctvId() {
      return cctvId;
    }

    public void setCctvId(Integer cctvId) {
      this.cctvId = cctvId;
    }

    public String getPx() {
      return px;
    }

    public void setPx(String px) {
      this.px = px;
    }

    public String getPy() {
      return py;
    }

    public void setPy(String py) {
      this.py = py;
    }

    public String getLineId() {
      return lineId;
    }

    public Integer getMileage() {
      return mileage;
    }

    public void setMileage(Integer mileage) {
      this.mileage = mileage;
    }

    public void setLineId(String lineId) {
      this.lineId = lineId;
    }

    public List<CameraUrl> getUrls() {
      return urls;
    }

    public void setUrls(List<CameraUrl> urls) {
      this.urls = urls;
    }

    public Direction getDirection() {
      return direction;
    }

    public void setDirection(Direction direction) {
      this.direction = direction;
    }
  }

  public static class CameraUrl {
    private String protocol;
    private String url;
    private String type;
    private String resolution;

    public String getProtocol() {
      return protocol;
    }

    public void setProtocol(String protocol) {
      this.protocol = protocol;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getResolution() {
      return resolution;
    }

    public void setResolution(String resolution) {
      this.resolution = resolution;
    }
  }
}
