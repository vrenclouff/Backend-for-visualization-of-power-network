package cz.zcu.kiv.vps.idm.core;

import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.idm.api.SecurityManager;
import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.model.repositories.api.UserRepository;
import cz.zcu.kiv.vps.idm.credential.UsernamePasswordCredentials;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.model.Session;
import cz.zcu.kiv.vps.idm.model.User;
import cz.zcu.kiv.vps.idm.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.DuplicateKeyException;
import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.ws.rs.NotFoundException;
import java.util.Date;

/**
 * Created by Lukas Cerny.
 */
public class SecurityManagerImpl implements SecurityManager {

    private static final Logger logger = LogManager.getLogger(SecurityManagerImpl.class);

    @Inject
    private LoggedUserStorage loggedUserStorage;

    @Inject
    private UserRepository userRepository;

    @Override
    public LoggedUser login(UsernamePasswordCredentials credentials) throws AuthenticationException {


        if (credentials == null) {
            throw new AuthenticationException(Messages.getString("securityManager.login.credentials.missing"));
        }

        User user = userRepository.findByUsernameAndHash(credentials.getUsername(), credentials.getHash());

        if (user == null) {
            throw new AuthenticationException(Messages.getString("securityManager.login.credentials.bad"));
        }

        if (!user.getAllowed().booleanValue()) {
            throw new AuthenticationException(Messages.getString("securityManager.login.user.disabled"));
        }

        String authToken = SecurityUtils.createAuthToken(user.getUsername(), credentials.getId());
        credentials.setPrincipal(authToken);
        LoggedUser loggedUser = new LoggedUser(user, new Session(), new Date());
        loggedUser.extendToken();

        try {
            return loggedUserStorage.saveAsLogged(authToken, loggedUser);
        }catch (DuplicateKeyException e) {
            throw new AuthenticationException(Messages.getString("securityManager.login.user.logged"));
        }
    }

    @Override
    public LoggedUser logout(UsernamePasswordCredentials credentials) throws AuthenticationException {

        String userToken = credentials.getPrincipal();

        if (StringUtils.isEmpty(userToken)) {
            throw new AuthenticationException(Messages.getString("securityManager.logout.user.credentials"));
        }

        try {
            return loggedUserStorage.deleteFromLogged(userToken);
        }catch (NullPointerException | NotFoundException e) {
            throw new AuthenticationException(Messages.getString("securityManager.logout.user.notFound"));
        }
    }
}
