package cz.fel.cvut.adaptiverestfulapi.core.filters.serialization;

import cz.fel.cvut.adaptiverestfulapi.core.Context;
import cz.fel.cvut.adaptiverestfulapi.core.Filter;
import cz.fel.cvut.adaptiverestfulapi.core.FilterException;


public abstract class Serializer extends Filter {

    @Override
    public void process(Context context) throws FilterException {
        this.resign(context);
    }

    public abstract void serialize(Context context);

    public abstract void deserialize(Context context);

}
