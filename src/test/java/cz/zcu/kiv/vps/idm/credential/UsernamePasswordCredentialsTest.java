package cz.zcu.kiv.vps.idm.credential;

import org.junit.Before;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class UsernamePasswordCredentialsTest {

    private UsernamePasswordCredentials credentials;

    private String userName = "admin";
    private String hash = "#123456789";;

    @Before
    public void setUp() {
        credentials = new UsernamePasswordCredentials("1", Base64.getEncoder().encodeToString((userName+":"+hash).getBytes()));
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals(userName, credentials.getUsername());
    }

    @Test
    public void getHash() throws Exception {
        assertEquals(hash, credentials.getHash());
    }

}