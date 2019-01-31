package cz.zcu.kiv.vps.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by Lukas Cerny.
 */
public class ValidatorUtils {

    private static final Logger logger = LogManager.getLogger(ValidatorUtils.class);


    /**
     * Method validations object using Bean Validation
     * @param object object will be validated
     * @return boolean value represents result
     */
    public static boolean isValid(Object object) {

        if (object != null) {

            logger.debug("Validation object '" + object.getClass().getName() + "'.");
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validate(object);

            if (violations.isEmpty()) {
                return true;
            }else {
                StringBuilder msg = new StringBuilder();
                violations.forEach(e -> msg.append(e.getPropertyPath() + " : " + e.getMessage()));
                logger.error("Object '" + object.getClass().getName() + "' is not valid.");
                logger.error(msg);
            }

        }else {
            logger.debug("Null object can not be valid.");
        }

        return false;
    }
}
