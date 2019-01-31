package cz.zcu.kiv.vps.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Lukas Cerny
 *
 * Class decoreates output data in JSON format.
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}