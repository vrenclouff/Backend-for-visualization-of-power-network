package cz.zcu.kiv.vps.idm.core;

import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas Cerny.
 */

@Singleton
public class LoggedUserStorageImpl implements LoggedUserStorage, Serializable {

    private static final Logger logger = LogManager.getLogger(LoggedUserStorageImpl.class);

    private Map<String, LoggedUser> storage = new HashMap<>();

    @Override
    public LoggedUser findByToken(String userToken) {
        if (StringUtils.isNotEmpty(userToken)) {
            return storage.get(userToken);
        }else {
            return null;
        }
    }

    @Override
    public LoggedUser saveAsLogged(String token, LoggedUser user) throws DuplicateKeyException {

        if (user == null) {
            throw new NullPointerException();
        }

        if (storage.containsKey(token)) {
            throw new DuplicateKeyException();
        }
        storage.put(token, user);
        return user;
    }

    @Override
    public LoggedUser deleteFromLogged(String token) {

        if (StringUtils.isNotEmpty(token)) {
            if (storage.containsKey(token)) {
                return storage.remove(token);
            }else {
                throw new NotFoundException();
            }
        }else {
            throw new NullPointerException();
        }
    }

    @Override
    public boolean isLogged(Long userID) {
        return storage.values().stream()
                .filter(e -> e.getUser().getId().equals(userID))
                .findFirst()
                .orElse(null) != null;
    }

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    private void cleanUsers() {
        logger.info("Cleaning users with expired token.");
        storage.entrySet().removeIf(e-> e.getValue().expiredToken());
    }
}
