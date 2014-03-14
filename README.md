# Adaptive RESTful API

An Adaptive RESTful API is a library for automatic HTTP request handling based on the application's model. It's written in Java and is dedicated to use within the Java EE applications.

*NOTICE: This project is a result of the author's work on his diploma thesis.*

## Overview

The basic idea behind the library is to process the HTTP request in the chain of filters represented by the `Filter` class. It's a responsibility of the concrete filter to decide what to do with the request, if it handles completely (creates a response), or it makes something useful with the content (eq. converts JSON to POJO) of the request and then resigns the processing to the next filter in the chain.

You can see the example flow of the request in the picture.

_TODO image_

To be able to process the request through the filters in some manner there are two concepts: `model` and `configuration` of this model. The `model` is used to describe your domain classes. It consists of entities which contain properties (attributes and relationships). The configuration allows to add, or modify the default meaning of the model in a hierarchical way.

*TODO model image*

*TODO configuration image*

## Architecture

The library is divided into seperate modules. On the base level is `core` module which depends on the `meta` module. The rest of the modules add support for more concrete types of the filter. The `example` module shows how to use the library in a "real world" application.

### meta

The `meta` module is used to build the [model](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Model.java) of the [entities](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Entity.java) with properties ([attributes](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Attribute.java) and [relationships](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Relationship.java)) and it's [configuration](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/configuration/Configuration.java). The inspection process is done by the [inspector](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/Inspector.java). The first task is to inspect concrete `package` for classes in it. Then inspects `triplets` of the field, it's getter (starts with `is` or `get`) and setter (starts with `set`) methods. The `inspector` is not responsible for creating instances of the entities and propeties, but it delegates this work to a model [listener](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/ModelInspectionListener.java). After the model is built, the configuration is inspected. The inspector goes through the model and asks configuration [listener](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/ConfigurationInspectionListener.java) for the list of variables of the property, entity or model. The configuration respects the hierarchy of the model, so if you ask for some value on the attribute, it will flow through the hierarchy of configurations to find the most specific value.

### core

