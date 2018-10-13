package simpletest.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * It's a simple servlet, configuration is in web.xml
 */
public class HelloServlet extends HttpServlet {


    static boolean canEncodeWithGzip(HttpServletRequest req) {
        return req.getHeader("Accept-Encoding").contains("gzip");
    }

    static void encodeWithGzip(final HttpServletResponse resp,
                                              final String content) throws IOException {
        resp.setHeader("Content-Encoding", "gzip");
        PrintWriter writer = new PrintWriter(new GZIPOutputStream(resp.getOutputStream()));
        writer.println(content);
        writer.flush();
        writer.close();
    }


    void processCookies(final HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            System.out.println("Cookie name: " + cookie.getName() +
                    " value: " + cookie.getValue() +
                    " maxAge: " + cookie.getMaxAge());
        }
    }

    void addCookie(HttpServletResponse resp) {
        Cookie cookie = new Cookie("Yoga", "Pants");
        cookie.setMaxAge(10);
        cookie.setSecure(true);
        resp.addCookie(cookie);
    }

    void processSession(final HttpServletRequest req) {
        HttpSession session = req.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();

        if (attributeNames != null) {
            System.out.println("SESSION:" + session.getId());
            while (attributeNames.hasMoreElements()) {
                String attrName = attributeNames.nextElement();
                System.out.println(attrName + " : " + session.getAttribute(attrName));
            }
        }

        session.setAttribute("Data", "AXCXVXVGSDGSDGV");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init Hello servlet");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doGet");
        processCookies(req);
        addCookie(resp);

        processSession(req);

        final String responseStr = "    if (req.getHeader(\"Accept-Encoding\").contains(\"gzip\")) {\n" +
                "            resp.setHeader(\"Content-Encoding\", \"gzip\");\n" +
                "            PrintWriter writer = new PrintWriter(new GZIPOutputStream(resp.getOutputStream()));\n" +
                "            writer.println(\"Hello World!\");\n" +
                "            writer.flush();\n" +
                "            writer.close();\n" +
                "        }\uFEFF";

        if (canEncodeWithGzip(req)) {
            System.out.println("ENCODED");

            // Redirect or Refresh header example:
            //resp.sendRedirect("https://ru.cppreference.com/w/cpp/language/memory_model");
            //resp.setHeader("Refresh", "3;URL=https://ru.wikipedia.org");

            encodeWithGzip(resp, responseStr);

        } else {
            System.out.println("NOT ENCODED");
            resp.getWriter().write(responseStr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost");
        resp.getWriter().write("Hello World!");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service");

        final String from = req.getParameter("from");
        String logMsg = "Handling request";
        if (Objects.nonNull(from)) {
            logMsg += " from " + from;
        }
        System.out.println(logMsg);

        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("destroy Hello servlet");
    }
}
