package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.Context;

import javax.servlet.http.HttpServletResponse;


public class ResponseWriter {

    public static void write(Context context, HttpServletResponse response) {
        response.setStatus(context.getStatusCode().getCode());
    }

}
