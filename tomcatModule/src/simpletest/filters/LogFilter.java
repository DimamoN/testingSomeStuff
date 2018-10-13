package simpletest.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * It's a filter of incoming requests, we can make filter chains
 */
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        String justString = filterConfig.getInitParameter("justString");
        System.out.println("init simpletest.filters.LogFilter, param from web.xml = " + justString);
    }

    @Override
    public void destroy() {
        System.out.println("simpletest.filters.LogFilter destroy!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String servletPath = req.getServletPath();

        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
                + ", URL =" + req.getRequestURL());

        // Разрешить request продвигаться дальше. (Перейти данный Filter).
        chain.doFilter(request, response);
    }

}
