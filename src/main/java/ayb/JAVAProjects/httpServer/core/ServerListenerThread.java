package ayb.JAVAProjects.httpServer.core;

import ayb.JAVAProjects.httpServer.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    // SLF4J Logger for logging
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    // Instance variables
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    // Constructor
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    // Run method
    @Override
    public void run() {

        try {
            // Continuously listen for incoming connection requests
            while (serverSocket.isBound() && !serverSocket.isClosed()) {

                // Accept incoming connection requests and create a socket for each client
                Socket socket = serverSocket.accept();

                // Log message indicating that a connection is accepted
                LOGGER.info("* Connection accepted :" + socket.getInetAddress());

                // Get input and output streams for the socket
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // HTML page to be sent to the browser
                String html = "<!DOCTYPE html><html><head><title>Simple Java Http Server</title></head><body><h1>Served Using Our  Http Server</h1></body></html>";

                // Carriage return and line feed characters
                final String CRFL = "\r\n"; // 13 , 10

                // HTTP response to be sent back to the client
                String response =
                        "HTTP/1.1 200 OK" + CRFL + // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE :
                                "Content-Length :" + html.getBytes().length + CRFL + // Header
                                CRFL +
                                html +
                                CRFL + CRFL;

                // Write the HTTP response to the output stream
                outputStream.write(response.getBytes());

                // Close input and output streams and the socket
                inputStream.close();
                outputStream.close();
                socket.close();

                // Pause for 5 seconds to simulate processing time (for testing purposes)
                try {
                    sleep(5000); // to test that its make other clients waiting until this finish , so we create another thread to handle the  problem and requests
                } catch (InterruptedException e) {
                    // Throw a RuntimeException if sleep is interrupted
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            // Print any IOExceptions that occur during server operation
            e.printStackTrace();
        }
    }
}
