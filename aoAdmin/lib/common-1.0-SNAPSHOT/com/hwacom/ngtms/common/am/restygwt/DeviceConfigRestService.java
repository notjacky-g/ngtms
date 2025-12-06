package com.hwacom.ngtms.common.am.restygwt;

import com.hwacom.ngtms.common.shared.dto.DeviceConfigDTO;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("/api/deviceConfig")
public interface DeviceConfigRestService extends RestService {

  @POST
  @Path("/saveDeviceConfig")
  public void saveDeviceConfig(DeviceConfigDTO dto, MethodCallback<Void> callback);
}
