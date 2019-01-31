package cz.zcu.kiv.vps.idm.ws.api;

import cz.zcu.kiv.vps.idm.model.GraphModelDTO;
import cz.zcu.kiv.vps.idm.model.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lukas Cerny.
 */

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
public interface UserController {

    /**
     * Method which listens on path for insert user.
     * Creates response with with data.
     * @param user - information about user
     * @return response with information about result
     */
    @POST @Path("insert")
    Response insert(UserDTO user);

    /**
     * Method which listens on path for delete user.
     * Creates response with with data.
     * @param userID - identification of user
     * @return response with information about result
     */
    @GET @Path("delete")
    Response delete(@QueryParam("userID") Long userID);

    /**
     * Method wich listens on path for update user.
     * @param user - object which represents user
     * @return response with information about result
     */
    @POST @Path("update")
    Response update(UserDTO user);

    /**
     * Method which listens on path for get info about user.
     * @param userID - identification of user
     * @return response with user's information
     */
    @GET @Path("info")
    Response info(@QueryParam("userID") Long userID);

    /**
     * Method which listens on path for get info about current logged user.
     * @return response with user's information
     */
    @GET @Path("current")
    Response current();

    /**
     * Method which listens on path for get all users.
     * @return response with list of users
     */
    @GET @Path("all")
    Response all();

    /**
     * Method which listens on path for change permissions for model.
     * @param userID - identification of user, who will be changed permissions
     * @param models - list of changes
     * @return response with information about result
     */
    @POST @Path("changeModelPermission")
    Response changeModelPermission(@QueryParam("userID") Long userID, List<GraphModelDTO> models);
}
