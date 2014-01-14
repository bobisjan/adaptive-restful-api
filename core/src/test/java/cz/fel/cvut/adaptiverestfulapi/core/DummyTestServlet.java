package cz.fel.cvut.adaptiverestfulapi.core;

import cz.fel.cvut.adaptiverestfulapi.core.Context;
import cz.fel.cvut.adaptiverestfulapi.core.Filter;
import cz.fel.cvut.adaptiverestfulapi.core.servlet.FilteredServlet;


public class DummyTestServlet extends FilteredServlet {

    public DummyTestServlet() {
        this.chain = new Filter() {
            @Override
            public void process(Context context) {

            }
        };
    }

}
