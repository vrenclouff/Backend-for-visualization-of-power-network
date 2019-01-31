package cz.zcu.kiv.vps.idm.annotations;

import cz.zcu.kiv.vps.idm.model.Permission;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by Lukas Cerny.
 *
 * Annotation for defines permissions for access to invoke method.
 */

@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({ METHOD, TYPE })
public @interface RequiresPermissions {
    @Nonbinding Permission [] value() default {};
}
