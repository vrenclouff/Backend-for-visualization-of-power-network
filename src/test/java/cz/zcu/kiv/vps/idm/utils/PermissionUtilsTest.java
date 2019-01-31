package cz.zcu.kiv.vps.idm.utils;

import cz.zcu.kiv.vps.idm.model.Permission;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class PermissionUtilsTest {

    @Test
    public void convertToArray() throws Exception {
        int intPermission = 0;
        Permission[] permissions;

        intPermission = Permission.READ.getValue();
        permissions = PermissionUtils.convertToArray(intPermission);
        assertEquals(1, permissions.length);

        intPermission = Permission.READ.getValue() | Permission.WRITE.getValue();
        permissions = PermissionUtils.convertToArray(intPermission);
        assertEquals(2, permissions.length);

        intPermission = Permission.WRITE.getValue();
        permissions = PermissionUtils.convertToArray(intPermission);
        assertEquals(1, permissions.length);

        intPermission = 0;
        permissions = PermissionUtils.convertToArray(intPermission);
        assertEquals(0, permissions.length);
    }

    @Test
    public void convertToInteger() throws Exception {
        int intPermission = 0;
        Permission[] permissions;

        permissions = new Permission[]{Permission.WRITE, Permission.READ};
        intPermission = PermissionUtils.convertToInteger(permissions);
        assertEquals(6, intPermission);

        permissions = new Permission[]{Permission.READ};
        intPermission = PermissionUtils.convertToInteger(permissions);
        assertEquals(4, intPermission);

        permissions = new Permission[]{Permission.WRITE};
        intPermission = PermissionUtils.convertToInteger(permissions);
        assertEquals(2, intPermission);

        permissions = new Permission[]{};
        intPermission = PermissionUtils.convertToInteger(permissions);
        assertEquals(0, intPermission);
    }

}