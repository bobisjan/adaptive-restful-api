package cz.fel.cvut.adaptiverestfulapi.core.filters.model;

import cz.fel.cvut.adaptiverestfulapi.core.Context;
import cz.fel.cvut.adaptiverestfulapi.core.Filter;
import cz.fel.cvut.adaptiverestfulapi.core.FilterException;


public abstract class DataHandler extends Filter {

    @Override
    public void process(Context context) throws FilterException {
        this.resign(context);
    }

    // TODO define abstract CRUD operations

}
