package cz.zcu.kiv.vps.idm.utils;

import cz.zcu.kiv.vps.idm.model.Permission;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Lukas Cerny.
 */
public class PermissionUtils {

    /**
     * Method converts integer value to array of permissions.
     * @param integer value represents permissions
     * @return array of permissions
     */
    public static Permission[] convertToArray(int integer) {
        if (integer == 0) return new Permission[]{};
        return Stream.of(Permission.values())
                .filter(e -> (e.getValue() & integer) != 0)
                .toArray(Permission[]::new);
    }

    /**
     * Method converts integer value to list of permissions.
     * @param integer value represents permissions
     * @return list of permissions
     */
    public static List<Permission> convertToList(int integer) {
        if (integer == 0) return Collections.emptyList();
        return Stream.of(Permission.values())
                .filter(e -> (e.getValue() & integer) != 0)
                .collect(Collectors.toList());
    }

    /**
     * Method converts array of permissions to integer value.
     * @param array array of permissions
     * @return integer value
     */
    public static int convertToInteger(Permission[] array) {
        if (array == null || array.length == 0) return 0;
        return Stream.of(array).mapToInt(e -> e.getValue()).sum();
    }
}
