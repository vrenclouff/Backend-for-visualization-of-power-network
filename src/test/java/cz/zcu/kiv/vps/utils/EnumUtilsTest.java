package cz.zcu.kiv.vps.utils;

import cz.zcu.kiv.vps.idm.model.Permission;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by Lukas Cerny.
 */
public class EnumUtilsTest {

    private final String expectedJsonString =
            "[" +
                "{" +
                    "\"name\":\"READ\"," +
                    "\"value\":\"4\"" +
                "}," +
                "{" +
                    "\"name\":\"WRITE\"," +
                    "\"value\":\"2\"" +
                "}" +
            "]";

    @Test
    public void toJSON() throws Exception {
        JSONAssert.assertEquals(expectedJsonString, EnumUtils.toJSON(Permission.class), false);
    }
}