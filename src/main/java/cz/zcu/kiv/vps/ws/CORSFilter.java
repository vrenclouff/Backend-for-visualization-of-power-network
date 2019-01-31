package cz.zcu.kiv.vps.ws;

import cz.zcu.kiv.vps.core.config.ApplicationConfig;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Lukas Cerny.
 *
 * Filter which enables header for request and response.
 */

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Inject
    private ApplicationConfig config;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        String authorizationHeader = config.getAuthorizationHeaderName();
        String authenticationHeader = config.getAuthenticationHeaderName();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST");
        headers.add("Access-Control-Allow-Headers", authenticationHeader+", "+authorizationHeader+", "+"X-Requested-With, Content-Type");
        headers.add("Access-Control-Allow-Credential", "true");
   }
}
