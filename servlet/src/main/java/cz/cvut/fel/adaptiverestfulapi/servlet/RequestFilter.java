
package cz.cvut.fel.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;


public class RequestFilter extends Filter {

    private HttpServletRequest request;

    public RequestFilter(HttpServletRequest request, Filter next) {
        super(next);
        this.request = request;
    }

    @Override
    public HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        httpContext = this.input(this.request);
        return this.resign(httpContext, model, configuration);
    }

    private HttpContext input(HttpServletRequest request) throws FilterException {
        String uri = request.getRequestURL().toString();
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        HttpHeaders headers = this.headers(request);
        String content = this.content(request);

        return new HttpContext(uri, httpMethod, headers, content);
    }

    private HttpHeaders headers(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
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

    private String content(HttpServletRequest request) throws FilterException {
        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader reader = request.getReader();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            throw new FilterException(e);
        }
    }

}
