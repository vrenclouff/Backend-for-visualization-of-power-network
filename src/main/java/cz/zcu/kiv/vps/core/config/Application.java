package cz.zcu.kiv.vps.core.config;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Lukas Cerny.
 *
 * Class which set up start of path for RESTFull API
 */

@ApplicationPath(ApplicationConfig.START_OF_APPLICATION_PATH)
public class Application extends javax.ws.rs.core.Application {
}
