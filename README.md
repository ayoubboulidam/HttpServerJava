# HTTP Server Project Overview

## Project Description
The project is a simple HTTP server implementation in Java. It provides basic functionalities for handling incoming HTTP requests, processing them, and generating appropriate responses.

## Project Structure
The project consists of several packages and classes:

### HTTP Server Core
- **Package:** `ayb.JAVAProjects.httpServer.core`
  - `HttpServer`: Main class responsible for starting the server.
  - `ServerListenerThread`: Listens for incoming connections and delegates request handling to worker threads.
  - `HttpConnectionWorkerThread`: Handles individual client connections, processing requests and generating responses.

### Configuration Management
- **Package:** `ayb.JAVAProjects.httpServer.config`
  - `ConfigurationManager`: Manages server configurations, including loading settings from a file.
  - `Configuration`: Represents server configuration settings such as port number and web root directory.

### HTTP Protocol Handling
- **Package:** `ayb.JAVAProjects.http`
  - `HttpRequest`: Represents an HTTP request, including methods for parsing and accessing request parameters.
  - `HttpParser`: Parses incoming HTTP requests and extracts relevant information.
  - `HttpMethod`: Enum representing various HTTP methods like GET and HEAD.
  - `HttpVersion`: Enum representing HTTP versions, with methods for determining the best compatible version.
  - `HttpStatusCode`: Enum representing HTTP status codes.

### Exceptions
- **Package:** `ayb.JAVAProjects.http`
  - `HttpParsingException`: Custom exception for handling errors during HTTP request parsing.
  - `BadHttpVersionException`: Custom exception for handling errors related to unsupported HTTP versions.

### Cookies 
Cookies are utilized for maintaining state between client and server. The server includes functionality for setting cookies when serving content to clients.

### Unit Testing
The project includes unit tests to verify the correctness and reliability of key components such as `HttpParser` and `HttpVersion`. Tests cover various scenarios, including valid HTTP requests, malformed requests, and error handling.

## Conclusion
Overall, the project provides a basic HTTP server implementation in Java, adhering to HTTP conventions and including error handling mechanisms for robustness. Cookies are utilized for maintaining state between client and server interactions.
