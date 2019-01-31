package cz.zcu.kiv.vps.idm.exceptions;

/**
 * Created by Lukas Cerny.
 *
 * Exception which is used for exceptions in userManager class.
 */
public class UserManagerException extends Exception {

    public UserManagerException(String message) {
        super(message);
    }
}
