package cz.zcu.kiv.vps.utils;

/**
 * Created by Lukas Cerny.
 */
public class ClassUtils {

    private static final String DOMAIN_PACKAGE = "cz.zcu.kiv.vps.model.domain.";
    private static final String DTO_PACKAGE = "cz.zcu.kiv.vps.model.dto.";

    /**
     * Method converts domain class to data transfer class.
     * @param domain class represents domain class
     * @return data transfer class
     */
    public static Class getDTOClassFromDomainClass(Class domain) {
        try {
            return Class.forName(DTO_PACKAGE+domain.getSimpleName()+"DTO");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Method converts data transfer class to domain class.
     * @param dto class represents data transfer class
     * @return domain class
     */
    public static Class getDomainClassFromDTOClass(Class dto) {
        String dtoName = dto.getSimpleName();
        String domainName = dtoName.substring(0, dtoName.length()-3);
        try {
            return Class.forName(DOMAIN_PACKAGE+domainName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
