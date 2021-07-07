package org.geektimes.configuration.micriprofile.config.converter;

public class LongConverter extends AbstractConverter<Long>{
    @Override
    protected Long deConvert(String value) throws Throwable {
        return Long.valueOf(value);
    }
}
