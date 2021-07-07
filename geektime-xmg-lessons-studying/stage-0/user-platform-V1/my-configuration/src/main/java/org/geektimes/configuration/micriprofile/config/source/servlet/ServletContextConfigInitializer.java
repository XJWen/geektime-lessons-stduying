package org.geektimes.configuration.micriprofile.config.source.servlet;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextConfigInitializer implements ServletContextListener {



    /**
     * Receives notification that the web application initialization
     * process is starting.
     *
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param servletContextEvent the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ServletContextConfigSource servletContextConfigSource = new ServletContextConfigSource(servletContext);
        //获取当前ClassLoader
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        ConfigBuilder configBuilder = configProviderResolver.getBuilder();
         //配置 ClassLoader --> 默认配置源（内建的，静态的） --> 通过发现配置源（动态的） --> 增加扩展配置源（基于 Servlet 引擎）
        configBuilder.forClassLoader(classLoader).addDefaultSources().addDiscoveredConverters().withSources(servletContextConfigSource);
        //获取Config
        Config config = configBuilder.build();
        //注册Config 关联当前ClassLoader
        configProviderResolver.registerConfig(config, classLoader);
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     *
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param servletContextEvent the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config config = configProviderResolver.getConfig(classLoader);
    }
}
