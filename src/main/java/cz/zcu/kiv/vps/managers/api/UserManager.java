package cz.zcu.kiv.vps.managers.api;

import cz.zcu.kiv.vps.idm.exceptions.UserManagerException;
import cz.zcu.kiv.vps.idm.model.GraphModelDTO;
import cz.zcu.kiv.vps.idm.model.Permission;
import cz.zcu.kiv.vps.idm.model.UserDTO;

import java.util.List;

/**
 * Created by Lukas Cerny.
 */
public interface UserManager {

    /**
     * Method creates new user and saves to database.
     * @param user object represents new user
     * @throws UserManagerException
     */
    void insert(final UserDTO user) throws UserManagerException;

    /**
     * Method deletes user
     * @param userID identification of user
     * @throws UserManagerException
     */
    void delete(final Long userID) throws UserManagerException;

    /**
     * Method updates user
     * @param user object represents user
     * @throws UserManagerException
     */
    void update(final UserDTO user) throws UserManagerException;

    /**
     * Method loads and returns information about user
     * @param userID identification of user
     * @return information about user
     * @throws UserManagerException
     */
    UserDTO info(final Long userID) throws UserManagerException;

    /**
     * Method loads and returns information about logged user.
     * @return information about user
     * @throws UserManagerException
     */
    UserDTO current() throws UserManagerException;

    /**
     * Method loads and returns all users in system.
     * @return list of users
     */
    List<UserDTO> all();

    /**
     * Method changes permissions for models.
     * @param userID identification of user
     * @param models list of changes
     * @throws UserManagerException
     */
    void changeModelPermission(final Long userID, List<GraphModelDTO> models) throws UserManagerException;

    /**
     * Method loads and returns permissions for model by user
     * @param userID identification of user
     * @param modelID identification of model
     * @return array of permissions
     */
    Permission[] userPermissionsForModel(final Long userID, final Long modelID);

    /**
     * Method validates user's permissions for model.
     * @param userID identification of user
     * @param modelID identification of model
     * @return boolean value represents result
     */
    boolean isUserAllowedForModel(final Long userID, final Long modelID);
}
