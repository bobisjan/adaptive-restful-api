package cz.fel.cvut.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;


public class DummyTestServlet extends FilteredServlet {

    public DummyTestServlet() {
        this.chain = new Filter() {
            @Override
            public void process(Context context) {

            }
        };
    }

}
