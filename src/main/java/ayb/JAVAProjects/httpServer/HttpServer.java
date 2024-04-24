package ayb.JAVAProjects.httpServer;

/*
 * Driver Class for the Http Server
 */

import ayb.JAVAProjects.httpServer.config.Configuration;
import ayb.JAVAProjects.httpServer.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {

        // Print a message indicating that the server is starting
        System.out.println("Server starting ...");

        // Load the configuration file using the ConfigurationManager singleton
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // Retrieve the current configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        // Print the port and web root directory being used
        System.out.println("Using Port : " + conf.getPort());
        System.out.println("Using WebRoot  : " + conf.getWebroot());

        try {
            // Create a ServerSocket to listen for incoming connections on the specified port
            ServerSocket serverSocket = new ServerSocket(conf.getPort());

            // Accept incoming connection requests and create a socket for each client
            Socket socket = serverSocket.accept();

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
            serverSocket.close();

        } catch (IOException e) {
            // Print any IOExceptions that occur during server operation
            e.printStackTrace();
        }
    }
}
