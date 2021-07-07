package org.geektimes.configuration.micriprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.ServiceLoader.load;


public class ConfigSources  implements Iterable<ConfigSource> {
    /**
     * 是否添加默认配置源
     * */
    private boolean addedDefaultConfigSources;

    private boolean addedDiscoveredConfigSources;

    private  List<ConfigSource> configSources = new LinkedList<>();


    private ClassLoader classLoader;

    public ConfigSources(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public void addDefaultSources(){
        if (addedDefaultConfigSources){
            return;
        }
        addConfigSources(JavaSystemPropertiesConfigSource.class,
                OperationSystemEnvironmentVariablesConfigSource.class,
                DefaultResourceConfigSource.class
        );

        addedDefaultConfigSources = true;
    }


    public  void  addDiscoveredSources(){
        if (addedDiscoveredConfigSources){
            return;
        }

        addConfigSources(load(ConfigSource.class,classLoader));

        addedDiscoveredConfigSources = true;
    }

    public void addConfigSources(Class<? extends ConfigSource>... configSourcesClass){
        addConfigSources(
                Stream.of(configSourcesClass).map(this::newInstance).toArray(ConfigSource[]::new)
        );
    }

    public  void  addConfigSources(ConfigSource... configSources){
        addConfigSources(Arrays.asList(configSources));
    }

    public  void  addConfigSources(Iterable<ConfigSource> configSources) {
        configSources.forEach(this.configSources::add);
        Collections.sort(this.configSources, ConfigSourceOrdinalComparator.INSTANCE);
    }

    private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClass) {
        ConfigSource newInstance = null;
        try {
            newInstance = configSourceClass.newInstance();
        }catch(InstantiationException | IllegalAccessException e){
            throw new IllegalStateException(e);
        }

        return newInstance;

    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ConfigSource> iterator() {
        return configSources.iterator();
    }

    public boolean isAddedDefaultConfigSources() {
        return addedDefaultConfigSources;
    }

    public boolean isAddedDiscoveredConfigSources() {
        return addedDiscoveredConfigSources;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

}
