package org.geektimes.configuration.micriprofile.config.converter;

import java.net.URI;

/**
 * {@link URI} Converter
 *
 * */
public class URIConverter extends AbstractConverter<URI> {

    @Override
    protected URI doConvert(String value) throws Throwable {
        return URI.create(value);
    }
}