This module contains probably the most essesential part of the library, a [filter](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java). The filter is used to create the chain which defines the flow of the HTTP request goes through. Each filter accepts the HTTP [context](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/HttpContext.java), model and configuration via the [process](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java#L70) method. In this method you decide what to do with the request, there are basically three options:

1. set the response and stop chaining the process,
2. do something with the request and [resign](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java#L55) the process to the next filter,
3. throw an exception if something goes wrong.

This is an example implementation of the filter:

```java
public class HelloFilter extends Filter {

    @Override
    public final HttpContext process(HttpContext httpContext, Model model, Configuration configuration) throws FilterException {
        String requestContent = httpContext.getRequestContent();
        
        if (requestContent.startsWith("Hello,")) {
            String name = requestContent.substring("Hello,".length());
            
            // set the content for next filter
            httpContext.setContent(new Hello(name));
            
            // resign the process to the next filter
            return this.resign(httpContext, model, configuration);
            
        } else {
            throw new FilterException(HttpStatus.S_400); // bad request
        }
    }

}
```

*There is no responsibility to create an HTTP context, model, nor configuration, it must be done somewhere else.*

<hr>

### caching

Adds default [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/caching/src/main/java/cz/cvut/fel/adaptiverestfulapi.caching/Cache.java) of the filter for the caching and provides abstract methods to handle the request. If the `load` method hits the cache, the filter returns the context immediately, otherwise it resigns the process and finally tries to `save` the context.

This module contains simple [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/caching/src/main/java/cz/cvut/fel/adaptiverestfulapi.caching/IfModifiedSinceCache.java) of the `If-Modified-Since` caching mechanism.

### data

The purpose of this module is to deal with content of the HTTP context. The dispatcher resolves an HTTP method, and the entity via the [router](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/HttpRouter.java), then it loads data handler from the configuration and delegates the process to him. There are available interfaces for GET, POST, PUT and DELETE methods.

The module also provides basic implementation of the data handlers for JPA's entity manager, see [persistence](https://github.com/bobisjan/adaptive-restful-api/tree/master/data/src/main/java/cz/cvut/fel/adaptiverestfulapi/data/persistence) package. *It's not the responsibility of this module to create an entity manager.*

### security

The security is divided into the two parts:

* [authentication](https://github.com/bobisjan/adaptive-restful-api/blob/master/security/src/main/java/cz/cvut/fel/adaptiverestfulapi/security/Authentication.java),
* [authorization](https://github.com/bobisjan/adaptive-restful-api/blob/master/security/src/main/java/cz/cvut/fel/adaptiverestfulapi/security/Authorization.java).

Both abstract classes provides methods, where the security process should be handled. If the authentication, resp. authorization fails, then the appropriate exception must be thrown.

This module comes with an [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/security/src/main/java/cz/cvut/fel/adaptiverestfulapi/security/basic/BasicAuthentication.java) of the HTTP Basic authentication.

### serialization

The responsibility of this module is to [serialize](https://github.com/bobisjan/adaptive-restful-api/blob/master/serialization/src/main/java/cz/cvut/fel/adaptiverestfulapi/serialization/Serializer.java#L22), resp. [deserialize](https://github.com/bobisjan/adaptive-restful-api/blob/master/serialization/src/main/java/cz/cvut/fel/adaptiverestfulapi/serialization/Serializer.java#L22) the content of the HTTP response, resp. request. The [resolver](https://github.com/bobisjan/adaptive-restful-api/blob/master/serialization/src/main/java/cz/cvut/fel/adaptiverestfulapi/serialization/Resolver.java) loads the concrete serializer from configuration and delegates the (de)serialization process to him.

The `JSON` [(de)serializer](https://github.com/bobisjan/adaptive-restful-api/blob/master/serialization/src/main/java/cz/cvut/fel/adaptiverestfulapi/serialization/application/json/JsonSerializer.java) and `plain text` [serializer](https://github.com/bobisjan/adaptive-restful-api/blob/master/serialization/src/main/java/cz/cvut/fel/adaptiverestfulapi/serialization/text/plain/PlainTextSerializer.java) are provided.

### servlet

The [FilteredServlet](https://github.com/bobisjan/adaptive-restful-api/blob/master/servlet/src/main/java/cz/cvut/fel/adaptiverestfulapi/servlet/FilteredServlet.java) class implements `service(request, response)` method to handle all HTTP communication. First off all, it asks for the `ApplicationContext` singleton, which provides current `model` and `configuration`. The second step is to create a [HttpContext](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/HttpContext.java) from the `HttpServletRequest` object. Then starts the processing through the filter's chain passing the `httpContext`, `model` and `configuration`. Finally, it sets the `HttpServletResponse` from processed `HttpContent` or catched `FilteredException`.

*You use `FilteredServlet` directly by creating an instance or subclassing it and setting the desired filter.*

<hr>

### example

An example is built using a servlet [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/ExampleServlet.java), so the [initialization](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/ApplicationContextListener.java#L28) of the `ApplicationContext` is done within the `contextInitialized` method of the `ServletContextListener`. The inspector uses these [model](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/ModelListener.java) and [configuration](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/ConfigurationListener.java) listeners. The model of the application is defined [here](https://github.com/bobisjan/adaptive-restful-api/tree/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/model), it's a simple project/issue management system. The `entity manager` is created by the [PersistentContext](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/PersistenceContext.java), the usage of persistence handlers is shown in the configuration listener. The API is capable to communicate in the JSON format, and in a read-only mode of the plain text. The application is able to handle HTTP Basic [authentication](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/security/SimpleAuthentication.java), [method](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/security/MethodAuthorization.java) and [serialization](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/security/SimpleAuthorization.java) authorization (for more details look at the [Users](https://github.com/bobisjan/adaptive-restful-api/blob/master/example/src/main/java/cz/cvut/fel/adaptiverestfulapi/example/security/Users.java) class and configuration listener).

So, an example request `GET localhost:8080/Project/2` with correctly set `Accept` and `Content-Type`
 headers to `application/json;charset=UTF-8` and `Authorization` to `Basic YWRtaW46MTIzNA==` for administator user will end up to the response:

```
{
    "started": false,
    "id": 2,
    "startedAt": null,
    "name": "Project B",
    "bugs": [ 2, 3 ],
    "manager": 4,
    "tasks": [ 3 ]
}
```

## Contact

### Author

[Jan Bobisud](https://github.com/bobisjan)

### Supervisor

[Karel Čemus](https://github.com/KarelCemus)

## License

Adaptive RESTful API is available under the LGPL license. See the LICENSE file for more info.
