package cz.fel.cvut.adaptiverestfulapi.core.servlet;

import javax.servlet.ServletException;


public class FilteredServletException extends ServletException {

    public FilteredServletException(String message) {
        super(message);
    }
}
