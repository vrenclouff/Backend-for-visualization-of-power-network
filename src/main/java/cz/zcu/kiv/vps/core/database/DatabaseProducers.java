package cz.zcu.kiv.vps.core.database;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by Lukas Cerny.
 *
 * Class produces entity managers to database access.
 */
public class DatabaseProducers {

    @PersistenceContext(unitName = "ModelsDataSource")
    private EntityManager modelsEntityManager;

    @PersistenceContext(unitName = "UsersDataSource")
    private EntityManager usersEntityManager;

    @Produces @DatabaseQualifier(DatabaseType.MODELS)
    public EntityManager models() {
        return modelsEntityManager;
    }

    @Produces @DatabaseQualifier(DatabaseType.USERS)
    public EntityManager users() {
        return usersEntityManager;
    }
}