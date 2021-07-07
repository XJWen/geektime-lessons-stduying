package org.geektimes.configuration.micriprofile.config.converter;

import java.math.BigInteger;

public class BigIntegerConverter extends AbstractConverter<BigInteger>{
    @Override
    protected BigInteger deConvert(String value) throws Throwable {
        return new BigInteger(value);
    }
}
