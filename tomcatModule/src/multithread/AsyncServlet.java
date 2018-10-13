package multithread;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * There are fixed thread pool for handling requests.
 * To make handling requests more fast and efficient we can use async procession.
 */
@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext context = req.getAsyncContext();

        System.out.println("Handling request in THREAD: " + Thread.currentThread().getName());

        context.start(() -> {
            // processing in another thread
            try {
                resp.getWriter()
                        .write("RESPONSE FROM THREAD: + " + Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        context.complete();
    }
}
