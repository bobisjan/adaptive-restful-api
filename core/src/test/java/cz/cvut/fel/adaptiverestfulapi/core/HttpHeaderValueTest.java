
package cz.cvut.fel.adaptiverestfulapi.core;

import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaderValue;
import org.testng.annotations.Test;

import java.lang.ClassCastException;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Integer;
import java.util.Date;


public class HttpHeaderValueTest {

    @Test
    public void testCompareTo() throws Exception {
        HttpHeaderValue a = new HttpHeaderValue("a");
        HttpHeaderValue b = new HttpHeaderValue("b", 0.3);
        HttpHeaderValue c = new HttpHeaderValue("c", 0.5);
        HttpHeaderValue d = new HttpHeaderValue("d", 0.5);
        HttpHeaderValue e = new HttpHeaderValue("e", 0.0);
        HttpHeaderValue f = new HttpHeaderValue("f", 1.0);

        assert (a.compareTo(b) == 1) : "" + a.getQ() + " should be grater then " + b.getQ() + ".";
        assert (a.compareTo(e) == 1) : "" + a.getQ() + " should be grater then " + e.getQ() + ".";
        assert (a.compareTo(f) == 1) : "" + a.getQ() + " should be grater then " + f.getQ() + ".";
        assert (c.compareTo(d) == 0) : "" + c.getQ() + " should be equal to" + d.getQ() + ".";
        assert (e.compareTo(f) == -1) : "" + e.getQ() + " should be lower then " + f.getQ() + ".";
    }

    @Test
    public void testGet() throws Exception {
        HttpHeaderValue value = new HttpHeaderValue("a");
        String result = value.get();

        assert ("a".equals(result)) : "" + result + " should be eqaul to a.";
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void testGetNotSupportedYet() throws Exception {
        HttpHeaderValue value = new HttpHeaderValue("a");

        Boolean b = value.get();
        Integer i = value.get();
        Float f = value.get();
        Double d = value.get();
        Date dt = value.get();
    }

}
