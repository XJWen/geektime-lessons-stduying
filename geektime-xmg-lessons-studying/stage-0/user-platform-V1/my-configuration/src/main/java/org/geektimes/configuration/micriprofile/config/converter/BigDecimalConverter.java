package org.geektimes.configuration.micriprofile.config.converter;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractConverter<BigDecimal> {
    @Override
    protected BigDecimal deConvert(String value) throws Throwable {
        return new BigDecimal(value);
    }
}
