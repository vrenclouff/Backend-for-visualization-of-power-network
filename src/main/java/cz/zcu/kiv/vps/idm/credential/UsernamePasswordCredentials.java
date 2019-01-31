package cz.zcu.kiv.vps.idm.credential;

import cz.zcu.kiv.vps.idm.utils.SecurityUtils;

/**
 * Created by Lukas Cerny.
 *
 * Container which contains information for login or logout.
 */
public class UsernamePasswordCredentials {

    private final String id;

    private final String username;

    private final String hash;

    private String principal;

    public UsernamePasswordCredentials(String principal) {
        this.id = null;
        this.username = null;
        this.principal = principal;
        this.hash = null;
    }

    public UsernamePasswordCredentials(String id, String baseHash) {
        String [] credentials = SecurityUtils.decodeBase64Hash(baseHash);
        this.username = credentials[0];
        this.hash = credentials[1];
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getId() {
        return id;
    }

}
