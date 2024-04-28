package ayb.JAVAProjects.httpServer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    // SLF4J Logger for logging
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    // Instance variable
    private Socket socket;

    // Constructor
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    // Run method
    @Override
    public void run() {

        // Input and output streams for socket communication
        InputStream inputStream = null;
        OutputStream outputStream = null;



        try {
            // Get input and output streams for the socket
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();




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

            // Log message indicating that connection processing is finished
            LOGGER.info("Connection Processing Finished");

        } catch (IOException e) {
            // Log error message if there is a problem with communication
            LOGGER.error("Problem with communication ", e);
        } finally { // Close input and output streams and the socket
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
