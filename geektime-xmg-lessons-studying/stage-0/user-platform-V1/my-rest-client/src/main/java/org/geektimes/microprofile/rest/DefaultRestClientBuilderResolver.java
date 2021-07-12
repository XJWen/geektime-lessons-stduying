package org.geektimes.microprofile.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.spi.RestClientBuilderResolver;

/**
 * Default {@link RestClientBuilderResolver} implementation
 *
 */
public class DefaultRestClientBuilderResolver extends RestClientBuilderResolver {

    @Override
    public RestClientBuilder newBuilder() {
        return new DefaultRestClientBuilder(getClass().getClassLoader());
    }

}
