package org.geektimes.configuration.micriprofile.config.source.spring;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class PropertySourceConfigSource implements ConfigSource {

    private final EnumerablePropertySource propertySource;

    private  int ordinal;

    public PropertySourceConfigSource(EnumerablePropertySource propertySource){
        this.propertySource = propertySource;
        if (propertySource instanceof Ordered){
            this.setOrdinal(((Ordered)propertySource).getOrder());
        }else{
            this.setOrdinal(ConfigSource.super.getOrdinal());
        }
    }

    @Override
    public Map<String,String> getProperties(){ return  ConfigSource.super.getProperties(); }

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
        for(String propertyName : propertyNames){
            propertyNamesSet.add(propertyName);
        }
        return unmodifiableSet(propertyNamesSet);
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
        return propertyValue instanceof String ? String.valueOf(propertyValue):null;
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

    @Override
    public int getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

}
