import mock.NestedModel;
import mock.SimpleResourceMock;
import net.apetheory.publicise.server.data.ResourceSet;
import net.apetheory.publicise.server.data.converter.JsonConverter;
import org.junit.Assert;
import org.junit.Test;

public class JsonConverterTest {

    private static final long EXPECTED_TOTAL_COUNT = 10;
    private static final String EXPECTED_RES_ID = "id";
    private static final String EXPECTED_STRING_VALUE = "string_value";
    private static final double EXPECTED_DOUBLE_VALUE = 1.1d;
    private static final int EXPECTED_INT_VALUE = 2;
    private static final double EXPECTED_DOUBLE_VALUE_NESTED = 3.3d;
    private static final int EXPECTED_INT_VALUE_NESTED = 4;

    @Test
    public void testConverterShouldConvert() {
        String expectedResponse = "{\"meta\":{\"filteredCount\":1,\"limit\":10,\"next\":null,\"offset\":0,\"prev\":null,\"totalCount\":10},\"objects\":[{\"id\":\"id\",\"nested\":{\"doubleValue\":1.1,\"integerValue\":2,\"nested\":{\"doubleValue\":3.3,\"integerValue\":4,\"nested\":null}},\"resourceUri\":null,\"stringValue\":\"string_value\"}]}";
        String actualResponse = JsonConverter.toJSON(getResourceSet(), null);

        Assert.assertEquals("message", expectedResponse, actualResponse);
    }

    @Test
    public void testConverterShouldFilter() {
        String expectedResponse = "{\"meta\":{\"filteredCount\":1,\"limit\":10,\"next\":null,\"offset\":0,\"prev\":null,\"totalCount\":10},\"objects\":[{\"nested\":{\"doubleValue\":1.1,\"integerValue\":2},\"stringValue\":\"string_value\"}]}";
        String actualResponse = JsonConverter.toJSON(getResourceSet(), new String[] {
                "nested.doubleValue",
                "stringValue",
                "nested.integerValue"
        });

        Assert.assertEquals("message", expectedResponse, actualResponse);
    }

    private ResourceSet getResourceSet() {
        ResourceSet.Builder<SimpleResourceMock> builder = new ResourceSet.Builder<>(EXPECTED_TOTAL_COUNT);
        SimpleResourceMock resource = new SimpleResourceMock();

        NestedModel nestedNested = new NestedModel();
        nestedNested.setDoubleValue(EXPECTED_DOUBLE_VALUE_NESTED);
        nestedNested.setIntegerValue(EXPECTED_INT_VALUE_NESTED);

        NestedModel nested = new NestedModel();
        nested.setDoubleValue(EXPECTED_DOUBLE_VALUE);
        nested.setIntegerValue(EXPECTED_INT_VALUE);
        nested.setNested(nestedNested);

        resource.setId(EXPECTED_RES_ID);
        resource.setStringValue(EXPECTED_STRING_VALUE);
        resource.setNested(nested);

        return builder.addResource(resource).build();
    }
}
