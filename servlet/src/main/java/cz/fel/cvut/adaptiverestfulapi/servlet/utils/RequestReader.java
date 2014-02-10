package cz.fel.cvut.adaptiverestfulapi.servlet.utils;

import cz.cvut.fel.adaptiverestfulapi.core.HttpContext;
import cz.cvut.fel.adaptiverestfulapi.core.Method;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class RequestReader {

    public static HttpContext context(HttpServletRequest request) {
        URL url = url(request);
        Method method = Method.valueOf(request.getMethod());
        Map<String, String> headers = headers(request);
        String content = content(request);

        if (url == null || content == null) {
            return null;
        }
        return new HttpContext(url, method, headers, content);
    }

    private static URL url(HttpServletRequest request) {
        try {
            return new URL(request.getRequestURL().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> headers(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<String, String>();
        Enumeration<String> keys = request.getHeaderNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Enumeration<String> values = request.getHeaders(key);

            while (values.hasMoreElements()) {
                String value = values.nextElement();
                headers.put(key, value);
            }
        }
        return headers;
    }

    private static String content(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader reader = request.getReader();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }

}
