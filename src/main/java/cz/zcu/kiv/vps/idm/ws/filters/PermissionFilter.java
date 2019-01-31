package cz.zcu.kiv.vps.idm.ws.filters;


import cz.zcu.kiv.vps.core.config.ApplicationConfig;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.managers.api.UserManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lukas Cerny.
 */

@WebFilter(ApplicationConfig.START_OF_APPLICATION_PATH+"/model/*")
public class PermissionFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(PermissionFilter.class);

    private static final Pattern PERMISSION_REGEX = Pattern.compile("^(\\"+ApplicationConfig.START_OF_APPLICATION_PATH+"\\/model\\/)(all|[0-9]*)");

    private String authTokenName;

    @Inject
    private LoggedUserStorage loggedUserStorage;

    @Inject
    private ApplicationConfig config;

    @Inject
    private UserManager userManager;


    /**
     * Filter initialization. Setting header name for authorized requests.
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.authTokenName = config.getAuthorizationHeaderName();
    }

    /**
     * Method which is invoked when someone run request for access to model.
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String url = httpRequest.getRequestURI();
        Matcher m = PERMISSION_REGEX.matcher(url);

        if (m.find() && m.groupCount() == 2) {

            String req = m.group(2);

            if (NumberUtils.isDigits(req)) {
                String authToken = httpRequest.getHeader(authTokenName);
                httpResponse.setHeader(authTokenName, authToken);
                LoggedUser loggedUser = loggedUserStorage.findByToken(authToken);
                if (loggedUser != null && !userManager.isUserAllowedForModel(loggedUser.getUser().getId(), Long.valueOf(req))) {
                    logger.info("Forbidden. You don't have permission to access this model.");
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                            Messages.getString("permissionFilter.forbidden"));
                }
            }else if (!req.equals("all")) {
                logger.info("Bad request. The request does not contain ID of model.");
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        Messages.getString("permissionFilter.invalidRequest"));
            }
        }else {
            logger.info("Bad request. The request does not contain ID of model.");
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    Messages.getString("permissionFilter.badRequest"));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}