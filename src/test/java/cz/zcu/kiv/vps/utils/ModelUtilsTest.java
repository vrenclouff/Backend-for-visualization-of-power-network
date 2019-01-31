package cz.zcu.kiv.vps.utils;

import cz.zcu.kiv.vps.model.domain.DummyUser;
import cz.zcu.kiv.vps.model.domain.Model;
import cz.zcu.kiv.vps.idm.model.GraphModel;
import cz.zcu.kiv.vps.idm.model.GraphModelDTO;
import cz.zcu.kiv.vps.idm.model.Permission;
import cz.zcu.kiv.vps.idm.utils.PermissionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class ModelUtilsTest {


    @Test
    public void mergeNewAndOldPermissionsChangePermission() throws Exception {

        Long userID = 10L;

        List<Model> newModels = Arrays.asList(createModel(1, StringUtils.EMPTY));

        Model model = createModel(1, StringUtils.EMPTY);
        model.getCustomers().add(new DummyUser(userID, Permission.READ.getValue()));
        List<Model> actualModels = Arrays.asList(model);

        Model changedModel = createModel(1, StringUtils.EMPTY);
        Integer changedPermission = Permission.READ.getValue() | Permission.WRITE.getValue();

        Map<Long, GraphModel> batchOfChanges = new HashMap<>();
        batchOfChanges.put(changedModel.getId(), new GraphModel(changedModel.getId(), changedModel.getName(), changedPermission));

        assertEquals(1, newModels.size());
        assertEquals(1, actualModels.get(0).getCustomers().size());
        assertEquals(Permission.READ.getValue(), actualModels.get(0).getCustomers().get(0).getPermissions().intValue());

        Set<Model> mergedModels = ModelUtils.mergeNewAndOldPermissions(userID, actualModels, newModels, batchOfChanges);

        assertEquals(1, mergedModels.size());
        assertEquals(1, mergedModels.iterator().next().getCustomers().size());
        assertEquals(changedPermission, mergedModels.iterator().next().getCustomers().get(0).getPermissions());
    }

    @Test
    public void mergeNewAndOldPermissionsAddPermission() throws Exception {

        Long userID = 10L;
        List<Model> newModels = Collections.singletonList(createModel(1, StringUtils.EMPTY));
        List<Model> actualModels = Collections.emptyList();

        Model changedModel = createModel(1, StringUtils.EMPTY);
        Integer changedPermission = Permission.READ.getValue() | Permission.WRITE.getValue();

        Map<Long, GraphModel> batchOfChanges = new HashMap<>();
        batchOfChanges.put(changedModel.getId(), new GraphModel(changedModel.getId(), changedModel.getName(), changedPermission));

        assertEquals(1, newModels.size());
        assertEquals(0, newModels.get(0).getCustomers().size());
        assertEquals(0, actualModels.size());

        Set<Model> mergedModels = ModelUtils.mergeNewAndOldPermissions(userID, actualModels, newModels, batchOfChanges);

        assertEquals(1, mergedModels.size());
        assertEquals(1, mergedModels.iterator().next().getCustomers().size());
        assertEquals(changedPermission, mergedModels.iterator().next().getCustomers().get(0).getPermissions());
    }

    @Test
    public void mergeNewAndOldPermissionsDeletePermission() throws Exception {

        Long userID = 10L;
        List<Model> newModels = Collections.emptyList();

        Model model = createModel(1, StringUtils.EMPTY);
        model.getCustomers().add(new DummyUser(userID, Permission.READ.getValue()));
        List<Model> actualModels = Collections.singletonList(model);

        Map<Long, GraphModel> batchOfChanges = new HashMap<>();

        assertEquals(0, newModels.size());
        assertEquals(1, actualModels.size());
        assertEquals(1, actualModels.get(0).getCustomers().size());
        assertEquals(Permission.READ.getValue(), actualModels.get(0).getCustomers().get(0).getPermissions().intValue());

        Set<Model> mergedModels = ModelUtils.mergeNewAndOldPermissions(userID, actualModels, newModels, batchOfChanges);

        assertEquals(1, mergedModels.size());
        assertEquals(0, mergedModels.iterator().next().getCustomers().size());
    }

    @Test
    public void mergeNewAndOldPermissionsWithConverterChangedModels() throws Exception {

        Long userID = 10L;

        List<Model> newModels = Collections.singletonList(createModel(1, StringUtils.EMPTY));

        Model model = createModel(1, StringUtils.EMPTY);
        model.getCustomers().add(new DummyUser(userID, Permission.READ.getValue()));
        List<Model> actualModels = Arrays.asList(model);

        List<GraphModelDTO> batchOfChanges = Arrays.asList(createGraphModelDTO(1, "Model 1", Permission.READ.getValue()));

        assertEquals(1, newModels.size());
        assertEquals(1, actualModels.get(0).getCustomers().size());
        assertEquals(Permission.READ.getValue(), actualModels.get(0).getCustomers().get(0).getPermissions().intValue());

        Set<Model> mergedModels = ModelUtils.mergeNewAndOldPermissions(userID, actualModels, newModels, ModelUtils.convertChangedModels(batchOfChanges));

        assertEquals(1, mergedModels.size());
        assertEquals(1, mergedModels.iterator().next().getCustomers().size());
        assertEquals(Permission.READ.getValue(), mergedModels.iterator().next().getCustomers().get(0).getPermissions().intValue());
    }

    @Test
    public void mergeNewAndOldPermissionsNull() throws Exception {
        Set<Model> mergedModels = ModelUtils.mergeNewAndOldPermissions(null, null, null, null);
        assertEquals(0, mergedModels.size());
    }

    @Test
    public void convertChangedModelsFilter() throws Exception {

        List<GraphModelDTO> batchOfChanges = Arrays.asList(
                createGraphModelDTO(1, "Model 1", 0),
                createGraphModelDTO(2, "Model 1", 2),
                createGraphModelDTO(3, "Model 1", 4),
                createGraphModelDTO(4, "Model 1", 6)
                );

        assertEquals(3, ModelUtils.convertChangedModels(batchOfChanges).size());
    }

    @Test
    public void convertChangedModelsConvert() throws Exception {

        List<GraphModelDTO> batchOfChanges = Collections.singletonList(createGraphModelDTO(1, "Model 1", 2));
        Map<Long, GraphModel> bathOfChangesMap = ModelUtils.convertChangedModels(batchOfChanges);

        assertEquals(1, bathOfChangesMap.size());
        assertTrue(bathOfChangesMap.containsKey(batchOfChanges.get(0).getModelID()));
        assertEquals(GraphModel.class, bathOfChangesMap.get(batchOfChanges.get(0).getModelID()).getClass());
        assertEquals(batchOfChanges.get(0).getName(), bathOfChangesMap.get(batchOfChanges.get(0).getModelID()).getName());
        assertEquals(PermissionUtils.convertToInteger(batchOfChanges.get(0).getPermissions()), bathOfChangesMap.get(batchOfChanges.get(0).getModelID()).getPermissions().intValue());
    }

    @Test
    public void convertChangedModelsNull() throws Exception {
        assertEquals(0, ModelUtils.convertChangedModels(null).size());
    }

    private GraphModelDTO createGraphModelDTO(int modelID, String name, Integer permissions) {
        GraphModelDTO result = new GraphModelDTO();
        result.setModelID((long) modelID);
        result.setName(name);
        result.setPermissions(PermissionUtils.convertToArray(permissions));
        return result;
    }

    private Model createModel(int id, String description) {
        Model result = new Model();
        result.setId((long) id);
        result.setName("Model " + id);
        result.setDescription(description);
        return result;
    }
}