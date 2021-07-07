package org.geektimes.configuration.micriprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.load;

public class MyConverters implements Iterable<Converter> {

    public static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter>> typedConverters = new HashMap<>();

    private ClassLoader classLoader;

    private boolean addedDiscoveredConverters = false;

    public MyConverters(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public MyConverters() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public void addDiscoveredConverters() throws IOException {
        if (addedDiscoveredConverters){
            return;
        }
        addConverters((Iterable<Converter>) load(Converter.class, classLoader));
        addedDiscoveredConverters = true;
    }

    public void addConverters(Iterable<Converter> converters){converters.forEach(this::addConverters);}

    private void addConverters(Converter converter) {
        addConverter(converter,DEFAULT_PRIORITY);
    }

    private void addConverter(Converter converter, int defaultPriority) {
        Class<?> convertedType = resolveConvertedType(converter);
        addConverter(converter,defaultPriority,convertedType);
    }

    public void addConverter(Converter converter, int priority, Class<?> convertedType) {
        PriorityQueue priorityQueue = typedConverters.computeIfAbsent(convertedType,ct->new PriorityQueue<>());
        priorityQueue.offer(new PrioritizedConverter(converter,priority));
    }

    private Class<?> resolveConvertedType(Converter<?> converter) {
        assertConverter(converter);
        Class<?> convertedType = null;
        Class<?> converterClass = converter.getClass();
        while (converterClass!=null) {
            convertedType = resolveConvertedType(converterClass);
            if (convertedType != null) {
                break;
            }

            Type superType = converterClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType){
                convertedType = resolveConvertedType(superType);
            }

            if (convertedType != null){
                break;
            }
            // recursively
            converterClass = converterClass.getSuperclass();
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
        for(Type superInterface : converterClass.getGenericInterfaces()){
            convertedType = resolveConvertedType(superInterface);
            if (convertedType != null){
                break;
            }
        }
        return convertedType;
    }

    private Class<?> resolveConvertedType(Type type) {
        Class<?> convertedType = null;
        if (type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType) type;
            if (pType.getRawType() instanceof Class){
                Class<?> rawType = (Class) pType.getRawType();
                if (Converter.class.isAssignableFrom(rawType)){
                    Type[] arguments = pType.getActualTypeArguments();
                    if (arguments.length==1&&arguments[0] instanceof Class){
                        convertedType = (Class) arguments[0];
                    }
                }
             }
        }
        return convertedType;
    }

    public void addConverters(Converter... converters){addConverters(Arrays.asList(converters));}

    public List<Converter> getConverters(Class<?> convertedType) {
        PriorityQueue<PrioritizedConverter> prioritizedConverters = typedConverters.get(convertedType);
        if (prioritizedConverters == null||prioritizedConverters.isEmpty()){
            return Collections.emptyList();
        }
        List<Converter> converters = new LinkedList<>();
        for(PrioritizedConverter prioritizedConverter:prioritizedConverters){
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
        for (PriorityQueue<PrioritizedConverter>converters : typedConverters.values()){
            for(PrioritizedConverter converter:converters){
                allConverters.add(converter.getConverter());
            }
        }
        return allConverters.iterator();
    }
}
