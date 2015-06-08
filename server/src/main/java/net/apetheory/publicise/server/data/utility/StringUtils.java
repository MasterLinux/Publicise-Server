package net.apetheory.publicise.server.data.utility;

/**
 * Collection of String utilities
 */
public class StringUtils {

    /**
     * Checks whether the given String
     * is null or empty
     *
     * @param s The String to test
     * @return true if it is empty or null, false otherwise
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

}
