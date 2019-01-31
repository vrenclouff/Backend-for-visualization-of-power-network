package cz.zcu.kiv.vps.managers.api;

/**
 * Created by Lukas Cerny.
 */
public interface ModelElementManager<E, D extends Object> {

    /**
     * Method gets information about element.
     * @param modelID identification of model
     * @param elementID identification of element
     * @return object represents element
     */
    D info(Long modelID, Long elementID);

    /**
     * Method updates element information
     * @param modelID identification of model
     * @param elementID identification of element
     * @param object object represents element
     */
    void update(Long modelID, Long elementID, D object);

    /**
     * Method deletes element in model
     * @param modelID identification of model
     * @param elementID identification of element
     */
    void delete(Long modelID, Long elementID);

    /**
     * Method inserts element to model.
     * @param modelID identification of model
     * @param object object represents element
     */
    void insert(Long modelID, D object);
}
