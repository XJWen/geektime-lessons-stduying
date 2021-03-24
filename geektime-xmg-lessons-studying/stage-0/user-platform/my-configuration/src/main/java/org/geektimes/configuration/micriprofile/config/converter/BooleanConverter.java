package org.geektimes.configuration.micriprofile.config.converter;

public class BooleanConverter extends AbstractConverter<Boolean> {

    @Override
    protected Boolean doConvert(String value) {
        return Boolean.parseBoolean(value);
    }
}
