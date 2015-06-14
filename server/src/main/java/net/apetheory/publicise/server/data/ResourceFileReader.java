package net.apetheory.publicise.server.data;

import net.apetheory.publicise.server.data.utility.StringUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Gets a file from the resources folder
 */
public class ResourceFileReader {


    public InputStream getResourceAsStream(String resource) {
        InputStream stream;

        //Try with the Thread Context Loader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(resource);
            if (stream != null) {
                return stream;
            }
        }

        //Let's now try with the class loader that loaded this class.
        classLoader = this.getClass().getClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(resource);
            if (stream != null) {
                return stream;
            }
        }

        //Last ditch attempt. Get the resource from the classpath.
        return ClassLoader.getSystemResourceAsStream(resource);
    }

    /**
     * Reads a file from the resources folder
     *
     * @param resource The file path to the resource
     * @return The file content as String
     */
    @Nullable
    public String readFile(String resource) {
        InputStream input = getResourceAsStream(resource);
        String fileContent = null;

        try {
            fileContent = IOUtils.toString(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    @Nullable
    public Properties readProperties(String filePath) {   // TODO implement simple version with IOUtils
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
