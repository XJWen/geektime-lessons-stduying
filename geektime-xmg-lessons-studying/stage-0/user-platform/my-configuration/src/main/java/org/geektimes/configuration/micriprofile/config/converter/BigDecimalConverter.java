package org.geektimes.configuration.micriprofile.config.converter;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractConverter<BigDecimal> {

    @Override
    protected BigDecimal doConvert(String value) {
        return new BigDecimal(value);
    }
}
