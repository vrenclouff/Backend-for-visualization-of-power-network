package cz.zcu.kiv.vps.idm.interceptors;

import cz.zcu.kiv.vps.idm.annotations.RequiresRoles;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import java.util.Arrays;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * Created by Lukas Cerny.
 *
 * Class represents interceptor which validates if user has role for invoke method annotated @RequiresRoles.
 */

@Interceptor @RequiresRoles
public class RequiresRolesInterceptor {

    private static final Logger logger = LogManager.getLogger(RequiresRolesInterceptor.class);

    @Inject
    private LoggedUser loggedUser;

    /**
     * Method which is invoked when is called method with annotation @RequiresRoles.
     * Method validates if user has role for invoke method.
     * @param context
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        logger.info("Control user's role for method '" + context.getTarget().getClass().getName() + "." + context.getMethod().getName()+"'.");
        Role[] roles = context.getMethod().getAnnotation(RequiresRoles.class).value();

        if (loggedUser == null || loggedUser.getUser() == null) {
            return Response.status(UNAUTHORIZED).build();
        }

        if (Arrays.asList(roles).contains(loggedUser.getUser().getRole())) {
            return context.proceed();
        }else {
            return Response.status(FORBIDDEN).build();
        }
    }
}
