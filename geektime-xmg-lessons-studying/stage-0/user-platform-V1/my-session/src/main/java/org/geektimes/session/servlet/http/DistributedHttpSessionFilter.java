package org.geektimes.session.servlet.http;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.geektimes.configuration.micriprofile.config.source.servlet.FilterConfigSource;
import org.geektimes.session.SessionRepository;
import org.geektimes.session.config.DefaultSessionConfigSource;
import org.geektimes.session.config.converter.SessionRepositoryConverter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.eclipse.microprofile.config.spi.ConfigProviderResolver.instance;

/**
 * {@link HttpSession} Filter based on the distributed cache.
 */
public class DistributedHttpSessionFilter implements Filter {

    public static final String SESSION_REPOSITORY_CLASS_PROPERTY_NAME = "session.repository.class";

    private ClassLoader classLoader;

    private Config config;

    private SessionRepository sessionRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.classLoader = filterConfig.getServletContext().getClassLoader();
        initConfig(filterConfig, classLoader);
        initSessionRepository(config, classLoader);
    }

    protected void initConfig(FilterConfig filterConfig, ClassLoader classLoader) {
        ConfigProviderResolver resolver = ConfigProviderResolver.instance();
        ConfigBuilder configBuilder = resolver.getBuilder();
        configBuilder.forClassLoader(classLoader);
        configBuilder.addDefaultSources();
        configBuilder.addDiscoveredSources();
        configBuilder.addDiscoveredConverters();
        // Add the customized Converter(s)
        configBuilder.withConverters(new SessionRepositoryConverter(classLoader));
        // Add the customized ConfigSources
        configBuilder.withSources(new FilterConfigSource(filterConfig),
                new DefaultSessionConfigSource(classLoader));
        this.config = configBuilder.build();

        ConfigProviderResolver configProviderResolver = instance();
        // register Config
        configProviderResolver.registerConfig(config, classLoader);
    }

    protected void initSessionRepository(Config config, ClassLoader classLoader) {
        this.sessionRepository = config.getValue(SESSION_REPOSITORY_CLASS_PROPERTY_NAME, SessionRepository.class);
        this.sessionRepository.initialize();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) { // Non-HTTP Servlet
            chain.doFilter(request, response);
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Throwable error = null;
        try {
            beforeFilter(httpRequest, httpResponse);
            filter(httpRequest, httpResponse, chain);
        } catch (Throwable e) {
            error = e;
        } finally {
            afterFilter(httpRequest, httpResponse, error);
        }
    }

    protected void beforeFilter(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void filter(HttpServletRequest request, HttpServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        DistributedServletRequestWrapper requestWrapper = new DistributedServletRequestWrapper(request, sessionRepository);
        DistributedServletResponseWrapper responseWrapper = new DistributedServletResponseWrapper(response);
        chain.doFilter(requestWrapper, responseWrapper);
    }

    protected void afterFilter(HttpServletRequest request, HttpServletResponse response, Throwable error) {
        DistributedHttpSession session = DistributedHttpSession.get(request);
        if (session != null) {
            session.commitSessionInfo();
        }
    }

    @Override
    public void destroy() {
        destroyConfig();
        destroySessionRepository();
    }

    private void destroyConfig() {
        ConfigProviderResolver.instance().releaseConfig(config);
    }

    private void destroySessionRepository() {
        this.sessionRepository.destroy();
    }

}
