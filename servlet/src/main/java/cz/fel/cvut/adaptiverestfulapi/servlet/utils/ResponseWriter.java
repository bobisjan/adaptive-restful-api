package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;

import javax.servlet.http.HttpServletResponse;


public class ResponseWriter {

    public static void write(HttpContext httpContext, HttpServletResponse response) {
        response.setStatus(httpContext.getStatusCode().getCode());
    }

}
