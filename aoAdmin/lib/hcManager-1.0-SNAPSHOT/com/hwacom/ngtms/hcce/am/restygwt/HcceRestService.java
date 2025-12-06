/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.restygwt;

import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import com.hwacom.ngtms.hcce.shared.dto.DynamicConfigDTO;
import com.hwacom.ngtms.hcce.shared.dto.FmeDefinitionDTO;
import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeCfgVO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeStatusDTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("/api/hcManager")
public interface HcceRestService extends RestService {

  @POST
  @Path("/addTopologyNodeCfg")
  void addTopologyNodeCfg(TopologyNodeCfgVO vo, MethodCallback<TopologyNodeCfgVO> callback);

  @POST
  @Path("/saveTopologyNodeCfg")
  void saveTopologyNodeCfg(TopologyNodeCfgVO vo, MethodCallback<TopologyNodeCfgVO> callback);

  @DELETE
  @Path("/deleteTopologyNodeCfg/{groupName}/{nodeName}")
  void deleteTopologyNodeCfg(
      @PathParam("groupName") String groupName,
      @PathParam("nodeName") String nodeName,
      MethodCallback<Boolean> callback);

  @GET
  @Path("/fetchTopologyNodeCfgData/{topologyGroup}")
  void fetchTopologyNodeCfgData(
      @PathParam("topologyGroup") String topologyGroup,
      MethodCallback<List<TopologyNodeCfgVO>> callback);

  @GET
  @Path("/fetchNodeData/{topologyGroup}")
  void fetchNodeData(
      @PathParam("topologyGroup") String topologyGroup, MethodCallback<List<NodeDTO>> callback);

  @POST
  @Path("/saveFme")
  void saveFme(FmeDefinitionDTO vo, MethodCallback<Void> callback);

  @DELETE
  @Path("/deleteFme/{groupName}/{fmeName}")
  void deleteFme(
      @PathParam("groupName") String groupName,
      @PathParam("fmeName") String fmeName,
      MethodCallback<Void> callback);

  @POST
  @Path("/resetFme")
  void resetFme(FmeDefinitionDTO vo, MethodCallback<Boolean> callback);

  @POST
  @Path("/doSystemAction")
  void doSystemAction(ClusterModeDTO clusterMode, MethodCallback<Boolean> callback);

  @POST
  @Path("/startCluster")
  void startCluster(MethodCallback<Boolean> callback);

  @POST
  @Path("/stopCluster")
  void stopCluster(MethodCallback<Boolean> callback);

  @POST
  @Path("/startNode/{topologyGroup}/{nodeName}")
  void startNode(
      @PathParam("topologyGroup") String topologyGroup,
      @PathParam("nodeName") String nodeName,
      MethodCallback<Boolean> callback);

  @POST
  @Path("/stopNode/{topologyGroup}/{nodeName}")
  void stopNode(
      @PathParam("topologyGroup") String topologyGroup,
      @PathParam("nodeName") String nodeName,
      MethodCallback<Boolean> callback);

  @POST
  @Path("/removeNode/{topologyGroup}/{nodeName}")
  void removeNode(
      @PathParam("topologyGroup") String topologyGroup,
      @PathParam("nodeName") String nodeName,
      MethodCallback<Boolean> callback);

  @GET
  @Path("/getSystemEnv")
  void getSystemEnv(MethodCallback<SystemEnvDTO> callback);

  @GET
  @Path("/fetchTopologyNodeStatusData/{topologyGroup}")
  void fetchTopologyNodeStatusData(
      @PathParam("topologyGroup") String topologyGroup,
      MethodCallback<List<TopologyNodeStatusDTO>> callback);

  @GET
  @Path("/fetchFmeDefinitionData/{groupName}")
  void fetchFmeDefinitionData(
      @PathParam("groupName") String groupName, MethodCallback<List<FmeDefinitionDTO>> callback);

  @GET
  @Path("/getFmeDyNamicConfig/{groupName}/{fmeName}/{className}/{topologyGroup}")
  void getFmeDyNamicConfig(
      @PathParam("groupName") String groupName,
      @PathParam("fmeName") String fmeName,
      @PathParam("className") String className,
      @PathParam("topologyGroup") String topologyGroup,
      MethodCallback<List<DynamicConfigDTO>> callback);

  @POST
  @Path("/updateDynamicConfig")
  void updateDynamicConfig(
      DynamicConfigDTO dynamicConfigVo, MethodCallback<DynamicConfigDTO> callback);
}
