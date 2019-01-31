package cz.zcu.kiv.vps.model.repositories.api;

import cz.zcu.kiv.vps.core.exceptions.RepositoryException;

import java.util.List;

/**
 * Created by Lukas Cerny.
 *
 * AbstractRepository is wrapper for all repositories. This class contains CRUD operations above object.
 */
public interface AbstractRepository<T extends Object> {

    /**
     * Method saves new object to database.
     * @param object
     * @throws RepositoryException
     */
    void create(T object) throws RepositoryException;

    /**
     * Method updates exist object in davabase.
     * @param object
     * @throws RepositoryException
     */
    void update(T object) throws RepositoryException;

    /**
     * Method deletes object in database.
     * @param id
     * @throws RepositoryException
     */
    void delete(Long id) throws RepositoryException;

    /**
     * Method finds all object in database.
     * @return
     */
    List<T> findAll();

    /**
     * Method finds object by ID.
     * @param id
     * @return
     * @throws RepositoryException
     */
    T findByID(Long id) throws RepositoryException;
}
