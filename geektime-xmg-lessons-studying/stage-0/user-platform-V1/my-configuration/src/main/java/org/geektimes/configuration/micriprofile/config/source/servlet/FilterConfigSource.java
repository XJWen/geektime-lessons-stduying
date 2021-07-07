package org.geektimes.configuration.micriprofile.config.source.servlet;

import javax.servlet.FilterConfig;
import java.util.Enumeration;
import java.util.Map;

import static java.lang.String.format;

import org.geektimes.configuration.micriprofile.config.source.MapBasedConfigSource;

public class FilterConfigSource extends MapBasedConfigSource{

    private final FilterConfig filterConfig;

    public FilterConfigSource(FilterConfig filterConfig) {
        super(format("Filter[name:%s] Init Parameters", filterConfig.getFilterName()), 550);
        this.filterConfig = filterConfig;
    }

    protected  void prepareConfigData(Map configData)throws Throwable{
        Enumeration<String> parameterNames = filterConfig.getInitParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            configData.put(parameterName,filterConfig.getInitParameter(parameterName));
        }
    }

}
