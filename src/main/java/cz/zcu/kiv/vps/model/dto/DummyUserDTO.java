package cz.zcu.kiv.vps.model.dto;

import cz.zcu.kiv.vps.idm.model.Permission;

/**
 * Created by Lukas Cerny.
 */

public class DummyUserDTO {

    private Long userID;

    private Permission[] permissions;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }
}