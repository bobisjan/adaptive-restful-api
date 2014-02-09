package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.Context;
import cz.cvut.fel.adaptiverestfulapi.core.Method;

import javax.servlet.http.HttpServletRequest;


public class RequestReader {

    public static Context context(HttpServletRequest request) {
        Context context = new Context();
        context.setMethod(Method.valueOf(request.getMethod()));
        return context;
    }

}
