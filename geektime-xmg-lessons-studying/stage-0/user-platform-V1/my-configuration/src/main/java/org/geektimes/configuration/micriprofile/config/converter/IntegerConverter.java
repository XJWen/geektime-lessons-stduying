package org.geektimes.configuration.micriprofile.config.converter;

public class IntegerConverter extends AbstractConverter<Integer>{
    @Override
    protected Integer deConvert(String value) throws Throwable {
        return Integer.valueOf(value);
    }
}
