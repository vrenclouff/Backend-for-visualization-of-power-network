package cz.zcu.kiv.vps.idm.interceptors;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import cz.zcu.kiv.vps.idm.annotations.RequiresPermissions;
import cz.zcu.kiv.vps.idm.model.GraphModel;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.model.Permission;
import cz.zcu.kiv.vps.idm.model.Role;
import cz.zcu.kiv.vps.idm.utils.PermissionUtils;
import cz.zcu.kiv.vps.managers.api.UserManager;
import cz.zcu.kiv.vps.idm.annotations.ModelIdentification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Lukas Cerny.
 *
 * Class represents interceptor which validates if user has permissions for invoke method annotated @RequiresPermissions.
 */

@Interceptor @RequiresPermissions
public class RequiresPermissionsInterceptor {

    private static final Logger logger = LogManager.getLogger(RequiresPermissionsInterceptor.class);

    @Inject
    private LoggedUser loggedUser;

    @Inject
    private UserManager userManager;

    /**
     * Method which is invoked when is called method with annotation @RequiresPermissions.
     * Method validates if user has permissions for invoke method.
     * @param context
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        logger.info("Control user's permissions for method '" + context.getTarget().getClass().getName() + "." + context.getMethod().getName()+"'.");

        Long modelID = getModelIdentifierFromParameters(context.getParameters(), context.getMethod().getParameterAnnotations());

        if (loggedUser == null || loggedUser.getUser() == null) {
            return Response.status(UNAUTHORIZED).build();
        }

        if (loggedUser.getUser().getRole().equals(Role.ADMIN)) {
            return context.proceed();
        }

        Integer integerPermissions = loggedUser.getUser().getModels().stream().filter(e -> e.getModelID().equals(modelID))
                .findFirst().orElse(new GraphModel()).getPermissions();

        List<Permission> userPermissionsList = PermissionUtils.convertToList(integerPermissions);
        Permission[] permissions = context.getMethod().getAnnotation(RequiresPermissions.class).value();

        boolean allowed = true;
        for (Permission permission : permissions) {
            allowed &= userPermissionsList.contains(permission);
        }

        if (allowed) {
            return context.proceed();
        }else {
            return Response.status(FORBIDDEN).build();
        }
    }

    /**
     * Method finds identification of model from parameters of method.
     * @param parameters
     * @param parameterAnnotations
     * @return identification of model
     */
    private Long getModelIdentifierFromParameters(Object[] parameters, Annotation[][] parameterAnnotations) {
        Long modelID = null;
        for(Annotation[] annotations : parameterAnnotations){
            for(int i = 0; i < annotations.length; i++ ){
                if (annotations[i] instanceof ModelIdentification) {
                    modelID = (Long) parameters[i];
                }
            }
        }

        if (modelID != null) {
            return modelID;
        }else {
            throw new RuntimeException("Not find @ModelIdentification annotation in method parameters.");
        }
    }
}
