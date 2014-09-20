import net.apetheory.publicise.server.data.UriUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UriUtilsTest {

    private static final String EXPECTED_OFFSET_VALUE = "7";
    private static final String EXPECTED_LIMIT_VALUE = "3";

    @Test
    public void testUriParameterShouldBeValid() {
        List<String> values = new ArrayList<>();
        values.add("test_value");
        String name = "test_name";
        String exclude = "exclude_name";
        String anotherExclude = "another_exclude_name";

        Assert.assertTrue(UriUtils.isQueryParameterValid(name, values));
        Assert.assertTrue(UriUtils.isQueryParameterValid(name, values, exclude));
        Assert.assertTrue(UriUtils.isQueryParameterValid(name, values, exclude, anotherExclude));
    }

    @Test
    public void testExcludedUriParameterShouldBeInvalid() {
        List<String> values = new ArrayList<>();
        values.add("test_value");
        String name = "test_name";
        String exclude = "exclude_name";
        String anotherExclude = "test_name";

        Assert.assertFalse(UriUtils.isQueryParameterValid(name, values, anotherExclude));
        Assert.assertFalse(UriUtils.isQueryParameterValid(name, values, exclude, anotherExclude));
    }

    @Test
    public void testUriParameterWithEmptyOrNullValueShouldBeInvalid() {
        List<String> values = new ArrayList<>();
        String name = "test_name";
        String exclude = "exclude_name";

        Assert.assertFalse(UriUtils.isQueryParameterValid(name, values));
        Assert.assertFalse(UriUtils.isQueryParameterValid(name, values, exclude));
        Assert.assertFalse(UriUtils.isQueryParameterValid(name, null, exclude));
    }

    @Test
    public void testUriParameterWithEmptyOrNullNameShouldBeInvalid() {
        List<String> values = new ArrayList<>();
        values.add("test_value");
        String name = "";
        String exclude = "exclude_name";

        Assert.assertFalse(UriUtils.isQueryParameterValid(name, values));
        Assert.assertFalse(UriUtils.isQueryParameterValid(null, values, exclude));
    }

    @Test
    public void testOffsetValueShouldBeAsExpected() {
        MultivaluedHashMap<String, String> parameter = new MultivaluedHashMap<>();
        parameter.add("offset", EXPECTED_OFFSET_VALUE);
        UriInfo info = buildUriInfoMock(parameter);

        Assert.assertEquals((Object) Integer.valueOf(EXPECTED_OFFSET_VALUE), UriUtils.getCurrentOffset(info));
    }

    @Test
    public void testOffsetValueShouldBeUndefined() {
        MultivaluedHashMap<String, String> parameter = new MultivaluedHashMap<>();
        UriInfo info = buildUriInfoMock(parameter);
        String emptyValue = "";

        //test missing value
        Assert.assertEquals((Object) UriUtils.UNDEFINED_OFFSET_PARAMETER_VALUE, UriUtils.getCurrentOffset(info));

        parameter.add("offset", emptyValue);
        info = buildUriInfoMock(parameter);

        //test empty value
        Assert.assertEquals((Object) UriUtils.UNDEFINED_OFFSET_PARAMETER_VALUE, UriUtils.getCurrentOffset(info));
    }

    @Test
    public void testLimitValueShouldBeAsExpected() {
        MultivaluedHashMap<String, String> parameter = new MultivaluedHashMap<>();
        parameter.add("limit", EXPECTED_LIMIT_VALUE);
        UriInfo info = buildUriInfoMock(parameter);

        Assert.assertEquals((Object) Integer.valueOf(EXPECTED_LIMIT_VALUE), UriUtils.getCurrentLimit(info));
    }

    @Test
    public void testLimitValueShouldBeUndefined() {
        MultivaluedHashMap<String, String> parameter = new MultivaluedHashMap<>();
        UriInfo info = buildUriInfoMock(parameter);
        String emptyValue = "";

        //test missing value
        Assert.assertEquals((Object) UriUtils.UNDEFINED_LIMIT_PARAMETER_VALUE, UriUtils.getCurrentLimit(info));

        parameter.add("limit", emptyValue);
        info = buildUriInfoMock(parameter);

        //test empty value
        Assert.assertEquals((Object) UriUtils.UNDEFINED_LIMIT_PARAMETER_VALUE, UriUtils.getCurrentLimit(info));
    }

    private UriInfo buildUriInfoMock(MultivaluedHashMap<String, String> parameter) {
        UriInfo uriInfo = mock(UriInfo.class);

        when(uriInfo.getQueryParameters()).thenReturn(parameter);

        return uriInfo;
    }
}
