package cz.zcu.kiv.vps.model.converters;

import cz.zcu.kiv.vps.idm.model.Permission;
import cz.zcu.kiv.vps.idm.utils.PermissionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * Created by Lukas Cerny.
 *
 * Class converts from domain object to DTO and back.
 */
public class ModelConverter {

    /* Instance represents converter  */
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        createTypeMap();
        createConverters();
        modelMapper.validate();
    }

    /**
     * Static method sets up domain object to convert.
     * These objects are validation for correct convert.
     */
    private static void createTypeMap() {

    }

    /**
     * Static method defines exception in domains object and DTOs.
     */
    private static void createConverters() {

        modelMapper.createTypeMap(Integer.class, Permission[].class).setConverter(e -> PermissionUtils.convertToArray(e.getSource()));
        modelMapper.createTypeMap(Permission[].class, Integer.class).setConverter(e -> PermissionUtils.convertToInteger(e.getSource()));
    }

    /**
     * Method generic converts object.
     * @param source
     * @param destinationType
     * @param <D>
     * @return
     */
    public static <D> D convert(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    /**
     * Method generic converts list.
     * @param source
     * @param type
     * @param <D>
     * @return
     */
    public static <D> List<D> convert(List<?> source, Class<D> type) {
        return modelMapper.map(source, new ListOfType<D>(type));
    }

    /**
     * Method generic converts set.
     * @param source
     * @param type
     * @param <D>
     * @return
     */
    public static <D> Set<D> convert(Set<?> source, Class<D> type) {
        return modelMapper.map(source, new SetOfType<D>(type));
    }
}

class ListOfType<X> implements ParameterizedType {

    private Class<?> wrapped;

    public ListOfType(Class<X> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {wrapped};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }
}

class SetOfType<X> implements ParameterizedType {

    private Class<?> wrapped;

    public SetOfType(Class<X> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {wrapped};
    }

    public Type getRawType() {
        return Set.class;
    }

    public Type getOwnerType() {
        return null;
    }
}