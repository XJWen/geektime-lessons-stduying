package org.geektimes.configuration.micriprofile.config.converter;

public class ByteConverter extends AbstractConverter<Byte> {

    @Override
    protected Byte doConvert(String value) {
        return Byte.valueOf(value);
    }
}
