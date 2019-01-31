package cz.zcu.kiv.vps.model.repositories.core;

import static cz.zcu.kiv.vps.core.database.DatabaseType.USERS;

import cz.zcu.kiv.vps.core.database.Database;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.model.repositories.api.UserRepository;
import cz.zcu.kiv.vps.core.exceptions.RepositoryException;
import cz.zcu.kiv.vps.idm.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * Created by Lukas Cerny.
 */

@Stateless @Database(USERS)
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    @Override
    public void create(Object object) throws RepositoryException {

        if (object == null) {
            throw new NullPointerException(Messages.getString("userRepository.create.null"));
        }

        if (!object.getClass().equals(User.class)) {
            throw new RepositoryException(Messages.getString("userRepository.create.unknownClass"));
        }

        User user = findByUsername(((User)object).getUsername());
        if (user == null) {
            super.create(object);
        }else {
            throw new RepositoryException(Messages.getString("userRepository.create.duplicate"));
        }
    }

    @Override
    public User findByUsernameAndHash(String username, String hash) {
        logger.debug("Finding user by username '"+username+"' and hash.");
        try {
            return entityManager.createNamedQuery("User.findByUsernameAndHash", User.class).setParameter("username", username).setParameter("hash", hash).getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        logger.debug("Finding user by username '"+username+"'.");
        try {
            return entityManager.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

}
