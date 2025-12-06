package com.hwacom.ngtms.hcce.shared;

import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hwacom.ngtms.base.hazelcast.serializer.HzPortable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class ClientHeartbeat extends HzPortable implements Serializable {

  private static final long serialVersionUID = 1725165508377810464L;

  private String ip;

  private String service;

  private String groupName;

  private String nodeName;

  private Date time;

  public String getId() {
    return ip + "_" + service;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  @Override
  public void writePortable(PortableWriter writer) throws IOException {
    writer.writeUTF("ip", ip);
    writer.writeLong("time", time.getTime());
    writer.writeUTF("groupName", groupName);
    writer.writeUTF("nodeName", nodeName);
    writer.writeUTF("service", service);
  }

  @Override
  public void readPortable(PortableReader reader) throws IOException {
    ip = reader.readUTF("ip");
    time = new Date(reader.readLong("time"));
    groupName = reader.readUTF("groupName");
    nodeName = reader.readUTF("nodeName");
    service = reader.readUTF("service");
  }

  @Override
  public String toString() {
    return "ClientHeartbeat [ip="
        + ip
        + ", service="
        + service
        + ", groupName="
        + groupName
        + ", nodeName="
        + nodeName
        + ", time="
        + time
        + "]";
  }
}
