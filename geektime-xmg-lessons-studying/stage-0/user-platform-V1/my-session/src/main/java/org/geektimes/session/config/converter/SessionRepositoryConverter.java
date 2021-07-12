package org.geektimes.session.config.converter;

import org.geektimes.configuration.micriprofile.config.converter.AbstractConverter;
import org.geektimes.configuration.micriprofile.config.converter.ClassConverter;
import org.geektimes.session.SessionRepository;

import java.lang.reflect.Constructor;

/**
 * {@link SessionRepository} {@link org.eclipse.microprofile.config.spi.Converter}
 *
 */
public class SessionRepositoryConverter extends AbstractConverter<SessionRepository> {

    private final ClassLoader classLoader;

    private final ClassConverter classConverter;

    public SessionRepositoryConverter(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.classConverter = new ClassConverter(classLoader);
    }

    @Override
    protected SessionRepository deConvert(String value) throws Throwable {
        Class repositoryClass = classConverter.convert(value);
        Constructor constructor = null;
        Object[] arguments;
        try {
            // try to Constructor with ClassLoader typed argument
            constructor = repositoryClass.getConstructor(ClassLoader.class);
            arguments = new Object[]{classLoader};
        } catch (Throwable ignored) {
            constructor = repositoryClass.getConstructor();
            arguments = new Object[0];
        }

        return (SessionRepository) constructor.newInstance(arguments);
    }
}
