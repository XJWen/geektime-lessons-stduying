package org.geektimes.configuration.micriprofile.config.converter;

public class BooleanConverter extends AbstractConverter<Boolean>{
    @Override
    protected Boolean deConvert(String value) throws Throwable {
        return Boolean.parseBoolean(value);
    }
}
