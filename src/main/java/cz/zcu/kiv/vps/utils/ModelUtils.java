package cz.zcu.kiv.vps.utils;

import cz.zcu.kiv.vps.model.converters.ModelConverter;
import cz.zcu.kiv.vps.model.domain.DummyUser;
import cz.zcu.kiv.vps.model.domain.Model;
import cz.zcu.kiv.vps.idm.model.GraphModel;
import cz.zcu.kiv.vps.idm.model.GraphModelDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Created by Lukas Cerny.
 */
public class ModelUtils {

    /**
     * Method merges permissions for model.
     * @param userID identification of user
     * @param actualModels list of models to which user has permissions
     * @param newModels list of models to which will by added user's permissions
     * @param batchOfChanges map of changes which contains permissions for models
     * @return set of models which was changed
     */
    public static Set mergeNewAndOldPermissions(Long userID, List<Model> actualModels, List<Model> newModels, Map<Long, GraphModel> batchOfChanges) {

        if (userID == null || actualModels == null || newModels == null || batchOfChanges == null) {
            return emptySet();
        }

        return Stream.concat(actualModels.stream(), newModels.stream()).filter(distinctByKey(Model::getId)).map(model -> {
            if (batchOfChanges.containsKey(model.getId())) {

                DummyUser dummyUser = model.getCustomers().stream()
                        .filter(e -> e.getUserID().equals(userID))
                        .findFirst().orElse(null);

                Integer permission = batchOfChanges.get(model.getId()).getPermissions();
                if (dummyUser != null) {
                    dummyUser.setPermissions(permission);
                }else {
                    model.getCustomers().add(new DummyUser(userID, permission));
                }

            }else {
                List<DummyUser> tmp = model.getCustomers();
                tmp.removeIf(e -> e.getUserID().equals(userID));
                model.setCustomers(new ArrayList<>(tmp));
            }
            return model;
        }).collect(toSet());
    }

    /**
     * Method creates predicate for remove extra objects
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Method converts list of models to map where key is ID of model and value is object which contain information about model and permissions.
     * @param models
     * @return
     */
    public static Map<Long, GraphModel> convertChangedModels(List<GraphModelDTO> models) {
        if (models == null) {
            return Collections.emptyMap();
        }
        return models.stream()
                .filter(e -> e.getPermissions().length != 0)
                .map(e -> ModelConverter.convert(e, GraphModel.class))
                .collect(toMap(GraphModel::getModelID, e -> e));
    }
}
