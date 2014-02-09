package cz.fel.cvut.adaptiverestfulapi.servlet;

import javax.servlet.ServletException;


public class FilteredServletException extends ServletException {

    public FilteredServletException(String message) {
        super(message);
    }
}
