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

public abstract interface CmRemote
  extends RemoteInterface
{
  public abstract void startNode(String paramString)
    throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  public abstract void stopNode(String paramString)
    throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  public abstract void restartNode(String paramString)
    throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  public abstract void shutdownCluster()
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void startCluster()
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract CmState getClusterManagerState();
  
  public abstract NodeSnapshot getCoordinatorNodeSnapshot();
  
  public abstract void promoteCluster()
    throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException;
  
  public abstract void demoteCluster()
    throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException;
  
  public abstract ClusterMode clusterAcitveOrStandby()
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void setAllowActive(boolean paramBoolean)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, DisasterRecoveryException;
  
  public abstract void submitFmeDefinition(FmeDefinition paramFmeDefinition)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException;
  
  public abstract void removeFmeDefinition(String paramString1, String paramString2)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException;
  
  public abstract void submitFmeDefTable(String paramString, FmeDefTable paramFmeDefTable)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException, FmeStaleVersionException;
  
  public abstract void resetFme(String paramString)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void addTopologyNodeCfg(TopologyNodeCfg paramTopologyNodeCfg)
    throws TopologyNodeCfgException, ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void removeTopologyNodeCfg(String paramString1, String paramString2)
    throws ClusterManagerNotCoordinatorException, FmExecutorException, ClusterManagerOperationException, ClusterManagerNotReadyException;
  
  public abstract void modifyTopologyCfgNode(TopologyNodeCfg paramTopologyNodeCfg)
    throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException;
  
  public abstract void forceReloadTopologyInfo()
    throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException;
  
  public abstract void addDynamicConfig(DynamicConfig paramDynamicConfig)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void removeDynamicConfig(DynamicConfigPk paramDynamicConfigPk)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void updateDynamicConfig(DynamicConfig paramDynamicConfig)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract void removeFmeConfigs(String paramString1, String paramString2)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract Map<DynamicConfigPk, DynamicConfig> getFmeConfigs(String paramString1, String paramString2)
    throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException;
  
  public abstract DynamicConfigDeclare[] getDynamicConfigDeclare(String paramString)
    throws Throwable;
  
  public abstract HcceEnv getHcceEnv();
  
  public abstract String createSshClient(String paramString1, String paramString2, String paramString3)
    throws IOException;
  
  public abstract String createSshClient(String paramString)
    throws IOException;
  
  public abstract CmdExecResult execCmd(String paramString1, String paramString2)
    throws IOException;
  
  public abstract CmdExecBinaryResult execCmdBinary(String paramString1, String paramString2)
    throws IOException;
  
  public abstract void execCmd(String paramString1, String paramString2, CmdRealTimeResponse paramCmdRealTimeResponse)
    throws IOException;
  
  public abstract void closeSshClient(String paramString);
  
  public abstract Page<NodeMonitorLog> findMonitorLogs(Date paramDate1, Date paramDate2, Pageable paramPageable)
    throws IOException;
  
  public abstract List<NodeMonitorLog> findMonitorLogs(Date paramDate1, Date paramDate2)
    throws IOException;
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\remote\CmRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */