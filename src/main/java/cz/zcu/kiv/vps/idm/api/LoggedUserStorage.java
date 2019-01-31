package cz.zcu.kiv.vps.idm.api;

import cz.zcu.kiv.vps.idm.model.LoggedUser;

import javax.ejb.DuplicateKeyException;

/**
 * Created by Lukas Cerny.
 */

public interface LoggedUserStorage {

    /**
     * Method finds logged user by token.
     * @param userToken user's token
     * @return logged user
     */
    LoggedUser findByToken(String userToken);

    /**
     * Method saves user to store with logged users by token.
     * @param token key to store with logged users
     * @param user logged user
     * @return logged user
     * @throws DuplicateKeyException
     */
    LoggedUser saveAsLogged(String token, LoggedUser user) throws DuplicateKeyException;

    /**
     * Method deletes user from store with logged user.
     * @param token key to store with logged users
     * @return deleted user
     */
    LoggedUser deleteFromLogged(String token);

    /**
     * Method tests user if is logged.
     * @param userID identification of user
     * @return boolean value if user is logged
     */
    boolean isLogged(Long userID);
}
