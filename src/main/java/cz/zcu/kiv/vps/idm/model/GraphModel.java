package cz.zcu.kiv.vps.idm.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Lukas Cerny.
 */

@Embeddable
public class GraphModel {

    @NotNull
    private Long modelID;

    @NotNull
    private String name;

    private Integer permissions = 0;

    public GraphModel(){}

    public GraphModel(Long modelID, String name, Integer permissions) {
        this.modelID = modelID;
        this.name = name;
        this.permissions = permissions;
    }

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

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }
}
