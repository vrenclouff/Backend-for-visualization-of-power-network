package cz.zcu.kiv.vps.idm.annotations;

import cz.zcu.kiv.vps.idm.model.Role;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Lukas Cerny.
 *
 * Annotation for defines roles for access to invoke method.
 */

@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({ METHOD, TYPE })
public @interface RequiresRoles {
    @Nonbinding Role[] value() default {};
}
