package ayb.JAVAProjects.httpServer;


/*
*
*Driver Class for the Http Server
* */

import ayb.JAVAProjects.httpServer.config.Configuration;
import ayb.JAVAProjects.httpServer.config.ConfigurationManager;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {

        System.out.println("Server starting ...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port : " + conf.getPort());
        System.out.println("Using WebRoot  : " + conf.getWebroot());


        try {


        ServerSocket serverSocket = new ServerSocket(conf.getPort());
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();

        // page to send to browser

            String html = "<!DOCTYPE html><html><head><title>Simple Java Http Server</title></head><body><h1>Served Using Our  Http Server</h1></body></html>";

            final  String CRFL = "\r\n"; // 13 , 10
            String response =
                    "HTTP/1.1 200 OK" +CRFL+ // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE :
                     "Content-Length :"+ html.getBytes().length+CRFL+ // Header
                            CRFL+
                            html+
                            CRFL+CRFL;

         outputStream.write(response.getBytes());

        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
