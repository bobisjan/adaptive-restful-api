
package cz.cvut.fel.adaptiverestfulapi.meta;


/**
 * Exception for inspection process.
 */
public class InspectionException extends Exception {

    public InspectionException() {
        this("Something gets broken during the inspection process.");
    }

    public InspectionException(String message) {
        super(message);
    }

}
