/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared;

import java.util.List;

public class CctvDeviceStatusData {

  private String updateTime;

  private long interval;

  private List<NvrServer> nvrServers;

  private List<BroadcastServer> broadcastServers;

  private List<VideoEncoder> videoEncoders;

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public long getInterval() {
    return interval;
  }

  public void setInterval(long interval) {
    this.interval = interval;
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

  public static class Server {
    private String id;
    private String name;
    private boolean connected;
    private boolean operating;

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

    public boolean isConnected() {
      return connected;
    }

    public void setConnected(boolean connected) {
      this.connected = connected;
    }

    public boolean isOperating() {
      return operating;
    }

    public void setOperating(boolean operating) {
      this.operating = operating;
    }
  }

  public static class NvrServer extends Server {

    private List<Camera> cameras;

    private List<Storage> storages;

    public List<Camera> getCameras() {
      return cameras;
    }

    public void setCameras(List<Camera> cameras) {
      this.cameras = cameras;
    }

    public List<Storage> getStorages() {
      return storages;
    }

    public void setStorages(List<Storage> storages) {
      this.storages = storages;
    }
  }

  public static class Camera {
    private String id;
    private String name;
    private Integer cctvId;
    private String url;
    private String standardDefinition;
    private int fps;
    private int bitrate;
    private boolean connected;
    private boolean recording;

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

    public Integer getCctvId() {
      return cctvId;
    }

    public void setCctvId(Integer cctvId) {
      this.cctvId = cctvId;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getStandardDefinition() {
      return standardDefinition;
    }

    public void setStandardDefinition(String standardDefinition) {
      this.standardDefinition = standardDefinition;
    }

    public int getFps() {
      return fps;
    }

    public void setFps(int fps) {
      this.fps = fps;
    }

    public int getBitrate() {
      return bitrate;
    }

    public void setBitrate(int bitrate) {
      this.bitrate = bitrate;
    }

    public boolean isConnected() {
      return connected;
    }

    public void setConnected(boolean connected) {
      this.connected = connected;
    }

    public boolean isRecording() {
      return recording;
    }

    public void setRecording(boolean recording) {
      this.recording = recording;
    }
  }

  public static class Storage {
    private String id;
    private String name;
    private boolean connected;
    private Boolean storageError;
    private Boolean powerError;

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

    public boolean isConnected() {
      return connected;
    }

    public void setConnected(boolean connected) {
      this.connected = connected;
    }

    public Boolean getStorageError() {
      return storageError;
    }

    public void setStorageError(Boolean storageError) {
      this.storageError = storageError;
    }

    public Boolean getPowerError() {
      return powerError;
    }

    public void setPowerError(Boolean powerError) {
      this.powerError = powerError;
    }
  }

  public static class BroadcastServer extends Server {
    private boolean internetConnected;
    private int clientCount;
    private int clientConnected;
    private int clientDisconnected;

    private List<Camera> cameras;

    public boolean isInternetConnected() {
      return internetConnected;
    }

    public void setInternetConnected(boolean internetConnected) {
      this.internetConnected = internetConnected;
    }

    public int getClientCount() {
      return clientCount;
    }

    public void setClientCount(int clientCount) {
      this.clientCount = clientCount;
    }

    public int getClientConnected() {
      return clientConnected;
    }

    public void setClientConnected(int clientConnected) {
      this.clientConnected = clientConnected;
    }

    public int getClientDisconnected() {
      return clientDisconnected;
    }

    public void setClientDisconnected(int clientDisconnected) {
      this.clientDisconnected = clientDisconnected;
    }

    public List<Camera> getCameras() {
      return cameras;
    }

    public void setCameras(List<Camera> cameras) {
      this.cameras = cameras;
    }
  }

  public static class VideoEncoder {
    private String id;
    private String name;
    private boolean connected;
    private boolean operating;

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

    public boolean isConnected() {
      return connected;
    }

    public void setConnected(boolean connected) {
      this.connected = connected;
    }

    public boolean isOperating() {
      return operating;
    }

    public void setOperating(boolean operating) {
      this.operating = operating;
    }
  }

  public static class VideoEncoderCamera {
    private String id;
    private String name;
    private boolean videoStreamOutput;
    private String cctvId;
    private String url;

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

    public boolean isVideoStreamOutput() {
      return videoStreamOutput;
    }

    public void setVideoStreamOutput(boolean videoStreamOutput) {
      this.videoStreamOutput = videoStreamOutput;
    }

    public String getCctvId() {
      return cctvId;
    }

    public void setCctvId(String cctvId) {
      this.cctvId = cctvId;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
