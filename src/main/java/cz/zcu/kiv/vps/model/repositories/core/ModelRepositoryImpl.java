package cz.zcu.kiv.vps.model.repositories.core;

import static cz.zcu.kiv.vps.core.database.DatabaseType.MODELS;

import cz.zcu.kiv.vps.core.database.Database;
import cz.zcu.kiv.vps.model.domain.DummyUser;
import cz.zcu.kiv.vps.model.domain.Model;
import cz.zcu.kiv.vps.model.repositories.api.ModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;


/**
 * Created by Lukas Cerny.
 */

@Stateless @Database(MODELS)
public class ModelRepositoryImpl extends AbstractRepository<Model> implements ModelRepository {

    private static final Logger logger = LogManager.getLogger(ModelRepositoryImpl.class);

    @Override
    public DummyUser findCustomerForModel(Long userID, Long modelID) {

        if (userID == null || modelID == null) {
            return null;
        }

        try {
            return entityManager.createNamedQuery("Model.findDummyUser", DummyUser.class)
                    .setParameter("userID", userID)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Model> findAllWhereIsCustomer(Long userID) {

        if (userID == null) {
            return Collections.emptyList();
        }

        return entityManager.createNamedQuery("Model.findAllWhereIsCustomer", Model.class)
                .setParameter("userID", userID)
                .getResultList();
    }

    @Override
    public List<Model> findByIDS(Long... ids) {

        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }

        return entityManager.createNamedQuery("Model.findByIDS", Model.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
