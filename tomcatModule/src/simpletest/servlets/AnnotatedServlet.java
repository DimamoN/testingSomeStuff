package simpletest.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * From Servlet Api 2.5 we can simply define servlets by annotation
 */
@WebServlet(name = "Annotated", displayName = "Annotation Servlet", value = "/annotated")
public class AnnotatedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Hello from annotated servlet!");
    }
}
