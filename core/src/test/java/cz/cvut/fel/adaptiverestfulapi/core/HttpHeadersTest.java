
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.core.HttpHeader;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaderValue;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.lang.Exception;
import java.util.List;
import java.util.LinkedList;


public class HttpHeadersTest {

    @DataProvider
    public Object[][] Headers() {
        List<HttpHeaderValue> values = new LinkedList<>();
        values.add(new HttpHeaderValue("v3", 0.1));
        values.add(new HttpHeaderValue("v2", 0.5));
        values.add(new HttpHeaderValue("v1"));

        HttpHeader header = HttpHeader.create("k1", values);

        List<HttpHeader> headers = new LinkedList<>();
        headers.add(header);

        return new Object[][]{
                { new HttpHeaders(headers) }
        };
    }

    @Test(dataProvider = "Headers")
    public void testGet(HttpHeaders headers) throws Exception {
        String result = headers.get("k1");
        assert ("v1".equals(result)) : "" + result + " should be equal to v1 for k1.";
    }

    @Test(dataProvider = "Headers")
    public void testContains(HttpHeaders headers) throws Exception {
        assert (headers.contains("k1", "v1")) : "HTTP headers should contain value v1 for k1.";
        assert (headers.contains("k1", "v2")) : "HTTP headers should contain value v2 for k1.";
        assert (headers.contains("k1", "v3")) : "HTTP headers should contain value v3 for k1.";

        assert (!headers.contains("k2", "w1")) : "HTTP headers should not contain value w1 for k2.";
    }

    @Test(dataProvider = "Headers")
    public void testAdd(HttpHeaders headers) throws Exception {
        headers.add("k3", new HttpHeaderValue("v1"));
        assert (headers.contains("k3", "v1")) : "HTTP headers should contain value v1 for k3.";

        headers.add("k3", new HttpHeaderValue("v2"));
        assert (headers.contains("k3", "v2")) : "HTTP headers should contain value v2 for k3.";

        headers.add("k3", new HttpHeaderValue("v3", 0.5));
        assert (headers.contains("k3", "v3")) : "HTTP headers should contain value v3 for k3.";

        headers.add("k3", "v4");
        assert (headers.contains("k3", "v4")) : "HTTP headers should contain value v4 for k3.";

        headers.add("k3", "v5;q=0.3");
        assert (headers.contains("k3", "v5")) : "HTTP headers should contain value v5 for k3.";

        headers.add("k3", "v6; q=0.3");
        assert (headers.contains("k3", "v6")) : "HTTP headers should contain value v6 for k3.";
    }

}
