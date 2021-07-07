package org.geektimes.configuration.micriprofile.config.converter;

public class ShortConverter extends AbstractConverter<Short>{
    @Override
    protected Short deConvert(String value) throws Throwable {
        return Short.valueOf(value);
    }
}
