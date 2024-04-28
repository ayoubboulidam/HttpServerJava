package ayb.JAVAProjects.httpServer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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
                LOGGER.info("Connection accepted: {}", socket.getRemoteSocketAddress());

                // Check if the client has previously visited
                boolean hasVisited = checkIfVisited(reader);

                // Serve content based on visit status
                serveContent(outputStream, path, hasVisited);

                LOGGER.info("Connection Processing Finished");
            }
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Error closing socket", e);
            }
        }
    }

    private boolean checkIfVisited(BufferedReader reader) throws IOException {
        // Parse request headers to check if "isVisited" cookie is present
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Cookie:")) {
                String[] cookies = line.substring("Cookie:".length()).split("; ");
                for (String cookie : cookies) {
                    String[] parts = cookie.split("=");
                    if (parts.length == 2 && parts[0].trim().equals("isVisited")) {
                        return parts[1].trim().equals("yes");
                    }
                }
            }
            // Stop when reaching end of headers
            if (line.isEmpty()) {
                break;
            }
        }
        return false;
    }

    private void serveContent(OutputStream outputStream, String path, boolean hasVisited) throws IOException {
        // Check if the requested path ends with '/'
        if (path.endsWith("/")) {
            path += hasVisited ? "content2.html" : "Content.html";
        }

        // Construct the file path using the web root directory
        File file = new File(WEB_ROOT + path);

        String contentType;
        if (file.exists() && !file.isDirectory()) {
            // Determine the content type
            contentType = Files.probeContentType(file.toPath());

            // Read the content of the file
            byte[] content = Files.readAllBytes(file.toPath());

            // Always set the cookie to "yes" when serving content
            String setCookieHeader = "Set-Cookie: isVisited=yes";

            LOGGER.info("Set-Cookie header: {}", setCookieHeader);

            // Send the file content as the HTTP response along with the cookie
            sendResponse(outputStream, "200 OK", contentType, content, setCookieHeader);
        } else {
            // Handle file not found
            // Still send the cookie header even if the file is not found
            String setCookieHeader = "Set-Cookie: isVisited=yes";
            sendResponse(outputStream, "404 Not Found", "text/plain", "File Not Found".getBytes(), setCookieHeader);
        }
    }

    private void sendResponse(OutputStream outputStream, String status, String contentType, byte[] content, String cookieHeader) throws IOException {
        outputStream.write(("HTTP/1.1 " + status + "\r\n").getBytes());
        outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
        outputStream.write(("Content-Length: " + content.length + "\r\n").getBytes());
        if (cookieHeader != null) {
            outputStream.write((cookieHeader + "\r\n").getBytes());
        }
        outputStream.write("\r\n".getBytes());
        outputStream.write(content);
    }
}
