package cz.zcu.kiv.vps.idm.utils;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class SecurityUtilsTest {

    @Test
    public void decodeBase64Hash() throws Exception {
        String userName = "admin";
        String hash = "#123456789";
        String base64Hash = Base64.getEncoder().encodeToString((userName+":"+hash).getBytes());
        String[] decodeHash = SecurityUtils.decodeBase64Hash(base64Hash);
        assertEquals(userName, decodeHash[0]);
        assertEquals(hash, decodeHash[1]);
    }

    @Test
    public void decodeBase64HashEmpty() throws Exception {
        String[] decodeHash = SecurityUtils.decodeBase64Hash(null);
        assertNull(decodeHash[0]);
        assertNull(decodeHash[1]);
    }

    @Test
    public void decodeBase64HashBadFormat() throws Exception {
        String base64Hash = Base64.getEncoder().encodeToString(("admin#123456789").getBytes());
        String[] decodeHash = SecurityUtils.decodeBase64Hash(base64Hash);
        assertNull(decodeHash[0]);
        assertNull(decodeHash[1]);
    }
}