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
    public void testResponseShouldContainAllFields() {
        String actualResponse = JsonConverter.toJSON(getResourceSet(), new String[]{});

        Assert.assertNotNull(actualResponse);

        Assert.assertTrue(actualResponse.contains("\"meta\":{"));
        Assert.assertTrue(actualResponse.contains("\"filteredCount\":1"));
        Assert.assertTrue(actualResponse.contains("\"totalCount\":" + EXPECTED_TOTAL_COUNT));
        Assert.assertTrue(actualResponse.contains("\"limit\":10"));
        Assert.assertTrue(actualResponse.contains("\"next\":null"));
        Assert.assertTrue(actualResponse.contains("\"prev\":null"));
        Assert.assertTrue(actualResponse.contains("\"offset\":0"));

        Assert.assertTrue(actualResponse.contains("\"objects\":["));
        Assert.assertTrue(actualResponse.contains("\"id\":\"" + EXPECTED_RES_ID + "\""));
        Assert.assertTrue(actualResponse.contains("\"stringValue\":\"" + EXPECTED_STRING_VALUE + "\""));
        Assert.assertTrue(actualResponse.contains("\"resourceUri\":null"));

        Assert.assertTrue(actualResponse.contains("\"nested\":{"));
        Assert.assertTrue(actualResponse.contains("\"integerValue\":" + EXPECTED_INT_VALUE + ""));
        Assert.assertTrue(actualResponse.contains("\"doubleValue\":" + EXPECTED_DOUBLE_VALUE + ""));

        Assert.assertTrue(actualResponse.contains("\"integerValue\":" + EXPECTED_INT_VALUE_NESTED + ""));
        Assert.assertTrue(actualResponse.contains("\"doubleValue\":" + EXPECTED_DOUBLE_VALUE_NESTED + ""));
    }

    @Test
    public void testResponseShouldContainJustSpecificFields() {
        String actualResponse = JsonConverter
                .toJSON(getResourceSet(), new String[]{
                        "nested.doubleValue",
                        "stringValue",
                        "nested.integerValue",
                        "nested.nested.doubleValue"
                });

        Assert.assertNotNull(actualResponse);

        Assert.assertTrue(actualResponse.contains("\"meta\":{"));
        Assert.assertTrue(actualResponse.contains("\"filteredCount\":1"));
        Assert.assertTrue(actualResponse.contains("\"totalCount\":" + EXPECTED_TOTAL_COUNT));
        Assert.assertTrue(actualResponse.contains("\"limit\":10"));
        Assert.assertTrue(actualResponse.contains("\"next\":null"));
        Assert.assertTrue(actualResponse.contains("\"prev\":null"));
        Assert.assertTrue(actualResponse.contains("\"offset\":0"));

        Assert.assertTrue(actualResponse.contains("\"objects\":["));
        Assert.assertFalse(actualResponse.contains("\"id\":\"" + EXPECTED_RES_ID + "\""));
        Assert.assertTrue(actualResponse.contains("\"stringValue\":\"" + EXPECTED_STRING_VALUE + "\""));
        Assert.assertFalse(actualResponse.contains("\"resourceUri\":null"));

        Assert.assertTrue(actualResponse.contains("\"nested\":{"));
        Assert.assertTrue(actualResponse.contains("\"integerValue\":" + EXPECTED_INT_VALUE + ""));
        Assert.assertTrue(actualResponse.contains("\"doubleValue\":" + EXPECTED_DOUBLE_VALUE + ""));

        Assert.assertFalse(actualResponse.contains("\"integerValue\":" + EXPECTED_INT_VALUE_NESTED + ""));
        Assert.assertTrue(actualResponse.contains("\"doubleValue\":" + EXPECTED_DOUBLE_VALUE_NESTED + ""));
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
