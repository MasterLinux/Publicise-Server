package mock;

import net.apetheory.publicise.server.resource.BaseResource;

/**
 * Created by Christoph on 15.09.2014.
 */
public class SimpleResourceMock extends BaseResource {
    private String stringValue;
    private NestedModel nested;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public NestedModel getNested() {
        return nested;
    }

    public void setNested(NestedModel nested) {
        this.nested = nested;
    }
}
