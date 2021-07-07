package org.geektimes.configuration.micriprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class DefaultConfig implements Config {

//    private final ConfigSource configSource;


    /**
     * Return the resolved property value with the specified type for the specified property name from the underlying
     * {@linkplain ConfigSource configuration sources}.
     * <p>
     * The configuration value is not guaranteed to be cached by the implementation, and may be expensive to compute;
     * therefore, if the returned value is intended to be frequently used, callers should consider storing rather than
     * recomputing it.
     * <p>
     * The result of this method is identical to the result of calling
     * {@code getOptionalValue(propertyName, propertyType).get()}. In particular, If the given property name or the
     * value element of this property does not exist, the {@link NoSuchElementException} is thrown. This
     * method never returns {@code null}.
     *
     * @param propertyName The configuration property name
     * @param propertyType The type into which the resolved property value should get converted
     * @return the resolved property value as an instance of the requested type (not {@code null})
     * @throws IllegalArgumentException if the property cannot be converted to the specified type
     * @throws NoSuchElementException   if the property is not defined or is defined as an empty string or the converter returns {@code null}
     */
    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        return null;
    }

    /**
     * Return the {@link ConfigValue} for the specified property name from the underlying {@linkplain ConfigSource
     * configuration source}. The lookup of the configuration is performed immediately, meaning that calls to
     * {@link ConfigValue} will always yield the same results.
     * <p>
     * The configuration value is not guaranteed to be cached by the implementation, and may be expensive to compute;
     * therefore, if the returned value is intended to be frequently used, callers should consider storing rather than
     * recomputing it.
     * <p>
     * A {@link ConfigValue} is always returned even if a property name cannot be found. In this case, every method in
     * {@link ConfigValue} returns {@code null} except for {@link ConfigValue#getName()}, which includes the original
     * property name being looked up.
     *
     * @param propertyName The configuration property name
     * @return the resolved property value as a {@link ConfigValue}
     */
    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    /**
     * Return the resolved property value with the specified type for the specified property name from the underlying
     * {@linkplain ConfigSource configuration sources}.
     * <p>
     * The configuration value is not guaranteed to be cached by the implementation, and may be expensive to compute;
     * therefore, if the returned value is intended to be frequently used, callers should consider storing rather than
     * recomputing it.
     * <p>
     * If this method is used very often then consider to locally store the configured value.
     *
     * @param propertyName The configuration property name
     * @param propertyType The type into which the resolved property value should be converted
     * @return The resolved property value as an {@code Optional} wrapping the requested type
     * @throws IllegalArgumentException if the property cannot be converted to the specified type
     */
    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        return Optional.empty();
    }

    /**
     * Returns a sequence of configuration property names. The order of the returned property names is unspecified.
     * <p>
     * The returned property names are unique; that is, if a name is returned once by a given iteration, it will not be
     * returned again during that same iteration.
     * <p>
     * There is no guarantee about the completeness or currency of the names returned, nor is there any guarantee that a
     * name that is returned by the iterator will resolve to a non-empty value or be found in any configuration source
     * associated with the configuration; for example, it is allowed for this method to return an empty set always.
     * However, the implementation <em>should</em> return a set of names that is useful to a user that wishes to browse
     * the configuration.
     * <p>
     * It is implementation-defined whether the returned names reflect a point-in-time "snapshot" of names, or an
     * aggregation of multiple point-in-time "snapshots", or a more dynamic view of the available property names.
     * Implementations are not required to return the same sequence of names on each iteration; however, the produced
     * {@link Iterator Iterator} must adhere to the contract of that class, and must not return any more
     * elements once its {@link Iterator#hasNext() hasNext()} method returns {@code false}.
     * <p>
     * The returned instance is thread safe and may be iterated concurrently. The individual iterators are not
     * thread-safe.
     *
     * @return the names of all configured keys of the underlying configuration
     */
    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    /**
     * Return all of the currently registered {@linkplain ConfigSource configuration sources} for this configuration.
     * <p>
     * The returned sources will be sorted by descending ordinal value and name, which can be iterated in a thread-safe
     * manner. The {@link Iterable Iterable} contains a fixed number of {@linkplain ConfigSource configuration
     * sources}, determined at application start time, and the config sources themselves may be static or dynamic.
     *
     * @return the configuration sources
     */
    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return null;
    }

    /**
     * Return the {@link Converter} used by this instance to produce instances of the specified type from string values.
     *
     * @param forType the type to be produced by the converter
     * @return an {@link Optional} containing the converter, or empty if no converter is available for the specified
     * type
     */
    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.empty();
    }

    /**
     * Returns an instance of the specific class, to allow access to the provider specific API.
     * <p>
     * If the MP Config provider implementation does not support the specified class, a {@link IllegalArgumentException}
     * is thrown.
     * <p>
     * Unwrapping to the provider specific API may lead to non-portable behaviour.
     *
     * @param type Class representing the type to unwrap to
     * @return An instance of the given type
     * @throws IllegalArgumentException If the current provider does not support unwrapping to the given type
     */
    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
