package cz.cvut.fel.adaptiverestfulapi.serialization;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Filter;
import cz.cvut.fel.adaptiverestfulapi.core.FilterException;


public abstract class Serializer extends Filter {

    @Override
    public void process(Context context) throws FilterException {
        this.resign(context);
    }

    protected abstract void serialize(Context context);

    protected abstract void deserialize(Context context);

}
