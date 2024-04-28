package ayb.JAVAProjects.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The HttpParser class parses HTTP requests.
 */
public class HttpParser {

    // Logger for logging messages
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10

    /**
     * Parses an HTTP request from the provided input stream.
     *
     * @param inputStream The input stream containing the HTTP request data.
     */
    public HttpRequest parseHttpRequest(InputStream inputStream){
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        parseRequestLine(reader, request);
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;

    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {

    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) {

    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request)  throws IOException {

        int _byte;
        while ((_byte = reader.read()) >=0) {
            if (_byte == CR) {
                _byte = reader.read();
            if (_byte == LF) {
                return;
            }

        }

    }

}
