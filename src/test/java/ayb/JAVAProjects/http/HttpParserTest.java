package ayb.JAVAProjects.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The HttpParserTest class contains test cases for the HttpParser class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    // Instance variable for the HttpParser object
    private HttpParser httpParser;

    /**
     * Initializes the HttpParser object before executing test methods.
     */
    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    /**
     * Tests the parseHttpRequest method of the HttpParser class.
     */
    @Test
    void parseHttpRequest() {
        // Calls the parseHttpRequest method of HttpParser with a valid test case
        httpParser.parseHttpRequest(
                generateValidTestCase()
        );
    }

    /**
     * Generates a valid HTTP request as an InputStream for testing purposes.
     *
     * @return InputStream containing the valid HTTP request data.
     */
    private InputStream generateValidTestCase() {
        // Raw HTTP request data
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n"+
                "\r\n";

        // Convert raw data to InputStream using US_ASCII character set
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                ));

        return inputStream;
    }
}
