package org.geektimes.configuration.micriprofile.config.converter;

import java.net.URI;

/**
 * {@link URI} converter
 */
public class URIConverter extends AbstractConverter<URI>{

    @Override
    protected URI deConvert(String value) throws Throwable {
        return URI.create(value);
    }
}
