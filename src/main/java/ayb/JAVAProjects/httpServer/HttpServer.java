package ayb.JAVAProjects.httpServer;

/*
 * Driver Class for the Http Server
 */

import ayb.JAVAProjects.httpServer.config.Configuration;
import ayb.JAVAProjects.httpServer.config.ConfigurationManager;
import ayb.JAVAProjects.httpServer.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Added import for SLF4J Logger

import java.io.IOException;

public class HttpServer {

    // SLF4J Logger for logging
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        // Log message indicating that the server is starting
        LOGGER.info("Server starting ...");

        // Load the configuration file using the ConfigurationManager singleton
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // Retrieve the current configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        // Log the port and web root directory being used
        LOGGER.info("Using Port : " + conf.getPort());
        LOGGER.info("Using WebRoot  : " + conf.getWebroot());

        try {
            // Start a new server listener thread with the specified port and web root directory
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            // Print any IOExceptions that occur during server operation
            e.printStackTrace();
            // Comment: Exception handling to be improved
        }

    }
}
