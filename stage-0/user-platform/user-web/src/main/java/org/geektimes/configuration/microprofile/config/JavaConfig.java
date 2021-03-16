package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.*;

public class JavaConfig implements Config {

    /**
     * 内部可变的集合，不要直接暴露在外面
     */
    private List<ConfigSource> configSourceList = new LinkedList<>();

    public JavaConfig(){
        ClassLoader classLoader = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class,classLoader);
        //lambda
        serviceLoader.forEach(configSourceList::add);
        //通过比较进行排序
        configSourceList.sort(configSourceComparator);
    }

    /**
     * 比较方法
     */
    public static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
        @Override
        public int compare(ConfigSource o1, ConfigSource o2) {
            return Integer.compare(o1.getOrdinal(),o2.getOrdinal());
        }
    };

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        //String转成指定类型
        return null;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName,propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    protected String getPropertyValue(String propertyName){
        String propertyValue = null;
        for (ConfigSource configSource:configSourceList){
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue!=null){
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        //
        return Collections.unmodifiableList(configSourceList);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> propertyType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> propertyType) {
        return null;
    }
}
