package cz.zcu.kiv.vps.idm.api;

import cz.zcu.kiv.vps.idm.credential.UsernamePasswordCredentials;
import cz.zcu.kiv.vps.idm.model.LoggedUser;

import javax.naming.AuthenticationException;

/**
 * Created by Lukas Cerny.
 */
public interface SecurityManager {

    /**
     * Method login users.
     * @param credentials
     * @return logged user
     * @throws AuthenticationException
     */
    LoggedUser login(UsernamePasswordCredentials credentials) throws AuthenticationException;

    /**
     * Method log out users.
     * @param credentials
     * @return logged out user
     * @throws AuthenticationException
     */
    LoggedUser logout(UsernamePasswordCredentials credentials) throws AuthenticationException;
}
