
package cz.cvut.fel.adaptiverestfulapi.data;

import cz.cvut.fel.adaptiverestfulapi.core.HttpHeaders;
import cz.cvut.fel.adaptiverestfulapi.core.HttpStatus;


public class NotFoundException extends DataException {

    public NotFoundException(String entity, String identifier) {
        super("Object for entity " + entity + " with identifier `" + identifier + "` could not be found.", HttpStatus.S_404, new HttpHeaders());
    }

}
