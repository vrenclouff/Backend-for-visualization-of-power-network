package cz.zcu.kiv.vps.core.config;

import cz.zcu.kiv.vps.model.repositories.api.UserRepository;
import cz.zcu.kiv.vps.core.exceptions.RepositoryException;
import cz.zcu.kiv.vps.idm.model.Role;
import cz.zcu.kiv.vps.idm.model.User;
import cz.zcu.kiv.vps.idm.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Lukas Cerny.
 */

@Startup @Singleton
public class ApplicationConfig {

    private static final Logger logger = LogManager.getLogger(ApplicationConfig.class);

    public static final String START_OF_APPLICATION_PATH = "/api";

    private final ResourceBundle configuration = ResourceBundle.getBundle("config");

    @Inject
    private UserRepository userRepository;

    /**
     * Method initializations settings.
     */
    @PostConstruct
    public void init() {
        logger.info("Initialization application. Setting default configuration from 'config.properties' file.");

        createDefaultUser();
        createDefaultLocale();
    }


    /**
     * Method sets up default locale.
     */
    private void createDefaultLocale() {

        String language = getDefaultLocaleLanguage();
        String country = getDefaultLocaleCountry();

        if (StringUtils.isNotEmpty(language) && StringUtils.isNotEmpty(country)) {
            Locale.setDefault(new Locale(language, country));
        }else {
            Locale.setDefault(Locale.US);
        }

        logger.info("Setting default locale to '"+Locale.getDefault()+"'.");
    }

    /**
     * Method sets up default user.
     */
    private void createDefaultUser() {

        String username = getDefaultUserUsername();
        String password = getDefaultUserPassword();

        User defaultUser = userRepository.findByUsername(username);
        String defaultUserHash = SecurityUtils.createSHA256Hash(password);

        if (defaultUser == null) {

            logger.info("Creating default user with username '"+username+"'.");

            User user = new User();
            user.setUsername(username);
            user.setHash(defaultUserHash);
            user.setAllowed(true);
            user.setEmail(StringUtils.EMPTY);
            user.setFirstName(StringUtils.EMPTY);
            user.setLastName(StringUtils.EMPTY);
            user.setRole(Role.ADMIN);

            try {
                userRepository.create(user);
            } catch (RepositoryException e) {
                logger.error(e);
            }
        }else if (!defaultUser.getHash().equals(defaultUserHash)) {
            defaultUser.setHash(defaultUserHash);
            try {
                userRepository.update(defaultUser);
            } catch (RepositoryException e) {
                logger.error(e);
            }
            logger.info("Password for default user has been changed to default.");
        }else {
            logger.info("Exists default user with username '"+username+"'.");
        }
    }

    public String getDefaultUserUsername() {
        return configuration.getString("default.user.username");
    }

    public String getDefaultUserPassword() {
        return configuration.getString("default.user.password");
    }

    public String getAuthorizationHeaderName() {
        return configuration.getString("authorization.header.name");
    }

    public String getAuthenticationHeaderName() {
        return configuration.getString("authentication.header.name");
    }

    public String getAuthorizationCookiesName() {
        return configuration.getString("authorization.cookie.name");
    }

    public String getDefaultLocaleLanguage() {
        return configuration.getString("default.locale.language");
    }

    public String getDefaultLocaleCountry() {
        return configuration.getString("default.locale.country");
    }
}
