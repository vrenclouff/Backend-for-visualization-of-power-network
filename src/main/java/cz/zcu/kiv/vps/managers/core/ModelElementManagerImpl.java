package cz.zcu.kiv.vps.managers.core;

import cz.zcu.kiv.vps.managers.api.ModelElementManager;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by Lukas Cerny.
 */
public class ModelElementManagerImpl<E, D extends Object> implements ModelElementManager {

    private Class<E> entityType;

    private Class<D> dtoType;

    @Override
    public Object info(Long modelID, Long elementID) {
        throw new NotImplementedException(this.getClass().getName()+".info(Long modelID, Long elementID)"+" is not implemented.");
    }

    @Override
    public void update(Long modelID, Long elementID, Object object) {
        throw new NotImplementedException(this.getClass().getEnclosingMethod().getName()+".update(Long modelID, Long elementID, Object object)"+" is not implemented.");
    }

    @Override
    public void delete(Long modelID, Long elementID) {
        throw new NotImplementedException(this.getClass().getEnclosingMethod().getName()+".delete(Long modelID, Long elementID)"+" is not implemented.");
    }

    @Override
    public void insert(Long modelID, Object object) {
        throw new NotImplementedException(this.getClass().getEnclosingMethod().getName()+".insert(Long modelID, Object object)"+" is not implemented.");
    }
}
