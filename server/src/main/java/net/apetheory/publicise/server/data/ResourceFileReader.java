package net.apetheory.publicise.server.data;

import net.apetheory.publicise.server.data.utility.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Gets a file from the resources folder
 */
public class ResourceFileReader {
    private String filePath;

    public ResourceFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Nullable
    public String read() {
        String eol = System.getProperty("line.separator");
        URL resourceUrl = getClass().getClassLoader().getResource("documentation.html");
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
}
