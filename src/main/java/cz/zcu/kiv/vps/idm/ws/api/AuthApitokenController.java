package cz.zcu.kiv.vps.idm.ws.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Lukas Cerny.
 */

@Path("auth/apitoken")
@Produces(MediaType.APPLICATION_JSON)
public interface AuthApitokenController extends AuthController {

    /**
     * Method listens on path for login user.
     * @return response with information about result
     */
    @POST @Path("login")
    Response login();

    /**
     * Method listens on path for log out user.
     * @return response with information about result
     */
    @POST @Path("logout")
    Response logout();
}
