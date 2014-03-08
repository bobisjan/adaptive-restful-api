
package cz.cvut.fel.adaptiverestfulapi.servlet;

import cz.cvut.fel.adaptiverestfulapi.core.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;


public class FilteredServlet extends HttpServlet {

    protected Filter filter;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = ApplicationContext.getInstance();

        try {
            HttpContext httpContext = this.read(req);
            httpContext = this.filter.process(httpContext, applicationContext.getModel(), applicationContext.getConfiguration());
            this.write(resp, httpContext);

        } catch (FilterException e) {
            this.error(e, resp);
        }
    }

    protected HttpContext read(HttpServletRequest request) throws IOException {
        String url = request.getRequestURL().toString();
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        HttpHeaders headers = this.headers(request);
        String content = this.content(request);

        return new HttpContext(url, httpMethod, headers, content);
    }

    protected void write(HttpServletResponse response, HttpContext httpContext) throws IOException  {
        response.setStatus(httpContext.getStatus().getCode());

        response.getWriter().write(httpContext.getResponseContent());
        response.getWriter().flush();
        response.getWriter().close();
    }

    protected void error(FilterException e, HttpServletResponse response) throws IOException {
        response.setStatus(e.getStatus().getCode());

        for (String name : e.getHeaders()) {
            response.setHeader(name, e.getHeaders().getString(name));
        }

        response.getWriter().write(e.getLocalizedMessage());
        response.getWriter().flush();
        response.getWriter().close();
    }

    private HttpHeaders headers(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> keys = request.getHeaderNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Enumeration<String> values = request.getHeaders(key);

            while (values.hasMoreElements()) {
                String value = values.nextElement();
                headers.add(key, value);
            }
        }
        return headers;
    }

    private String content(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = request.getReader();

        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
