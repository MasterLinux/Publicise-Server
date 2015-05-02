package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.model.DocumentationModel;

/**
 * Created by Christoph on 02.05.2015.
 */
public class DocumentationReader implements Reader<DocumentationModel> {

    private Class<?>[] resources;

    public DocumentationReader(Class<?>... resources) {
        this.resources = resources;
    }

    @Override
    public DocumentationModel read() {
        DocumentationModel model = new DocumentationModel();

        for(Class<?> resource : resources) {
            model.addResource(new ResourceReader(resource).read());
        }

        return model;
    }
}
