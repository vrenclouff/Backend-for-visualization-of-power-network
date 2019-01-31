package cz.zcu.kiv.vps.idm.model;


/**
 * Created by Lukas Cerny.
 */

public class GraphModelDTO {

    private Long modelID;

    private String name;

    private Permission[] permissions;

    public Long getModelID() {
        return modelID;
    }

    public void setModelID(Long modelID) {
        this.modelID = modelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }
}
