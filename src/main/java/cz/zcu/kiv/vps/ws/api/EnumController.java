package cz.zcu.kiv.vps.ws.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Lukas Cerny.
 */

@Path("enum")
@Produces(MediaType.APPLICATION_JSON)
public interface EnumController {

    /**
     * Method listens on path for get all enums for graph model
     * @return response with list of enums
     */
    @GET @Path("model")
    Response model();

    /**
     * Method listens on path for get all enums for user
     * @return response with list of enums
     */
    @GET @Path("user")
    Response user();
}
