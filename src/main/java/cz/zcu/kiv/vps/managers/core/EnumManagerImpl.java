package cz.zcu.kiv.vps.managers.core;

import cz.zcu.kiv.vps.idm.model.Permission;
import cz.zcu.kiv.vps.idm.model.Role;
import cz.zcu.kiv.vps.managers.api.EnumManager;
import cz.zcu.kiv.vps.utils.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.uncapitalize;


/**
 * Created by Lukas Cerny.
 */
public class EnumManagerImpl implements EnumManager {

    private static final String PACKAGE_OF_DOMAINS = "cz.zcu.kiv.vps.model.domain";

    private static final Logger logger = LogManager.getLogger(EnumManagerImpl.class);

    @Override
    public Map<String, String> getModel() {
        return new Reflections(PACKAGE_OF_DOMAINS).getSubTypesOf(Enum.class).stream()
                .collect(toMap(k -> uncapitalize(k.getSimpleName()), EnumUtils::toJSON));
    }

    @Override
    public Map<String, String> getUser() {
        return Stream.of(Permission.class, Role.class)
                .collect(toMap(k -> uncapitalize(k.getSimpleName()), EnumUtils::toJSON));
    }
}
