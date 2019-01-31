package cz.zcu.kiv.vps.model.repositories.core;

import cz.zcu.kiv.vps.core.database.Database;
import cz.zcu.kiv.vps.core.database.DatabaseLiteral;
import cz.zcu.kiv.vps.core.config.Messages;
import cz.zcu.kiv.vps.core.exceptions.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Lukas Cerny.
 */

public class AbstractRepository<T extends Object> implements cz.zcu.kiv.vps.model.repositories.api.AbstractRepository, Serializable {

    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);

    private final Class<T> genericType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManager entityManager;

    @Inject @Any
    private Instance<EntityManager> entityManagerInstance;

    @PostConstruct
    public void init() {
        try {
            String databaseType = getClass().getAnnotation(Database.class).value();
            entityManager = entityManagerInstance.select(new DatabaseLiteral(databaseType)).get();
        }catch (NullPointerException e) {
            throw new RuntimeException(getClass().getName()+" doesn't contain annotation @Database with DatabaseType.");
        }
    }

    @Override
    public void create(Object object) throws RepositoryException {
        logger.debug("Adding '"+genericType.getTypeName()+"' entity to database.");

        if (object == null) {
            throw new NullPointerException(Messages.getString("abstractRepository.create.null"));
        }

        try {
            entityManager.persist(object);
        }catch (EntityExistsException e) {
            throw new RepositoryException(Messages.getString("abstractRepository.create.duplicate"));
        }catch (IllegalArgumentException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void update(Object object) throws RepositoryException {
        logger.debug("Updating '"+genericType.getTypeName()+"' entity to database.");

        if (object == null) {
            throw new NullPointerException(Messages.getString("abstractRepository.update.null"));
        }

        try {
            entityManager.merge(object);
        }catch (IllegalArgumentException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        T reference = entityManager.getReference(genericType, id);
        if (reference != null) {
            logger.debug("Deleting '"+genericType.getTypeName()+"' entity from database.");
            try {
                entityManager.remove(reference);
            }catch (IllegalArgumentException e) {
                throw new RepositoryException(e.getMessage());
            }
        }else {
            throw new NullPointerException(Messages.getString("abstractRepository.delete.null"));
        }
    }

    @Override
    public List<T> findAll() {
        logger.debug("Finding all '"+genericType.getTypeName()+"' entities from database.");
        String query = "SELECT t FROM " + genericType.getSimpleName() + " t";
        return entityManager.createQuery(query, genericType).getResultList();
    }

    @Override
    public T findByID(Long id) throws RepositoryException {
        logger.debug("Finding '"+genericType.getTypeName()+"' entity by id from database.");

        if (id == null) {
            throw new NullPointerException(Messages.getString("abstractRepository.findByID.null"));
        }

        try {
            return entityManager.find(genericType, id);
        }catch (IllegalArgumentException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
