package cz.zcu.kiv.vps.idm.ws.api;

import cz.zcu.kiv.vps.core.config.ApplicationConfig;
import cz.zcu.kiv.vps.idm.annotations.AuthorizationPath;

/**
 * Created by Lukas Cerny.
 */

@AuthorizationPath(ApplicationConfig.START_OF_APPLICATION_PATH+"/auth")
public interface AuthController {

}
