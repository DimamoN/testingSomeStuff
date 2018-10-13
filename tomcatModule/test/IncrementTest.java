import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Testing atomic / sync variable in {@link multithread.IncrementServlet}
 */
public class IncrementTest {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    URLConnection connection = new URL("http://localhost:8080/increment")
                            .openConnection();
                    connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
