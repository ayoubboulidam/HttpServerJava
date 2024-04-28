package ayb.JAVAProjects.httpServer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpConnectionWorkerThread extends Thread {
    private final static String WEB_ROOT = "src/main/resources/Webroot";
    private final Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line = reader.readLine();
            if (line != null) {
                String[] request = line.split("\\s+");
                String method = request[0];
                String path = request[1];
                LOGGER.info("Connection accepted : {}", socket.getRemoteSocketAddress());
                if (method.equals("GET")) {
                    serveFile(outputStream, path);
                } else {
                    // Handle unsupported methods
                    sendResponse(outputStream, "405 Method Not Allowed", "text/plain", "Method Not Allowed");
                }
                LOGGER.info("Connection Processing Finished");
            }
        } catch (IOException e) {
            LOGGER.error("Problem with communication ", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Error closing socket ", e);
            }
        }
    }

    private void serveFile(OutputStream outputStream, String path) throws IOException {
        // Check if the requested path ends with '/'
        if (path.endsWith("/")) {
            path += "Content.html"; // Append the specific HTML file name
        }

        // Construct the file path using the web root directory
        File file = new File(WEB_ROOT + path);

        // Check if the file exists and is not a directory
        if (file.exists() && !file.isDirectory()) {
            // Determine the content type
            String contentType = Files.probeContentType(file.toPath());

            // Read the content of the file
            byte[] content = Files.readAllBytes(file.toPath());

            // Send the file content as the HTTP response
            sendResponse(outputStream, "200 OK", contentType, content);
        } else {
            // Handle file not found
            sendResponse(outputStream, "404 Not Found", "text/plain", "File Not Found");
        }
    }


    private void sendResponse(OutputStream outputStream, String status, String contentType, byte[] content) throws IOException {
        outputStream.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
        outputStream.write(("Content-Length: " + content.length + "\r\n").getBytes());
        outputStream.write("\r\n".getBytes());
        outputStream.write(content);
    }

    private void sendResponse(OutputStream outputStream, String status, String contentType, String content) throws IOException {
        sendResponse(outputStream, status, contentType, content.getBytes());
    }
}
