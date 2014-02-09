package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.Context;

import javax.servlet.http.HttpServletRequest;


public class RequestReader {

    public static Context context(HttpServletRequest request) {
        Context context = new Context();
        context.setMethod(request.getMethod());
        return context;
    }

}
