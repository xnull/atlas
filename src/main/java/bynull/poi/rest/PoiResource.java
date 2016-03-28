package bynull.poi.rest;

import bynull.poi.dto.PoiDto;
import bynull.poi.rest.messages.OkMessage;
import bynull.poi.rest.messages.PoiQueryMessage;
import bynull.poi.service.PoiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static bynull.poi.rest.PoiResource.POI_ENDPOINT;

@Component
@Path(POI_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/poi", tags = {"poi"}, consumes = "application/json", produces = "application/json")
public class PoiResource {
    public static final String POI_ENDPOINT = "poi";
    public static final String SAVE_POI_ENDPOINT = "/add";
    public static final String FIND_ENDPOINT = "/find";

    @Autowired
    private PoiService poiService;

    @POST
    @Path(SAVE_POI_ENDPOINT)
    @ApiOperation(
            value = "Add a new POI to the store",
            notes = "You can save and share your POI",
            response = OkMessage.class
    )
    @ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input")})
    public Response add(PoiDto poi) {
        poiService.add(poi);
        return Response
                .ok()
                .entity(new OkMessage("A POI has added to the store"))
                .build();
    }

    @POST
    @Path(FIND_ENDPOINT)
    @ApiOperation(
            value = "Find POIs nearby some location",
            notes = "You can looking for POIs nearby you",
            response = PoiDto.class
    )
    @ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input")})
    public Response find(PoiQueryMessage query) {
        return Response
                .ok()
                .entity(poiService.find(query))
                .build();
    }
}
