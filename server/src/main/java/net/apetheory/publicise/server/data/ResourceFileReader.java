package net.apetheory.publicise.server.data;

import net.apetheory.publicise.server.data.utility.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Gets a file from the resources folder
 */
public class ResourceFileReader {

    /**
     * Reads a file from the resources folder
     * @param filePath The file path to the file
     * @return The file content as String
     */
    @Nullable
    public static String readFile(String filePath) {
        String eol = System.getProperty("line.separator");
        URL resourceUrl = ResourceFileReader.class.getClassLoader().getResource(filePath);
        String resource = resourceUrl != null ? resourceUrl.getFile() : null;
        String out = null;

        if (!StringUtils.isNullOrEmpty(resource)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
                StringBuilder builder = new StringBuilder();
                reader.lines().forEach(line -> builder.append(line).append(eol));
                out = builder.toString();

            } catch (IOException e) {
                out = null;
            }
        }

        return out;
    }

    @Nullable
    public static Properties readProperties(String filePath) {
        URL resourceUrl = ResourceFileReader.class.getClassLoader().getResource(filePath);
        String resource = resourceUrl != null ? resourceUrl.getFile() : null;
        Properties properties = new Properties();

        if (!StringUtils.isNullOrEmpty(resource)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
                properties.load(reader);

            } catch (IOException e) {
                properties = null;
            }
        }

        return properties;
    }
}
