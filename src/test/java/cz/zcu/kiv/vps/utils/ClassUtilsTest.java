package cz.zcu.kiv.vps.utils;

import cz.zcu.kiv.vps.model.domain.Model;
import cz.zcu.kiv.vps.model.dto.ModelDTO;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class ClassUtilsTest {

    @Test
    public void getDTOClassFromDomainClass() throws Exception {
        assertEquals(ModelDTO.class, ClassUtils.getDTOClassFromDomainClass(Model.class));
    }

    @Test
    public void getDomainClassFromDTOClass() throws Exception {
        assertEquals(Model.class, ClassUtils.getDomainClassFromDTOClass(ModelDTO.class));
    }
}