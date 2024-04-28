package ayb.JAVAProjects.http;

public class HttpRequest extends HttpMessage {

    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;


    HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

     void setMethod(HttpMethod method) {
        this.method = method;
    }


}
