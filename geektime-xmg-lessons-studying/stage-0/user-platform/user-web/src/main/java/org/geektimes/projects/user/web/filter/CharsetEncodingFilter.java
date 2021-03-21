package org.geektimes.projects.user.web.filter;

import lombok.SneakyThrows;
import org.geektimes.management.ConfigSourceContext;
import org.geektimes.management.ConfigSourceContextMBean;
import org.geektimes.management.UserManager;
import org.geektimes.management.UserManagerMBean;
import org.geektimes.projects.user.domain.User;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.prefs.BackingStoreException;

/**
 * 字符编码 Filter
 */
public class CharsetEncodingFilter implements Filter {

    private String encoding = null;

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.encoding = filterConfig.getInitParameter("encoding");
        this.servletContext = filterConfig.getServletContext();

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //继承HttpServletRequest
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpRequest.setCharacterEncoding(encoding);
            httpResponse.setCharacterEncoding(encoding);
            servletContext.log("当前编码已设置为：" + encoding);
            // CharsetEncodingFilter -> FrontControllerServlet -> forward -> index.jsp
        }


        // 执行过滤链
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
