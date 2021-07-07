package org.geektimes.configuration.micriprofile.config.converter;

public class FloatConverter extends AbstractConverter<Float>{
    @Override
    protected Float deConvert(String value) throws Throwable {
        return Float.valueOf(value);
    }
}
