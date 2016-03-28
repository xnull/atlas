package bynull.poi.config;

import bynull.poi.rest.PoiResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Configure Jersey server and Swagger
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(PoiResource.class);
        register(ApiListingResource.class);
        register(ObjectMapperContextResolver.class);

        BeanConfig config = new BeanConfig();
        config.setTitle("Swagger sample app");
        config.setVersion("1.0.0");
        config.setResourcePackage("bynull.poi.rest");
        config.setDescription("POI service");
        config.setHost("localhost:8080");
        config.setBasePath("/");
        config.setSchemes(new String[]{"http"});
        config.setScan(true);
    }

    /**
     * Custom json mapper: only properties with non-null values are to be included.
     */
    @Provider
    public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

        private final ObjectMapper mapper;

        public ObjectMapperContextResolver() {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }
    }
}