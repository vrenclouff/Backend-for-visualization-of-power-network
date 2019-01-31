package cz.zcu.kiv.vps.managers.core;

import cz.zcu.kiv.vps.model.converters.ModelConverter;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.model.domain.DummyUser;
import cz.zcu.kiv.vps.model.domain.Model;
import cz.zcu.kiv.vps.idm.api.LoggedUserStorage;
import cz.zcu.kiv.vps.model.repositories.api.UserRepository;
import cz.zcu.kiv.vps.core.exceptions.RepositoryException;
import cz.zcu.kiv.vps.idm.exceptions.UserManagerException;
import cz.zcu.kiv.vps.idm.model.*;
import cz.zcu.kiv.vps.idm.utils.PermissionUtils;
import cz.zcu.kiv.vps.utils.ModelUtils;
import cz.zcu.kiv.vps.managers.api.UserManager;
import cz.zcu.kiv.vps.model.repositories.api.ModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.*;

/**
 * Created by Lukas Cerny.
 */

public class UserManagerImpl implements UserManager {

    private static final Logger logger = LogManager.getLogger(UserManagerImpl.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ModelRepository modelRepository;

    @Inject
    private LoggedUserStorage loggedUserStorage;

    @Inject
    private Provider<LoggedUser> loggedUserProvider;

    @Override
    public void insert(UserDTO user) throws UserManagerException {

        if (user == null) {
            throw new UserManagerException(Messages.getString("userManager.insert"));
        }

        User entity = ModelConverter.convert(user, User.class);

        try {
            userRepository.create(entity);
        }catch (NullPointerException | RepositoryException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    @Override
    public void update(UserDTO user) throws UserManagerException {

        if (user == null) {
            throw new UserManagerException(Messages.getString("userManager.update"));
        }

        User entity;
        try {
            entity = (User) userRepository.findByID(user.getId());
        } catch (RepositoryException e) {
            throw new UserManagerException(e.getMessage());
        }

        if (entity == null) {
            throw new UserManagerException(Messages.getString("userManager.update"));
        }

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setRole(user.getRole());
        entity.setEmail(user.getEmail());
        entity.setAllowed(user.getAllowed());
        entity.setHash(user.getHash());

        try {
            userRepository.update(entity);
        } catch (RepositoryException | NullPointerException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    @Override
    public void delete(Long userID) throws UserManagerException {

        if (userID == null) {
            throw new UserManagerException(Messages.getString("userManager.delete"));
        }

        if (loggedUserStorage.isLogged(userID)) {
            throw new UserManagerException(Messages.getString("userManager.delete.logged"));
        }

        try {
            changeModelPermission(userID, Collections.emptyList());
            userRepository.delete(userID);

        } catch (RepositoryException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    @Override
    public UserDTO info(Long userID) throws UserManagerException {

        if (userID == null) {
            throw new UserManagerException(Messages.getString("userManager.info"));
        }

        try {
            User user = (User) userRepository.findByID(userID);
            return ModelConverter.convert(user, UserDTO.class);
        } catch (RepositoryException | NullPointerException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    @Override
    public UserDTO current() throws UserManagerException {

        LoggedUser loggedUser = loggedUserProvider.get();

        if (loggedUser != null) {
            User user = loggedUser.getUser();
            return ModelConverter.convert(user, UserDTO.class);
        }else {
            throw new UserManagerException(Messages.getString("userManager.current"));
        }
    }

    @Override
    public List<UserDTO> all() {
        List users = userRepository.findAll();
        return ModelConverter.convert(users, UserDTO.class);
    }

    @Override
    public void changeModelPermission(Long userID, List<GraphModelDTO> models) throws UserManagerException {

        if (userID == null) {
            throw new UserManagerException(Messages.getString("userManager.changeModelPermission"));
        }

        if (models == null) {
            models = Collections.emptyList();
        }

        User user;
        try {
             user = (User) userRepository.findByID(userID);
        } catch (RepositoryException | NullPointerException e) {
            throw new UserManagerException(e.getMessage());
        }

        if (user == null) {
            throw new UserManagerException(Messages.getString("userManager.changeModelPermission"));
        }

        Map<Long, GraphModel> newGraphModelMap = ModelUtils.convertChangedModels(models);

        List<Model> newListOfModels = modelRepository.findByIDS(newGraphModelMap.keySet().toArray(new Long[0]));
        List<Model> actualListOfModels =  modelRepository.findAllWhereIsCustomer(userID);

        user.setModels(new LinkedList<>(newGraphModelMap.values()));

        try {
            ModelUtils.mergeNewAndOldPermissions(user.getId(), actualListOfModels, newListOfModels, newGraphModelMap)
                    .forEach(model -> {
                        try {
                            modelRepository.update(model);
                        } catch (RepositoryException exp) {
                            throw new IllegalArgumentException(exp.getMessage());
                        }
                    });
            userRepository.update(user);
        }catch (IllegalArgumentException | RepositoryException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    @Override
    public Permission[] userPermissionsForModel(Long userID, Long modelID) {

        if (userID == null || modelID == null) {
            return new Permission[0];
        }

        DummyUser dummyUser = modelRepository.findCustomerForModel(userID, modelID);
        if (dummyUser != null) {
            return PermissionUtils.convertToArray(dummyUser.getPermissions());
        }else {
            return new Permission[0];
        }
    }

    @Override
    public boolean isUserAllowedForModel(Long userID, Long modelID) {

        if (userID == null || modelID == null) {
            return false;
        }

        return modelRepository.findCustomerForModel(userID, modelID) != null;
    }
}
