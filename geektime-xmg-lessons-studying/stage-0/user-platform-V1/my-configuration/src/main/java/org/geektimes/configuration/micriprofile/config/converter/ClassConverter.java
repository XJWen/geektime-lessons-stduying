package org.geektimes.configuration.micriprofile.config.converter;

/**
 * {@link ClassLoader}{@link }
 * */
public class ClassConverter extends AbstractConverter<Class>{

    private final ClassLoader classLoader;

    public ClassConverter(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassConverter(){this(Thread.currentThread().getContextClassLoader());}

    @Override
    public Class convert(String value){
        return super.convert(value);
    }

    @Override
    protected Class deConvert(String value) throws Throwable {
        return classLoader.loadClass(value);
    }
}
