/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.restygwt;

import com.hwacom.ngtms.c.shared.DivisionType;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("/api/road")
public interface RoadRestService extends RestService {

  @GET
  @Path("/roadLine")
  public void getRoadLines(MethodCallback<List<RoadLineDTO>> callback);

  /** @param RoadLineDTO */
  @POST
  @Path("/roadLine")
  public void addRoadLine(RoadParametersDTO params, MethodCallback<Boolean> callback);

  /** @param lineId */
  @DELETE
  @Path("/roadLine/{lineId}")
  public void removeRoadLine(@PathParam("lineId") String lineId, MethodCallback<Boolean> callback);

  /** @param RoadLineDTO */
  @PUT
  @Path("/roadLine")
  public void saveRoadLine(RoadParametersDTO params, MethodCallback<Boolean> callback);

  @GET
  @Path("/roadDivision")
  public void getRoadDivisions(MethodCallback<List<RoadDivisionDTO>> callback);

  /** @param DivisionType */
  @GET
  @Path("/roadDivision/{divisionType}")
  public void getRoadDivisionsByDivisionType(
      @PathParam("divisionType") DivisionType divisionType,
      MethodCallback<List<RoadDivisionDTO>> callback);

  /** @param RoadDivisionDTO */
  @PUT
  @Path("/roadDivision")
  public void saveRoadDivision(RoadParametersDTO params, MethodCallback<Boolean> callback);

  /** @param RoadDivisionDTO */
  @POST
  @Path("/roadDivision")
  public void addRoadDivision(RoadParametersDTO params, MethodCallback<Boolean> callback);

  /** @param divisionId */
  @DELETE
  @Path("/removeRoadDivision/{divisionId}")
  public void removeRoadDivision(
      @PathParam("divisionId") String divisionId, MethodCallback<Boolean> callback);

  @GET
  @Path("/roadSection")
  public void getRoadSections(MethodCallback<List<RoadSectionDTO>> callback);
}
