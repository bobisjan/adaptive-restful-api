package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;


public class DummyTestServlet extends FilteredServlet {

    public DummyTestServlet() {
        this.chain = new Filter() {
            @Override
            public HttpContext process(HttpContext httpContext) {
                return httpContext;
            }
        };
    }

}
