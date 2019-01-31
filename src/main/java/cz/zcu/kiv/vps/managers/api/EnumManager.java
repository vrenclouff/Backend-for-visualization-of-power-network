package cz.zcu.kiv.vps.managers.api;

import java.util.Map;

/**
 * Created by Lukas Cerny.
 */
public interface EnumManager {

    /**
     * Method creates map with all enum for model.
     * @return map where key represents name of enum and value represents all values of enums.
     */
    Map<String, String> getModel();

    /**
     * Method creates map with all enum for user.
     * @return map where key represents name of enum and value represents all values of enums.
     */
    Map<String, String> getUser();
}
