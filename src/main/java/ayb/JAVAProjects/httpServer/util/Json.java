package ayb.JAVAProjects.httpServer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

// This class essentially encapsulates common JSON processing tasks like parsing JSON strings,
// converting JSON to Java objects, and vice versa, along with methods for generating
// JSON strings from objects with options for pretty printing.
// (The `pretty` parameter controls whether the generated JSON string includes additional formatting (indentation and whitespace) for human readability.)

public class Json {

    // ObjectMapper is a class provided by Jackson library to read and write JSON.
    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    // This method initializes the ObjectMapper with default configurations.
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        // Configure ObjectMapper to ignore unknown properties when deserializing JSON.
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return om;
    }

    // This method parses a JSON string and returns a JsonNode representing the JSON structure.
    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    // This method converts a JsonNode to a Java object of a specified class.
    public static <A> A fromJson(JsonNode node , Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    // This method converts a Java object to a JsonNode.
    public  static  JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    // This method converts a JsonNode to a JSON string.
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    // This method converts a JsonNode to a pretty-printed JSON string.
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    // This method generates a JSON string from an object, with an option for pretty printing.
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty) {
            // Configure the ObjectWriter to indent the output for pretty printing.
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }

}
