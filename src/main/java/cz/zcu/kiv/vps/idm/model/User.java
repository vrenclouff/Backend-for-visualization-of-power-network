package cz.zcu.kiv.vps.idm.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas Cerny.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "User.findByUsernameAndHash", query = "SELECT u FROM User u WHERE u.username = :username AND u.hash = :hash")
})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String hash;

    @NotNull
    private Boolean allowed;

    @NotNull
    private String email;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String firstName;

    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GraphModel> models = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<GraphModel> getModels() {
        return models;
    }

    public void setModels(List<GraphModel> models) {
        this.models = models;
    }
}
