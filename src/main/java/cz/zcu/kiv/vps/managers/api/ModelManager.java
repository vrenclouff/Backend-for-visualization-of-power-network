package cz.zcu.kiv.vps.managers.api;

import cz.zcu.kiv.vps.model.dto.ModelDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Lukas Cerny.
 */
public interface ModelManager {

    /**
     * Method loads and returns simple information about high level graph.
     * @param modelID identification of model
     * @param voltageLevels voltage levels
     * @return map where key is element type and value is list of objects represents information
     */
    Map<String, Object> getHighLevelSimpleGraph(final Long modelID, final List<Integer> voltageLevels);

    /**
     * Method loads and returns simple information about low level graph.
     * @param modelID identification of model
     * @param voltageLevels voltage levels
     * @return map where key is element type and value is list of objects represents information
     */
    Map<String, Object> getLowLevelSimpleGraph(final Long modelID, final List<Integer> voltageLevels);

    /**
     * Method loads and returns detail information about high level graph.
     * @param modelID identification of model
     * @param voltageLevels voltage levels
     * @return map where key is element type and value is list of objects represents information
     */
    Map<String, Object> getHighLevelDetailGraph(final Long modelID, final List<Integer> voltageLevels);

    /**
     * Method loads and returns detail information about low level graph.
     * @param modelID identification of model
     * @param voltageLevels voltage levels
     * @return map where key is element type and value is list of objects represents information
     */
    Map<String, Object> getLowLevelDetailGraph(final Long modelID, final List<Integer> voltageLevels);

    /**
     * Method loads and returns all models to which user has permissions
     * @return list of models
     */
    List<ModelDTO> getAll();
}
