
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.core.*;
import cz.cvut.fel.adaptiverestfulapi.meta.configuration.Configuration;
import cz.cvut.fel.adaptiverestfulapi.meta.model.Model;
import cz.cvut.fel.adaptiverestfulapi.serialization.SerializationException;
import cz.cvut.fel.adaptiverestfulapi.serialization.Serializer;

import java.util.LinkedList;
import java.util.List;


public class ExampleSerializer implements Serializer {

    public static final String MIME = "text/plain;charset=UTF-8";

    private List<HttpHeader> responseHeaders() {
        List<HttpHeader> headers = new LinkedList<>();
        List<HttpHeaderValue> values = new LinkedList<>();
        values.add(new HttpHeaderValue(MIME));
        headers.add(HttpHeader.create(HttpHeaders.ContentType, values));
        return headers;
    }

    @Override
    public HttpContext serialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        HttpHeaders httpHeaders = new HttpHeaders(this.responseHeaders());

        Object obj = httpContext.getContent();
        String response = "";

        if (obj != null) {
            response = obj.toString();
        }

        httpContext.response(HttpStatus.S_200, httpHeaders, response);
        return httpContext;
    }

    @Override
    public HttpContext deserialize(HttpContext httpContext, Model model, Configuration configuration) throws SerializationException {
        httpContext.setContent(httpContext.getRequestContent());
        return httpContext;
    }

}
