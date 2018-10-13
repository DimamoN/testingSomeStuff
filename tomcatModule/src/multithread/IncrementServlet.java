package multithread;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "IncrementServlet", value = "/increment",
        description = "Servlet for testing multithreading while handling requests")
public class IncrementServlet extends HttpServlet {

    // Common data for all requests
    private int unsyncValue;
    private int syncValue;
    private AtomicInteger atomicValue = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(Thread.currentThread().getName() + ": Handling increment request.");

        // atomic variables can solve problem with race-condition
        for (int i = 0; i < 1_000_000; i++) {
            unsyncValue++;
            atomicValue.incrementAndGet();
        }

        // non-atomic variables should be synchronized
        synchronized (this) {
            for (int i = 0; i < 1_000_000; i++) {
                syncValue++;
            }
        }

        System.out.println(Thread.currentThread().getName() +
                ": Result atomic: " + atomicValue +
                ", synchronized non-atomic: " + syncValue +
                ", not synchronized non-atomic: " + unsyncValue);
    }
}
