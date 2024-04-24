package ayb.JAVAProjects.httpServer.config;

import ayb.JAVAProjects.httpServer.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    // Singleton instance of the ConfigurationManager
    public static ConfigurationManager myConfigurationManager; // we just need one shared on the app
    private static Configuration myCurrentConfiguration;

    // Private constructor to prevent instantiation from outside
    private ConfigurationManager(){}

    // Method to get the singleton instance of ConfigurationManager
    public static ConfigurationManager getInstance(){
        if (myConfigurationManager==null)
            myConfigurationManager=new ConfigurationManager();
        return myConfigurationManager;
    }



    // This method loads a configuration file from the specified filePath.
    // It reads the content of the file, parses it as JSON, and then converts it to a Configuration object.
    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader = null;
        try {
            // FileReader is used to read characters from the specified file.
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            // Throws a custom exception if the file is not found
            throw new HttpConfigurationException(e);
        }

        // StringBuffer is used to create a mutable string to store the content of the file.
        StringBuffer sb = new StringBuffer();

        int i;
        // Loop through each character of the file until reaching the end.
        while (true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                // Throws a custom exception if an IO error occurs while reading the file
                throw new HttpConfigurationException(e);
            }
            // Append each character to the StringBuffer.
            sb.append((char) i);
        }

        // Parse the content of the StringBuffer as JSON and convert it to a JsonNode.
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            // Throws a custom exception if there's an error parsing the JSON
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }

        // Convert the JsonNode to a Java object of type Configuration using the Json.fromJson method.
        // Configuration.class specifies the class type to which the JSON should be converted.
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            // Throws a custom exception if there's an error parsing the JSON
            throw new HttpConfigurationException("Error parsing the Configuration File, internal", e);
        }
    }

    /*
     * Returns the Current loaded Configuration
     * */
    public Configuration getCurrentConfiguration(){

        if (myCurrentConfiguration==null){
            // Throws a custom exception if no current configuration is set
            throw new HttpConfigurationException("No Current Configuration Set.");
        }

        return myCurrentConfiguration;
    }
}
