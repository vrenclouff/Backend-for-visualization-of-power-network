package cz.zcu.kiv.vps.model.repositories.api;


import cz.zcu.kiv.vps.model.domain.DummyUser;
import cz.zcu.kiv.vps.model.domain.Model;

import java.util.List;

/**
 * Created by Lukas Cerny.
 */
public interface ModelRepository extends AbstractRepository {

    /**
     * Method finds user which is saved in customers of models.
     * @param userID
     * @param modelID
     * @return
     */
    DummyUser findCustomerForModel(final Long userID, final Long modelID);

    /**
     * Method finds all modes where is contained user.
     * @param userID
     * @return
     */
    List<Model> findAllWhereIsCustomer(final Long userID);

    /**
     * Method finds all models by array of models's IDs.
     * @param ids
     * @return
     */
    List<Model> findByIDS(final Long ... ids);
}
