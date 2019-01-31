package cz.zcu.kiv.vps.model.repositories.api;

import cz.zcu.kiv.vps.idm.model.User;

/**
 * Created by Lukas Cerny.
 */

public interface UserRepository extends AbstractRepository {

    /**
     * Method finds user by username and hash.
     * @param username
     * @param hash
     * @return
     */
    User findByUsernameAndHash(final String username, final String hash);

    /**
     * Method finds user by username.
     * @param username
     * @return
     */
    User findByUsername(final String username);

}
