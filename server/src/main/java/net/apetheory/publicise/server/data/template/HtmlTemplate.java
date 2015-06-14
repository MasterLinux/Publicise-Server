package net.apetheory.publicise.server.data.template;

import net.apetheory.publicise.server.data.ResourceFileReader;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

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

        IMessageResolver messageResolver = new MessageResolver("templates/" + prefix + "Messages.properties");

        templateEngine = new TemplateEngine() {{
            setTemplateResolver(templateResolver);
            setMessageResolver(messageResolver);
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

    public static class MessageResolver implements IMessageResolver {
        private boolean isInitialized;
        private final String path;

        public MessageResolver(String path) {
            this.path = path;
        }

        @Override
        public String getName() {
            return "HTML_TEMPLATE_MESSAGE_RESOLVER";
        }

        @Override
        public Integer getOrder() {
            return 0;
        }

        @Override
        public MessageResolution resolveMessage(Arguments arguments, String key, Object[] messageParameters) {
            Properties properties = new ResourceFileReader().readProperties(path);
            MessageResolution resolution = null;

            if(properties != null && properties.containsKey(key)) {
                resolution = new MessageResolution(properties.getProperty(key));   //TODO use messageParameters
            }

            return resolution;
        }

        @Override
        public void initialize() {
            if(!isInitialized) {
                isInitialized = true;
            }
        }
    }
}
