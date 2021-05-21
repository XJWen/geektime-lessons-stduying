package org.geektimes.configuration.micriprofile.config.source.servlet;

import org.geektimes.configuration.micriprofile.config.source.MapBasedConfigSource;

import javax.servlet.FilterConfig;
import java.util.Enumeration;
import java.util.Map;

import static java.lang.String.format;

public class FilterConfigSource extends MapBasedConfigSource {

    private final FilterConfig filterConfig;

    protected FilterConfigSource(String name, int ordinal, FilterConfig filterConfig) {
        super(format("Filter[name:%s] Init Parameters", filterConfig.getFilterName()), 550);
        this.filterConfig = filterConfig;
    }

    /**
     * 准备配置数据
     *
     * @param configData
     * @throws Throwable
     */
    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        Enumeration<String> parameterNames = filterConfig.getInitParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            configData.put(parameterName,filterConfig.getInitParameter(parameterName));
        }
    }
}
