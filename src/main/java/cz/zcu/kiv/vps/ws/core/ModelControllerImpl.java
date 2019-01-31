package cz.zcu.kiv.vps.ws.core;

import cz.zcu.kiv.vps.idm.annotations.RequiresPermissions;
import cz.zcu.kiv.vps.managers.api.ModelManager;
import cz.zcu.kiv.vps.idm.annotations.ModelIdentification;
import cz.zcu.kiv.vps.ws.api.ModelController;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

import static cz.zcu.kiv.vps.idm.model.Permission.READ;


/**
 * Created by Lukas Cerny.
 */

public class ModelControllerImpl implements ModelController {

    @Inject
    private ModelManager modelManager;


    @Override @RequiresPermissions(READ)
    public Response highLevelSimpleGraph(@ModelIdentification Long modelID, List<Integer> voltageLevels) {
        Object result = modelManager.getHighLevelSimpleGraph(modelID, voltageLevels);
        return Response.status(Status.OK).entity(result).build();
    }

    @Override @RequiresPermissions(READ)
    public Response lowLevelSimpleGraph(@ModelIdentification Long modelID, List<Integer> voltageLevels) {
        Object result = modelManager.getLowLevelSimpleGraph(modelID, voltageLevels);
        return Response.status(Status.OK).entity(result).build();
    }

    @Override @RequiresPermissions(READ)
    public Response highLevelDetailGraph(@ModelIdentification Long modelID, List<Integer> voltageLevels) {
        Object result = modelManager.getHighLevelDetailGraph(modelID, voltageLevels);
        return Response.status(Status.OK).entity(result).build();
    }

    @Override @RequiresPermissions(READ)
    public Response lowLevelDetailGraph(@ModelIdentification Long modelID, List<Integer> voltageLevels) {
        Object result = modelManager.getLowLevelDetailGraph(modelID, voltageLevels);
        return Response.status(Status.OK).entity(result).build();
    }

    @Override
    public Response all() {
        Object result = modelManager.getAll();
        return Response.status(Status.OK).entity(result).build();
    }
}
