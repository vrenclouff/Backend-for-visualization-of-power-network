package cz.zcu.kiv.vps.ws.core;


import cz.zcu.kiv.vps.managers.api.EnumManager;
import cz.zcu.kiv.vps.ws.api.EnumController;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Created by Lukas Cerny.
 */


public class EnumControllerImpl implements EnumController {

    @Inject
    private EnumManager enumManager;

    @Override
    public Response model() {
        Object result = enumManager.getModel();
        return Response.status(Status.OK).entity(result).build();
    }

    @Override
    public Response user() {
        Object result = enumManager.getUser();
        return Response.status(Status.OK).entity(result).build();
    }
}
