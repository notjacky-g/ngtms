package com.hwacom.ngtms.hcce.remote;

import com.hwacom.ngtms.base.ssh.CmdExecBinaryResult;
import com.hwacom.ngtms.base.ssh.CmdExecResult;
import com.hwacom.ngtms.base.ssh.CmdRealTimeResponse;
import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotCoordinatorException;
import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
import com.hwacom.ngtms.hcce.core.exception.ClusterManagerOperationException;
import com.hwacom.ngtms.hcce.core.exception.DisasterRecoveryException;
import com.hwacom.ngtms.hcce.core.exception.FmExecutorException;
import com.hwacom.ngtms.hcce.core.exception.FmeStaleVersionException;
import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
import com.hwacom.ngtms.hcce.core.exception.TopologyNodeCfgException;
import com.hwacom.ngtms.hcce.fme.controller.fm.RemoteInterface;
import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
import com.hwacom.ngtms.hcce.shared.ClusterMode;
import com.hwacom.ngtms.hcce.shared.CmState;
import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
import com.hwacom.ngtms.hcce.shared.FmeDefTable;
import com.hwacom.ngtms.hcce.shared.HcceEnv;
import com.hwacom.ngtms.hcce.shared.NodeSnapshot;
import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CmRemote extends RemoteInterface {
  void startNode(String paramString) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  void stopNode(String paramString) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  void restartNode(String paramString) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  void shutdownCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void startCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  CmState getClusterManagerState();
  
  NodeSnapshot getCoordinatorNodeSnapshot();
  
  void promoteCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException;
  
  void demoteCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException;
  
  ClusterMode clusterAcitveOrStandby() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void setAllowActive(boolean paramBoolean) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, DisasterRecoveryException;
  
  void submitFmeDefinition(FmeDefinition paramFmeDefinition) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException;
  
  void removeFmeDefinition(String paramString1, String paramString2) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException;
  
  void submitFmeDefTable(String paramString, FmeDefTable paramFmeDefTable) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException, FmeStaleVersionException;
  
  void resetFme(String paramString) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void addTopologyNodeCfg(TopologyNodeCfg paramTopologyNodeCfg) throws TopologyNodeCfgException, ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void removeTopologyNodeCfg(String paramString1, String paramString2) throws ClusterManagerNotCoordinatorException, FmExecutorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  void modifyTopologyCfgNode(TopologyNodeCfg paramTopologyNodeCfg) throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException;
  
  void forceReloadTopologyInfo() throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException;
  
  void addDynamicConfig(DynamicConfig paramDynamicConfig) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void removeDynamicConfig(DynamicConfigPk paramDynamicConfigPk) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void updateDynamicConfig(DynamicConfig paramDynamicConfig) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  void removeFmeConfigs(String paramString1, String paramString2) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  Map<DynamicConfigPk, DynamicConfig> getFmeConfigs(String paramString1, String paramString2) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  DynamicConfigDeclare[] getDynamicConfigDeclare(String paramString) throws Throwable;
  
  HcceEnv getHcceEnv();
  
  String createSshClient(String paramString1, String paramString2, String paramString3) throws IOException;
  
  String createSshClient(String paramString) throws IOException;
  
  CmdExecResult execCmd(String paramString1, String paramString2) throws IOException;
  
  CmdExecBinaryResult execCmdBinary(String paramString1, String paramString2) throws IOException;
  
  void execCmd(String paramString1, String paramString2, CmdRealTimeResponse paramCmdRealTimeResponse) throws IOException;
  
  void closeSshClient(String paramString);
  
  Page<NodeMonitorLog> findMonitorLogs(Date paramDate1, Date paramDate2, Pageable paramPageable) throws IOException;
  
  List<NodeMonitorLog> findMonitorLogs(Date paramDate1, Date paramDate2) throws IOException;
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\remote\CmRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */