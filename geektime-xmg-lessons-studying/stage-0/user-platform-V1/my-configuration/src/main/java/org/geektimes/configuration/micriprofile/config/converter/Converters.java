package org.geektimes.configuration.micriprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.ServiceLoader.load;

public class Converters  implements Iterable<Converter>{

    public static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter>>   typedConverters = new HashMap<>();

    private ClassLoader classLoader;

    private boolean addedDiscoveredConfigSources = false;

    public  Converters(ClassLoader classLoader) { this.classLoader = classLoader; }

    public  Converters() { this(Thread.currentThread().getContextClassLoader());}

    public  void  addDiscoveredConverters() throws IOException {
        if (addedDiscoveredConfigSources){
            return;
        }
        addConverters(load(Converter.class,classLoader));
        addedDiscoveredConfigSources = true;
    }

    public void addConverters(Iterable<Converter> converters){ converters.forEach(this::addConverter);}

    public void addConverter(Converter converter){  addConverter(converter,DEFAULT_PRIORITY); }

    private void addConverter(Converter converter,int priority) {
        Class<?> convertedType = resolveConvertedType(converter);
    }

    public Class<?> resolveConvertedType(Converter<?> converter) {
        assertConverter(converter);
        Class<?> convertedType = null;
        Class<?> convertedClass = converter.getClass();

        while (convertedClass != null){
            convertedType = resolveConvertedType(convertedClass);
            if (convertedType!=null){break;}

            Type superType = convertedClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType){
                convertedType = resolveConvertedType(superType);
            }

            if (convertedType!=null){break;}
            // recursively
            convertedClass = convertedClass.getSuperclass();
        }
        return convertedType;
    }

    private void assertConverter(Converter<?> converter) {
        Class<?> converterClass = converter.getClass();
        if (converterClass.isInterface()){
            throw new IllegalArgumentException("The implementation class of Converter must not be an interface!");
        }
        if (Modifier.isAbstract(converterClass.getModifiers())){
            throw new IllegalArgumentException("The implementation class of Converter must not be abstract!");
        }
    }

    private Class<?> resolveConvertedType(Class<?> converterClass) {
        Class<?> convertedType = null;

        for (Type superInterface : converterClass.getGenericInterfaces()){
            convertedType = resolveConvertedType(superInterface);
            if (convertedType != null){
                break;
            }
        }
        return convertedType;
    }

    private Class<?> resolveConvertedType(Type type){
        Class<?> convertedType = null;
        if (type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() instanceof  Class){
                Class<?> rawType = (Class) parameterizedType.getRawType();
                if (Converter.class.isAssignableFrom(rawType)){
                    Type[] arguments = parameterizedType.getActualTypeArguments();
                    if (arguments.length==1 && arguments[0] instanceof  Class){
                        convertedType = (Class) arguments[0];
                    }
                }
            }
        }
        return convertedType;
    }

    public void addConverters(Converter... converters){
         addConverters(Arrays.asList(converters));
    }

    public List<Converter> getConverters(Class<?> convertedType){
        PriorityQueue<PrioritizedConverter> prioritizedConverters = typedConverters.get(convertedType);
        if (prioritizedConverters == null|| prioritizedConverters.isEmpty()){
            return Collections.emptyList();
        }
        List<Converter> converters = new LinkedList<>();
        for (PrioritizedConverter prioritizedConverter : prioritizedConverters){
            converters.add(prioritizedConverter.getConverter());
        }
        return converters;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Converter> iterator() {
        List<Converter> allConverters = new LinkedList<>();
        for (PriorityQueue<PrioritizedConverter> converters:typedConverters.values()){
            for (PrioritizedConverter converter : converters){
                allConverters .add(converter.getConverter());
            }
        }
        return allConverters.iterator();
    }
}
