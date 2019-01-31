package cz.zcu.kiv.vps.idm.core;

import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.DuplicateKeyException;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class LoggedUserStorageImplTest {

    private User user;

    private final String token = "#123456789";
    private final String userName = "admin";
    private final Long userID = 10L;

    @Before
    public void setUp() {
        user = new User();
        user.setId(userID);
        user.setUsername(userName);
    }

    @Test(expected = NullPointerException.class)
    public void saveAsLoggedNullUser() throws Exception {
        LoggedUserStorage loggedUserStorage = new LoggedUserStorageImpl();
        loggedUserStorage.saveAsLogged(token, null);
    }

    @Test(expected = DuplicateKeyException.class)
    public void saveAsLoggedDuplicateKey() throws Exception {
        LoggedUserStorage loggedUserStorage = new LoggedUserStorageImpl();
        loggedUserStorage.saveAsLogged(token, new LoggedUser(user, null, null));
        loggedUserStorage.saveAsLogged(token, new LoggedUser(user, null, null));
    }

    @Test
    public void isLogged() throws Exception {
        LoggedUserStorage loggedUserStorage = new LoggedUserStorageImpl();
        loggedUserStorage.saveAsLogged(token, new LoggedUser(user, null, null));
        assertTrue(loggedUserStorage.isLogged(userID));
    }


    @Test
    public void findByToken() throws Exception {
        LoggedUserStorage loggedUserStorage = new LoggedUserStorageImpl();
        loggedUserStorage.saveAsLogged(token, new LoggedUser(user, null, null));
        LoggedUser loggedUser = loggedUserStorage.findByToken(token);
        assertEquals(userName, loggedUser.getUser().getUsername());
    }

    @Test
    public void deleteFromLogged() throws Exception {
        LoggedUserStorage loggedUserStorage = new LoggedUserStorageImpl();
        loggedUserStorage.saveAsLogged(token, new LoggedUser(user, null, null));
        loggedUserStorage.deleteFromLogged(token);
        assertNull(loggedUserStorage.findByToken(token));
    }
}