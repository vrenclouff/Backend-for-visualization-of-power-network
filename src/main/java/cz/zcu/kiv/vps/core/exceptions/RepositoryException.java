package cz.zcu.kiv.vps.core.exceptions;

/**
 * Created by Lukas Cerny.
 *
 * Wrapper for exceptions in repositories.
 */
public class RepositoryException extends Exception {

    public RepositoryException(String message) {
        super(message);
    }
}
