package org.geektimes.configuration.micriprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;



public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(String value) {
        if (value == null) {
            throw new NullPointerException("The value must not be null!");
        }
        T converedValue = null;
        try{
            converedValue = doConvert(value);
        }catch (Throwable e){
            throw new IllegalArgumentException("The value can't be converted.", e);
        }
        return converedValue;
    }

    protected abstract T doConvert(String value) throws Throwable;
}
