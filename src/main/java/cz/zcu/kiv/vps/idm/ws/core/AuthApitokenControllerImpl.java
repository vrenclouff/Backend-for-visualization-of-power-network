package cz.zcu.kiv.vps.idm.ws.core;

import cz.zcu.kiv.vps.core.config.ApplicationConfig;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.idm.api.SecurityManager;
import cz.zcu.kiv.vps.idm.credential.UsernamePasswordCredentials;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.utils.SecurityUtils;
import cz.zcu.kiv.vps.idm.ws.api.AuthApitokenController;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.*;


/**
 * Created by Lukas Cerny.
 */

public class AuthApitokenControllerImpl implements AuthApitokenController {

    private static final Logger logger = LogManager.getLogger(AuthApitokenControllerImpl.class);

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ApplicationConfig config;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;


    @Override
    public Response login() {

        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        String cookieName = config.getAuthorizationCookiesName();
        String baseHash = request.getHeader(config.getAuthenticationHeaderName());
        Cookie [] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null && cookies.length > 0) {
            cookie = Arrays.stream(request.getCookies()).filter(
                    e -> e.getName().equals(cookieName)).findFirst().orElse(null);
        }

        if (cookie == null) {
            cookie = new Cookie(cookieName, SecurityUtils.secureHash());
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        UsernamePasswordCredentials credentials = null;
        String id = cookie != null ? cookie.getValue() : StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(baseHash)) {
            credentials = new UsernamePasswordCredentials(id, baseHash);
        }

        try {
            LoggedUser loggedUser = securityManager.login(credentials);

            logger.debug("Configuring request and response for new user.");
            response.setHeader(config.getAuthorizationHeaderName(), credentials.getPrincipal());

            logger.debug("Preparing response data.");
            resultStatus = Status.OK;
            resultData.put("auth-token", credentials.getPrincipal());
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getStringWithFormat("authApitokenController.login.success", loggedUser.getUser().getUsername()));

            logger.info("User '"+loggedUser.getUser().getUsername()+"' has been logged.");

        }catch (AuthenticationException e) {

            logger.info(e.getMessage());
            logger.debug("Preparing response data.");
            resultStatus = Status.UNAUTHORIZED;
            resultData.put("code", resultStatus.getStatusCode());
            resultData.put("message", e.getMessage());
            resultData.put("result", Result.ERROR);
        }

        return Response.status(resultStatus).entity(resultData).build();
    }


    @Override
    public Response logout() {

        Map<String, Object> resultData = new HashMap<>();
        Status resultStatus;

        String userToken = request.getHeader(config.getAuthorizationHeaderName());
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userToken);

        try {
            LoggedUser loggedUser = securityManager.logout(credentials);

            logger.debug("Preparing response data.");
            resultStatus = Status.OK;
            resultData.put("result", Result.SUCCESS);
            resultData.put("message", Messages.getStringWithFormat("authApitokenController.logout.success", loggedUser.getUser().getUsername()));

            logger.info("User '"+loggedUser.getUser().getUsername()+"' has been logged out.");

        }catch (AuthenticationException e) {

            logger.info(e.getMessage());
            logger.debug("Preparing response data.");
            resultStatus = Status.BAD_REQUEST;
            resultData.put("result", Result.ERROR);
            resultData.put("message", e.getMessage());
        }

        return Response.status(resultStatus).entity(resultData).build();
    }
}