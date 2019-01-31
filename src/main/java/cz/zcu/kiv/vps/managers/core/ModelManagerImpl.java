package cz.zcu.kiv.vps.managers.core;

import cz.zcu.kiv.vps.model.converters.ModelConverter;
import cz.zcu.kiv.vps.model.dto.ModelDTO;
import cz.zcu.kiv.vps.idm.model.LoggedUser;
import cz.zcu.kiv.vps.idm.model.Role;
import cz.zcu.kiv.vps.managers.api.ModelManager;
import cz.zcu.kiv.vps.model.repositories.api.ModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Lukas Cerny.
 */
public class ModelManagerImpl implements ModelManager {

    private static final Logger logger = LogManager.getLogger(ModelManagerImpl.class);

    @Inject
    private ModelRepository modelRepository;

    @Inject
    private LoggedUser loggedUser;

    @Override
    public  Map<String, Object> getHighLevelSimpleGraph(final Long modelID, final List<Integer> voltageLevels) {
        return Collections.emptyMap();
    }

    @Override
    public  Map<String, Object> getLowLevelSimpleGraph(final Long modelID, final List<Integer> voltageLevels) {
        return Collections.emptyMap();
    }

    @Override
    public  Map<String, Object> getHighLevelDetailGraph(final Long modelID, final List<Integer> voltageLevels) {
        return Collections.emptyMap();
    }

    @Override
    public  Map<String, Object> getLowLevelDetailGraph(final Long modelID, final List<Integer> voltageLevels) {
        return Collections.emptyMap();
    }

    @Override
    public List<ModelDTO> getAll() {

        if (loggedUser == null) {
            return Collections.emptyList();
        }

        List models;
        if (loggedUser.getUser().getRole().equals(Role.ADMIN)) {
            models = modelRepository.findAll();
            return ModelConverter.convert(models, ModelDTO.class);
        }else {
            models = modelRepository.findAllWhereIsCustomer(loggedUser.getUser().getId());
        }
        return ModelConverter.convert(models, ModelDTO.class);
    }
}
