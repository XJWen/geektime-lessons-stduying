package org.geektimes.configuration.micriprofile.config.converter;

public class DoubleConverter extends AbstractConverter<Double> {
    @Override
    protected Double deConvert(String value) throws Throwable {
        return Double.valueOf(value);
    }
}
