package cz.zcu.kiv.vps.idm.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by Lukas Cerny.
 */
public class SecurityUtils {

    /**
     * Methot decodes string input to array which represents username and hash.
     * @param baseHash string encodes with format username:hash
     * @return array represents credentials
     */
    public static String[] decodeBase64Hash(String baseHash) {
        if (StringUtils.isNotEmpty(baseHash)) {
            byte[] decoded = Base64.getDecoder().decode(baseHash);
            String decodedString = new String(decoded, StandardCharsets.UTF_8);
            if (decodedString.contains(":")) {
                return decodedString.split(":");
            } else {
                return new String[]{null, null};
            }
        }else {
            return new String[]{null, null};
        }
    }

    /**
     * Method creates SHA256 hash.
     * @param string string for hash
     * @return hash
     */
    public static String createSHA256Hash(String string) {
        return DigestUtils.sha256Hex(string);
    }

    /**
     * Method creates random hash.
     * @return hash
     */
    public static String secureHash() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    /**
     * Method create authorization hash from username and salt.
     * @param username
     * @param salt
     * @return hash
     */
    public static String createAuthToken(String username, String salt) {
        return createSHA256Hash(username + salt);
    }
}
