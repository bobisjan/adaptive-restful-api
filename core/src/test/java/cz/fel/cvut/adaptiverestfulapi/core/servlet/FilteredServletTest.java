package cz.fel.cvut.adaptiverestfulapi.core.servlet;

import cz.fel.cvut.adaptiverestfulapi.core.DummyTestServlet;

import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilteredServletTest {

    @Test(expected=FilteredServletException.class)
    public void testEmptyChain() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        new FilteredServlet().service(request, response);
    }

    public void testDummyChain() throws ServletException, IOException {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        new DummyTestServlet().service(request, response);
        assertTrue(true);
    }

}
