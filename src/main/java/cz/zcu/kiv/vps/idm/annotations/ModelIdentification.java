package cz.zcu.kiv.vps.idm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Lukas Cerny.
 *
 * Annotation defines identification of model
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ModelIdentification {}
