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

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();


            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket",e);
        }finally {
            if (serverSocket!= null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }

        }
    }
}
