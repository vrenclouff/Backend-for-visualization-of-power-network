package cz.zcu.kiv.vps.idm.model;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lukas Cerny.
 */
public class UserDTO {

    private Long id;

    private String username;

    private String hash;

    private String firstName;

    private String lastName;

    private Boolean allowed;

    private String email;

    private Role role;

    private List<GraphModelDTO> models = new LinkedList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<GraphModelDTO> getModels() {
        return models;
    }

    public void setModels(List<GraphModelDTO> models) {
        this.models = models;
    }
}
