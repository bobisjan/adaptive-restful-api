
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.servlet.ServletFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExampleFilter extends ServletFilter {

    public ExampleFilter(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, new Dispatcher());
    }

}
