package simpletest.filters;

import simpletest.servlets.AnnotatedServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * It's a annotated filter of incoming requests, we can make filter chains
 * like a {@link AnnotatedServlet}
 */
@WebFilter(filterName = "simpletest.filters.AnnotatedLogFilter",
        urlPatterns = {"/hello", "*.jsp"},
        initParams = {
        @WebInitParam(name = "justAnnotatedString", value = "it's i am!")
})
public class AnnotatedLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        String justString = filterConfig.getInitParameter("justAnnotatedString");
        System.out.println("init simpletest.filters.AnnotatedLogFilter, param from @WebInitParam = " + justString);
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

        System.out.println("#INFO " + new Date() + " - (Annotated) ServletPath :" + servletPath //
                + ", URL =" + req.getRequestURL());

        // Разрешить request продвигаться дальше. (Перейти данный Filter).
        chain.doFilter(request, response);
    }

}
