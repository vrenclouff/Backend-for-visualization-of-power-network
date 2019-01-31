package cz.zcu.kiv.vps.model.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Lukas Cerny.
 */

@Embeddable
public class DummyUser {

    @NotNull
    private Long userID;

    private Integer permissions = 0;

    public DummyUser(){}

    public DummyUser(Long userID, Integer permissions) {
        this.userID = userID;
        this.permissions = permissions;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }
}