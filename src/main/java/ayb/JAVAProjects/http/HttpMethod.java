package ayb.JAVAProjects.http;

/**
 * Enum representing HTTP methods.
 */
public enum HttpMethod {

    // Enum constants representing HTTP methods
    GET, HEAD;

    // Static constant representing the maximum length of any HTTP method name
    public static final int MAX_LENGTH;

    // Static initialization block to compute the maximum length
    static {
        // Initialize a temporary variable to hold the maximum length
        int tempMaxLength = -1;

        // Iterate over all enum constants
        for (HttpMethod method : values()) {
            // Check if the length of the method name is greater than the current maximum length
            if (method.name().length() > tempMaxLength) {
                // Update the maximum length if necessary
                tempMaxLength = method.name().length();
            }
        }

        // Assign the computed maximum length to the MAX_LENGTH constant
        MAX_LENGTH = tempMaxLength;
    }
}
