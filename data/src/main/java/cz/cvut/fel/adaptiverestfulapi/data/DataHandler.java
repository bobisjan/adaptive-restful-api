package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


public abstract class DataHandler extends Filter {

    @Override
    public void process(Context context) throws FilterException {
        this.resign(context);
    }

    // TODO define abstract CRUD operations

}
