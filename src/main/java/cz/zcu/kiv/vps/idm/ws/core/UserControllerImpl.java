package cz.zcu.kiv.vps.idm.ws.core;

import static cz.zcu.kiv.vps.idm.model.Role.ADMIN;

import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.idm.annotations.RequiresRoles;
import cz.zcu.kiv.vps.idm.exceptions.UserManagerException;
import cz.zcu.kiv.vps.idm.model.GraphModelDTO;
import cz.zcu.kiv.vps.idm.model.UserDTO;
import cz.zcu.kiv.vps.managers.api.UserManager;
import cz.zcu.kiv.vps.idm.ws.api.UserController;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lukas Cerny.
 */

public class UserControllerImpl implements UserController {

    @Inject
    private UserManager userManager;

    @Override @RequiresRoles(ADMIN)
    public Response insert(UserDTO user) {

        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        try {
            userManager.insert(user);

            resultStatus = Status.OK;
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getStringWithFormat("userController.insert.success", user.getUsername()));

        }catch (UserManagerException e) {
            resultStatus = Status.BAD_REQUEST;
            resultData.put("result", Result.ERROR);
            resultData.put("message", e.getMessage());
        }

        return Response.status(resultStatus).entity(resultData).build();
    }

    @Override @RequiresRoles(ADMIN)
    public Response delete(Long userID) {
        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        try {
            userManager.delete(userID);

            resultStatus = Status.OK;
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getStringWithFormat("userController.delete.success", userID.toString()));

        }catch (UserManagerException e) {
            resultStatus = Status.BAD_REQUEST;
            resultData.put("result", Result.ERROR);
            resultData.put("message", e.getMessage());
        }

        return Response.status(resultStatus).entity(resultData).build();
    }

    @Override @RequiresRoles(ADMIN)
    public Response update(UserDTO user) {
        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        try {
            userManager.update(user);

            resultStatus = Status.OK;
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getStringWithFormat("userController.update.success", user.getUsername()));

        }catch (UserManagerException e) {
            resultStatus = Status.CONFLICT;
            resultData.put("result", Result.ERROR);
            resultData.put("message", e.getMessage());
        }

        return Response.status(resultStatus).entity(resultData).build();
    }

    @Override @RequiresRoles(ADMIN)
    public Response info(Long userID) {
        Object resultData;
        Status resultStatus;

        try {
            resultData = userManager.info(userID);
            resultStatus = Status.OK;

        }catch (UserManagerException e) {
            resultStatus = Status.BAD_REQUEST;
            Map<String, Object> res = new HashMap<>();
            res.put("result", Result.ERROR);
            res.put("message", e.getMessage());
            resultData = res;
        }

        return Response.status(resultStatus).entity(resultData).build();
    }

    @Override @RequiresRoles(ADMIN)
    public Response all() {
        Object response = userManager.all();
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response current() {
        Object resultData;
        Status resultStatus;

        try {
            resultData = userManager.current();
            resultStatus = Status.OK;

        }catch (UserManagerException e) {
            resultStatus = Status.NO_CONTENT;
            Map<String, Object> res = new HashMap<>();
            res.put("result", Result.ERROR);
            res.put("message", e.getMessage());
            resultData = res;
        }

        return Response.status(resultStatus).entity(resultData).build();
    }

    @Override @RequiresRoles(ADMIN)
    public Response changeModelPermission(Long userID, List<GraphModelDTO> models) {
        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        try {
            userManager.changeModelPermission(userID, models);

            resultStatus = Status.OK;
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getString("userController.changeModelPermission.success"));

        }catch (UserManagerException e) {
            resultStatus = Status.CONFLICT;
            resultData.put("result", Result.ERROR);
            resultData.put("message", e.getMessage());
        }

        return Response.status(resultStatus).entity(resultData).build();
    }
}
