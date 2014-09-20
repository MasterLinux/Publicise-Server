import net.apetheory.publicise.server.data.UriUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UriUtilsTest {

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
}
