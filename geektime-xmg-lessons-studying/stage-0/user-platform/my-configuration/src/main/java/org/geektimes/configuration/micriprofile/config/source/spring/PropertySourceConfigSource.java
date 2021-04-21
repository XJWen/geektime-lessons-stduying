package org.geektimes.configuration.micriprofile.config.source.spring;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class PropertySourceConfigSource implements ConfigSource {

    private final EnumerablePropertySource propertySource;

    private int ordinal;

    public PropertySourceConfigSource(EnumerablePropertySource propertySource) {
        this.propertySource = propertySource;
        if (propertySource instanceof Ordered) {
            this.setOrdinal(((Ordered) propertySource).getOrder());
        } else {
            this.setOrdinal(ConfigSource.super.getOrdinal());
        }
    }

    public PropertySourceConfigSource(EnumerablePropertySource propertySource, int ordinal) {
        this.propertySource = propertySource;
        this.ordinal = ordinal;
    }


    /**
     * Return the properties in this configuration source as a map.
     *
     * @return a map containing properties of this configuration source
     */
    @Override
    public Map<String, String> getProperties() {
        return ConfigSource.super.getProperties();
    }

    /**
     * Gets all property names known to this configuration source, potentially without evaluating the values. The
     * returned property names may be a subset of the names of the total set of retrievable properties in this config
     * source.
     * <p>
     * The returned set is not required to allow concurrent or multi-threaded iteration; however, if the same set is
     * returned by multiple calls to this method, then the implementation must support concurrent and multi-threaded
     * iteration of that set.
     * <p>
     * The set of keys returned <em>may</em> be a point-in-time snapshot, or <em>may</em> change over time (even during
     * active iteration) to reflect dynamic changes to the available set of keys.
     *
     * @return a set of property names that are known to this configuration source
     */
    @Override
    public Set<String> getPropertyNames() {
        String[] propertyNames = propertySource.getPropertyNames();
        Set<String> propertyNamesSet = new LinkedHashSet<>();
        for (String propertyName : propertyNames){
            propertyNamesSet.add(propertyName);
        }
        return Collections.unmodifiableSet(propertyNamesSet);
    }

    /**
     * Return the ordinal priority value of this configuration source.
     * <p>
     * If a property is specified in multiple config sources, the value in the config source with the highest ordinal
     * takes precedence. For configuration sources with the same ordinal value, the configuration source name will be
     * used for sorting according to string sorting criteria.
     * <p>
     * Note that this method is only evaluated during the construction of the configuration, and does not affect the
     * ordering of configuration sources within a configuration after that time.
     * <p>
     * The ordinal values for the default configuration sources can be found
     * <a href="#default_config_sources">above</a>.
     * <p>
     * Any configuration source which is a part of an application will typically use an ordinal between 0 and 200.
     * Configuration sources provided by the container or 'environment' typically use an ordinal higher than 200. A
     * framework which intends have values overridden by the application will use ordinals between 0 and 100.
     * <p>
     * The default implementation of this method looks for a configuration property named "{@link #CONFIG_ORDINAL
     * config_ordinal}" to determine the ordinal value for this configuration source. If the property is not found, then
     * the {@linkplain #DEFAULT_ORDINAL default ordinal value} is used.
     * <p>
     * This method may be overridden by configuration source implementations to provide a different behavior.
     *
     * @return the ordinal value
     */
    @Override
    public int getOrdinal() {
        return this.ordinal;
    }

    /**
     * Return the value for the specified property in this configuration source.
     *
     * @param propertyName the property name
     * @return the property value, or {@code null} if the property is not present
     */
    @Override
    public String getValue(String propertyName) {
        Object propertyValue = propertySource.getProperty(propertyName);
        return propertyValue instanceof String?String.valueOf(propertyValue):null;
    }

    /**
     * The name of the configuration source. The name might be used for logging or for analysis of configured values,
     * and also may be used in {@linkplain #getOrdinal() ordering decisions}.
     * <p>
     * An example of a configuration source name is "{@code property-file mylocation/myprops.properties}".
     *
     * @return the name of the configuration source
     */
    @Override
    public String getName() {
        return propertySource.getName();
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
