package org.geektimes.configuration.micriprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

public class ObjectConverter implements Converter<Object> {


    @Override
    public Object convert(String value) throws IllegalArgumentException, NullPointerException {
        return value;
    }

}
