package net.apetheory.publicise.server.data.template;

import org.jetbrains.annotations.Nullable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Christoph on 06.05.2015.
 */
public class HtmlTemplate {
    private final TemplateEngine templateEngine;

    public HtmlTemplate(String prefix) {
        TemplateResolver templateResolver = new TemplateResolver() {{
            setResourceResolver(new ClassLoaderResourceResolver());
            setTemplateMode("HTML5");
            setCacheTTLMs(3600000L);
            setPrefix("templates/" + prefix);
            setSuffix(".html");
        }};

        templateEngine = new TemplateEngine() {{
            setTemplateResolver(templateResolver);
        }};


    }

    @Nullable
    public String parse(String name, Object dataContext) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        String out = null;

        Context context = new Context() {{
            setVariable("dataContext", dataContext);
        }};

        templateEngine.process(name, context, writer);

        try {
            writer.flush();
            outputStream.flush();

            out = new String(outputStream.toByteArray());

        } catch (IOException e) {
            out = null;
        }

        return out;
    }
}
