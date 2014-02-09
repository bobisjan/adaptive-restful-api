package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Method;

import javax.servlet.http.HttpServletRequest;


public class RequestReader {

    public static Context context(HttpServletRequest request) {
        Context context = new Context();
        context.setMethod(method(request.getMethod()));
        return context;
    }

    public static Method method(String string) {
        if (string.equalsIgnoreCase("GET")) {
            return Method.GET;
        } else if (string.equalsIgnoreCase("POST")) {
            return Method.POST;
        } else if (string.equalsIgnoreCase("PUT")) {
            return Method.PUT;
        } else if (string.equalsIgnoreCase("DELETE")) {
            return Method.DELETE;
        } else {
            // support for UNKNOWN?
            return Method.GET;
        }
    }

}
