
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.core.HttpHeader;
import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaderValue;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.lang.Exception;
import java.util.List;
import java.util.LinkedList;


public class HttpHeaderTest {

    @DataProvider
    public Object[][] Header() {
        List<HttpHeaderValue> values = new LinkedList<>();
        values.add(new HttpHeaderValue("v3", 0.1));
        values.add(new HttpHeaderValue("v2", 0.5));
        values.add(new HttpHeaderValue("v1"));

        HttpHeader header = HttpHeader.create("key", values);
        return new Object[][]{
                { header }
        };
    }

    @Test
    public void testCreateEmpty() throws Exception {
        HttpHeader header = HttpHeader.create("key", new LinkedList<HttpHeaderValue>());
        assert (header == null) : "HTTP header with empty values list should be null.";
    }

    @Test(dataProvider = "Header")
    public void testCreate(HttpHeader header) throws Exception {
        assert (header != null) : "HTTP header with nonempty values list should not be null.";
    }

    @Test(dataProvider = "Header")
    public void testGet(HttpHeader header) throws Exception {
        String result = header.get();
        assert ("v1".equals(result)) : "" + result + " should be equal to v1.";
    }

    @Test(dataProvider = "Header")
    public void testContains(HttpHeader header) throws Exception {
        assert (header.contains("v1")) : "HTTP header should contain value v1.";
        assert (header.contains("v2")) : "HTTP header should contain value v2.";
        assert (header.contains("v3")) : "HTTP header should contain value v3.";

        assert (!header.contains("w1")) : "HTTP header should not contain value w1.";
    }

}
