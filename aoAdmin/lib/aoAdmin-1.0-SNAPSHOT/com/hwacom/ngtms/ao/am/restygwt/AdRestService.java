package com.hwacom.ngtms.ao.am.restygwt;

import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

@Path("/api/nvbr/ao/ad")
public interface AdRestService extends RestService {
  @POST
  @Path("/sync")
  @Options(dispatcher = AuthDispatcher.class)
  void sync(MethodCallback<Void> paramMethodCallback);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\restygwt\AdRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */