package org.geektime.cache.management;

import javax.cache.configuration.CompleteConfiguration;
import javax.cache.management.CacheMXBean;

/**
 * {@link CacheMXBean} Adapter based on
 * {@link CompleteConfiguration}
 *
 * @see CacheMXBean
 * @see CompleteConfiguratio
 *
 */
public class CacheMXBeanAdapter implements CacheMXBean {

    private final CompleteConfiguration<?,?> configuration;

    public CacheMXBeanAdapter(CompleteConfiguration<?, ?> configuration)throws NullPointerException {
        this.configuration = configuration;
    }

    @Override
    public String getKeyType() {
        return null;
    }

    @Override
    public String getValueType() {
        return null;
    }

    @Override
    public boolean isReadThrough() {
        return false;
    }

    @Override
    public boolean isWriteThrough() {
        return false;
    }

    @Override
    public boolean isStoreByValue() {
        return false;
    }

    @Override
    public boolean isStatisticsEnabled() {
        return false;
    }

    @Override
    public boolean isManagementEnabled() {
        return false;
    }
}
