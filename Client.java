import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final int PORT = 8010;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int NUM_CLIENTS = 100; // Adjust based on load testing

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10); // Adjust thread pool size

        for (int i = 0; i < NUM_CLIENTS; i++) {
            threadPool.execute(() -> {
                try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), PORT);
                     PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // Send message to server
                    toServer.println("Hello Server, This is client!");

                    // Read response from server
                    String response = fromServer.readLine();
                    System.out.println("Response from server: " + response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
    }
}
