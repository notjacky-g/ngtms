/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.restygwt;

import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
import com.hwacom.ngtms.common.shared.dto.ChangePwdDTO;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
import com.hwacom.ngtms.common.shared.dto.ResetPwdDTO;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
import com.hwacom.ngtms.common.shared.dto.UnitDTO;
import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import java.util.List;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

@Path("/api/account")
public interface AccountRestService extends RestService {

  @POST
  @Path("/getUserName")
  void getUserName(String login, TextCallback callback);

  @GET
  @Path("/retrieveUserBasicInfo/{login}")
  void retrieveUserBasicInfo(
      @PathParam("login") String login, MethodCallback<UserBasicInfoDTO> callback);

  @POST
  @Path("/saveUserBasicInfo")
  @Options(dispatcher = AuthDispatcher.class)
  void saveUserBasicInfo(UserBasicInfoDTO dto, MethodCallback<Void> callback);

  @GET
  @Path("/getUsers")
  public void getUsers(MethodCallback<List<UserDTO>> callback);

  @GET
  @Path("/getUserFunctionPermissions/{encryptedUserLogin}")
  void getUserFunctionPermissionIds(
      @PathParam("encryptedUserLogin") String encryptedUserLogin,
      MethodCallback<Set<String>> callback);

  @POST
  @Path("/getUserFunctionPermissions")
  void getUserFunctionPermissions(String encryptedUserLogin, MethodCallback<Set<String>> callback);

  /** @param UserDTO */
  @POST
  @Path("/addUser")
  @Options(dispatcher = AuthDispatcher.class)
  public void addUser(AccountParametersDTO params, MethodCallback<UserDTO> callback);

  /** @param UserDTO */
  @POST
  @Path("/saveUser")
  @Options(dispatcher = AuthDispatcher.class)
  public void saveUser(AccountParametersDTO params, MethodCallback<UserDTO> callback);

  /** @param UserDTO */
  @POST
  @Path("/deleteUser")
  @Options(dispatcher = AuthDispatcher.class)
  public void deleteUser(AccountParametersDTO params, MethodCallback<Boolean> callback);

  /** @param RoleDTO */
  @POST
  @Path("/addRole")
  @Options(dispatcher = AuthDispatcher.class)
  public void addRole(AccountParametersDTO params, MethodCallback<RoleDTO> callback);

  /** @param RoleDTO */
  @POST
  @Path("/saveRole")
  @Options(dispatcher = AuthDispatcher.class)
  public void saveRole(AccountParametersDTO params, MethodCallback<RoleDTO> callback);

  @GET
  @Path("/getRoles")
  public void getRoles(MethodCallback<List<RoleDTO>> callback);

  /** @param RoleDTO */
  @POST
  @Path("/deleteRole")
  @Options(dispatcher = AuthDispatcher.class)
  public void deleteRole(AccountParametersDTO params, MethodCallback<Boolean> callback);

  @GET
  @Path("/getFunctionPermissions")
  public void getFunctionPermissions(MethodCallback<List<FunctionPermissionDTO>> callback);

  /** @param FunctionPermissionDTO */
  @POST
  @Path("/addFunctionPermission")
  @Options(dispatcher = AuthDispatcher.class)
  public void addFunctionPermission(
      AccountParametersDTO params, MethodCallback<FunctionPermissionDTO> callback);

  /** @param FunctionPermissionDTO */
  @POST
  @Path("/saveFunctionPermission")
  @Options(dispatcher = AuthDispatcher.class)
  public void saveFunctionPermission(
      AccountParametersDTO params, MethodCallback<FunctionPermissionDTO> callback);

  /** @param FunctionPermissionDTO */
  @POST
  @Path("/deleteFunctionPermission")
  @Options(dispatcher = AuthDispatcher.class)
  public void deleteFunctionPermission(
      AccountParametersDTO params, MethodCallback<Boolean> callback);

  /**
   * @param user
   * @param password
   */
  @POST
  @Path("/checkAuthorization")
  public void checkAuthorization(AccountParametersDTO params, MethodCallback<Boolean> callback);

  @GET
  @Path("/reloadAccountMapData")
  public void reloadAccountMapData(MethodCallback<Boolean> callback);

  @GET
  @Path("/getUnits")
  public void getUnits(MethodCallback<List<UnitDTO>> callback);

  /** @param UnitDTO */
  @POST
  @Path("/addUnit")
  @Options(dispatcher = AuthDispatcher.class)
  public void addUnit(AccountParametersDTO params, MethodCallback<UnitDTO> callback);

  /** @param UnitDTO */
  @POST
  @Path("/saveUnit")
  @Options(dispatcher = AuthDispatcher.class)
  public void saveUnit(AccountParametersDTO params, MethodCallback<UnitDTO> callback);

  /** @param UnitDTO */
  @POST
  @Path("/deleteUnit")
  @Options(dispatcher = AuthDispatcher.class)
  public void deleteUnit(AccountParametersDTO params, MethodCallback<Boolean> callback);

  @GET
  @Path("/validatePwdExpiration")
  @Options(dispatcher = AuthDispatcher.class)
  public void validatePwdExpiration(MethodCallback<Boolean> callback);

  @POST
  @Path("/resetPwd")
  @Options(dispatcher = AuthDispatcher.class)
  public void resetPwd(String s, MethodCallback<SingleHintMessageDTO> callback);

  @POST
  @Path("/recoverPwd")
  void recoverPwd(RecoverPwdDTO dto, MethodCallback<Void> callback);

  @GET
  @Path("/validateTokenExpiration/{token}")
  void validateTokenExpiration(@PathParam("token") String token, MethodCallback<Boolean> callback);

  @POST
  @Path("/resetPwdWithToken")
  void resetPwdWithToken(ResetPwdDTO dto, MethodCallback<SingleHintMessageDTO> callback);

  @POST
  @Path("/changePwd")
  @Options(dispatcher = AuthDispatcher.class)
  void changePwd(ChangePwdDTO dto, MethodCallback<SingleHintMessageDTO> callback);

  @GET
  @Path("/isLocalAccount")
  @Options(dispatcher = AuthDispatcher.class)
  void isLocalAccount(MethodCallback<Boolean> callback);

  @POST
  @Path("/recoverMfaCode")
  public void recoverMfaCode(RecoverMfaCodeDTO dto, MethodCallback<Void> callback);
}
