package org.geektimes.configuration.micriprofile.config.converter;

/**
 * {@link Class}{@link MyConverters}
 *
 * @author WenXJ
 * @since 0.0.1
 * @date 2021-05-12
 * */
public class ClassConverter extends AbstractConverter{

    private final ClassLoader classLoader;

    public ClassConverter(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassConverter(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Object convert(String value) {
        return super.convert(value);
    }

    @Override
    protected Object doConvert(String value) throws Throwable {
        return classLoader.loadClass(value);
    }
}
