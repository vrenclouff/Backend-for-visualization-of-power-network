package cz.zcu.kiv.vps.idm.utils;

import cz.zcu.kiv.vps.core.config.ApplicationConfig;
import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.idm.model.LoggedUser;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by Lukas Cerny.
 */

public class Resources {

    @Inject
    private LoggedUserStorage loggedUserStorage;

    @Context
    private HttpServletRequest request;

    @Inject
    private ApplicationConfig config;


    /**
     * Method produces logged users.
     * From header is loaded user's authorize token and user is found in storage.
     * @param ip
     * @return logged user
     */
    @Produces
    public LoggedUser loggedUser(InjectionPoint ip) {
        try {
            String token = request.getHeader(config.getAuthorizationHeaderName());
            return loggedUserStorage.findByToken(token);
        }catch (IllegalStateException e){
            return null;
        }
    }
}
