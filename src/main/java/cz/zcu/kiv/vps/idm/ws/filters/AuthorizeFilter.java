package cz.zcu.kiv.vps.idm.ws.filters;


import cz.zcu.kiv.vps.core.config.ApplicationConfig;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.ws.api.AuthController;
import cz.zcu.kiv.vps.idm.annotations.AuthorizationPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Lukas Cerny.
 *
 * Filter which routes authorized and unauthorized requests.
 */

@WebFilter(ApplicationConfig.START_OF_APPLICATION_PATH+"/*")
public class AuthorizeFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthorizeFilter.class);

    private String authPath;

    private String authTokenName;

    @Inject
    private LoggedUserStorage loggedUserStorage;

    @Inject
    private ApplicationConfig config;

    /**
     * Filter initialization. Setting path for unauthorized request and header name for authorized requests.
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.authPath = AuthController.class.getAnnotation(AuthorizationPath.class).value();
        this.authTokenName = config.getAuthorizationHeaderName();
    }

    /**
     * Method which is always invoked by request.
     * Method routes authorized and unauthorized requests
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

        String servletPath = httpRequest.getServletPath();

        if (!StringUtils.startsWith(servletPath, authPath)) {
            String authToken = httpRequest.getHeader(authTokenName);
            httpResponse.setHeader(authTokenName, authToken);
            LoggedUser loggedUser = loggedUserStorage.findByToken(authToken);
            if (loggedUser != null) {
                if (!loggedUser.expiredToken()) {
                    loggedUser.extendToken();
                }else {
                    logger.info("Unauthorized: Your existing session token doesn't authorize you any more.");
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            Messages.getString("authorizeFilter.tokenExpired"));
                }
            }else {
                logger.info("Unauthorized: Access is denied due to invalid credentials.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        Messages.getString("authorizeFilter.unauthorized"));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}