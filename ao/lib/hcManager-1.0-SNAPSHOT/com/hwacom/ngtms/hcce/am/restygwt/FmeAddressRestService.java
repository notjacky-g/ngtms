/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.restygwt;

import com.hwacom.ngtms.base.shared.Pair;
import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

@Path("/first")
public interface FmeAddressRestService extends RestService {

  @GET
  @Path("/getClusterMode")
  void getClusterMode(MethodCallback<ClusterModeDTO> callback);

  /**
   * 取得模組 FM IP 位址
   *
   * @param moduleName
   */
  @POST
  @Path("/getFmeAddress")
  public void getFmeAddress(String fmeName, MethodCallback<Pair<String, Integer>> callback);

  @GET
  @Path("/retrieveArbitraryNodeIpAndPort")
  public void retrieveArbitraryNodeIpAndPort(MethodCallback<Pair<String, Integer>> callback);

  @GET
  @Path("/retrieveSpecificIpAndPort")
  public void retrieveSpecificIpAndPort(MethodCallback<Pair<String, Integer>> callback);

  @GET
  @Path("/retrieveArbitraryBackupNodeIpAndPort")
  void retrieveArbitraryBackupNodeIpAndPort(MethodCallback<Pair<String, Integer>> callback);

  @GET
  @Path("/retrieveEncryptedUserLogin")
  void retrieveEncryptedUserLogin(TextCallback callback);

  @GET
  @Path("/checkNginxActivated")
  void checkNginxActivated(MethodCallback<Boolean> callback);

  @GET
  @Path("/keepHttpSessionAlive")
  void keepHttpSessionAlive(MethodCallback<Void> callback);

  @GET
  @Path("/isHttpSessionTimeout")
  void isHttpSessionTimeout(MethodCallback<Boolean> callback);
}
