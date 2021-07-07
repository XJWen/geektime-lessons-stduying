package org.geektimes.configuration.micriprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.micriprofile.config.converter.Converters;

public class DefaultConfigBuilder  implements ConfigBuilder {

    private final  ConfigSource configSource;

    private final Converters converters;

    public DefaultConfigBuilder(ConfigSource configSource, Converters converters) {
        this.configSource = configSource;
        this.converters = converters;
    }

    /**
     * Add the <a href="ConfigSource.html#default_config_sources"><em>default configuration sources</em></a> to the
     * configuration being built.
     *
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder addDefaultSources() {
        return null;
    }

    /**
     * Add all configuration sources which can be <a href="ConfigSource.html#discovery">discovered</a> from this
     * configuration builder's {@linkplain #forClassLoader(ClassLoader) class loader}.
     *
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder addDiscoveredSources() {
        return null;
    }

    /**
     * Add all configuration converters which can be <a href="Converter.html#discovery">discovered</a> from this
     * configuration builder's {@linkplain #forClassLoader(ClassLoader) class loader}.
     *
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder addDiscoveredConverters() {
        return null;
    }

    /**
     * Specify the class loader for which this configuration is being built.
     *
     * @param loader the class loader
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        return null;
    }

    /**
     * Add the specified {@link ConfigSource} instances to the configuration being built.
     *
     * @param sources the configuration sources
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        return null;
    }

    /**
     * Add the specified {@link Converter} instances to the configuration being built.
     * <p>
     * The implementation may use reflection to determine the target type of the converter. If the type cannot be
     * determined reflectively, this method may fail with a runtime exception.
     * <p>
     * When using lambda expressions for custom converters you should use the
     * {@link #withConverter(Class, int, Converter)} method and pass the target type explicitly, since lambda
     * expressions generally do not offer enough type information to the reflection API in order to determine the target
     * converter type.
     * <p>
     * The added converters will be given a priority of {@code 100}.
     *
     * @param converters the converters to add
     * @return this configuration builder instance
     */
    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        return null;
    }

    /**
     * Add the specified {@link Converter} instance for the given type to the configuration being built.
     * <p>
     * This method does not rely on reflection to determine the target type of the converter; therefore, lambda
     * expressions may be used for the converter instance.
     * <p>
     * The priority value of custom converters defaults to {@code 100} if not specified.
     *
     * @param type      the class of the type to convert
     * @param priority  the priority of the converter
     * @param converter the converter (can not be {@code null})
     * @return this configuration builder instance
     */
    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        return null;
    }

    /**
     * Build a new {@link Config} instance based on this builder instance.
     *
     * @return the new configuration object
     */
    @Override
    public Config build() {
        return null;
    }
}
