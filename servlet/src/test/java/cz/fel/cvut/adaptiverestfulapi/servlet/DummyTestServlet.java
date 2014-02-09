package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.fel.cvut.adaptiverestfulapi.core.Context;
import cz.fel.cvut.adaptiverestfulapi.core.Filter;


public class DummyTestServlet extends FilteredServlet {

    public DummyTestServlet() {
        this.chain = new Filter() {
            @Override
            public void process(Context context) {

            }
        };
    }

}
