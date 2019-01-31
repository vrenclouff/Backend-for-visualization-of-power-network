package cz.zcu.kiv.vps.ws.api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lukas Cerny.
 */

@Path("model")
@Produces(MediaType.APPLICATION_JSON)
public interface ModelController {

    /**
     * Web service for a simple high level graph data
     *
     * @param modelID id of a desired model
     * @param voltageLevels List of deired voltage levels
     * @return Simple high level graph data
     */
    @GET @Path("/{modelID}/highLevel/simple")
    Response highLevelSimpleGraph(@PathParam("modelID") final Long modelID, @QueryParam("voltageLevel") final List<Integer> voltageLevels);

    /**
     * Web service for a simple low level graph data
     *
     * @param modelID id of a desired model
     * @param voltageLevels List of deired voltage levels
     * @return Simple low level graph data
     */
    @GET @Path("/{modelID}/lowLevel/simple")
    Response lowLevelSimpleGraph(@PathParam("modelID") final Long modelID, @QueryParam("voltageLevel") final List<Integer> voltageLevels);

    /**
     * Web service for a detailed high level graph data
     *
     * @param modelID id of a desired model
     * @param voltageLevels List of deired voltage levels
     * @return Detailed high level graph data
     */
    @GET @Path("/{modelID}/highLevel/detail")
    Response highLevelDetailGraph(@PathParam("modelID") final Long modelID, @QueryParam("voltageLevel") final List<Integer> voltageLevels);

    /**
     * Web service for a detailed low level graph data
     *
     * @param modelID id of a desired model
     * @param voltageLevels List of deired voltage levels
     * @return Detailed low level graph data
     */
    @GET @Path("/{modelID}/lowLevel/detail")
    Response lowLevelDetailGraph(@PathParam("modelID") final Long modelID, @QueryParam("voltageLevel") final List<Integer> voltageLevels);

    /**
     * Gets a List of all Models
     *
     * @return List of all Models
     */
    @GET @Path("all")
    Response all();
}
