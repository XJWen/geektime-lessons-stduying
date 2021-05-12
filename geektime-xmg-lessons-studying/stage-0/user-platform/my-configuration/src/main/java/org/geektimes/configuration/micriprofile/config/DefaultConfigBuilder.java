package org.geektimes.configuration.micriprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.micriprofile.config.converter.MyConverters;
import org.geektimes.configuration.micriprofile.config.source.ConfigSources;


/**
 * @see {@link ConfigSources}
 */
public class DefaultConfigBuilder implements ConfigBuilder {

    private final ConfigSources configSources;

    private final MyConverters<O> myConverters;

    public DefaultConfigBuilder(ClassLoader classLoader) {
        this.configSources = new ConfigSources(classLoader);
        myConverters = new MyConverters<O>(classLoader);
    }

    @Override
    public ConfigBuilder addDefaultSources() {
        configSources.addDefaultSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredSources() {
        configSources.addDiscoveredSources();
        return this;
    }

    @Override
    public ConfigBuilder addDiscoveredConverters() {
        myConverters.addDiscoveredConverters();
        return this;
    }

    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        configSources.setClassLoader(loader);
        myConverters.setClassLoader(loader);
        return this;
    }

    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        configSources.addConfigSources(sources);
        return this;
    }

    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        this.myConverters.addConverters(converters);
        return this;
    }

    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        this.myConverters.addConverter(converter, priority, type);
        return this;
    }

    @Override
    public Config build() {
        Config config = new DefaultConfig(configSources, myConverters);
        return config;
    }
}
