
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.data.Dispatcher;
import cz.cvut.fel.adaptiverestfulapi.meta.InspectionException;
import cz.cvut.fel.adaptiverestfulapi.meta.Inspector;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;


public class FilteredServlet extends HttpServlet {

    private ApplicationContext getApplicationContext() throws ServletException {
        ApplicationContext ctx = ApplicationContext.getInstance();

        if (!ctx.isInitialized()) {
            Inspector inspector = new Inspector();

            inspector.setModeler(new ModelListener());
            inspector.addConfigurator(new ConfigurationListener());

            try {
                Model model = inspector.model("cz.cvut.fel.adaptiverestfulapi.example.model");
                Configuration configuration = inspector.configuration(model);
                ApplicationContext.initialize(model, configuration);

            } catch (InspectionException e) {
                throw new ServletException(e);
            }
        }
        return ctx;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext applicationContext = this.getApplicationContext();

        Filter filter = new Dispatcher();
        HttpContext httpContext = this.input(req);

        try {
            httpContext = filter.process(httpContext, applicationContext.getModel(), applicationContext.getConfiguration());
            httpContext.response(HttpStatus.S_200, new HttpHeaders(), httpContext.getContent().toString());
            this.output(httpContext, resp);

        } catch (FilterException e) {
            e.printStackTrace();
        }
    }

    private HttpContext input(HttpServletRequest request) throws ServletException {
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

    private String content(HttpServletRequest request) throws ServletException {
        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader reader = request.getReader();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void output(HttpContext ctx, HttpServletResponse response) throws IOException {
        response.setStatus(ctx.getStatus().getCode());

        response.getWriter().write(ctx.getResponseContent());
        response.getWriter().flush();
        response.getWriter().close();
    }

}
