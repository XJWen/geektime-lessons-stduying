package org.geektimes.configuration.micriprofile.config.converter;

public class ByteConverter extends AbstractConverter<Byte>{

    @Override
    protected Byte deConvert(String value) throws Throwable {
        return Byte.valueOf(value);
    }
}
