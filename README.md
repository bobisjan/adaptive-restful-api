# Adaptive RESTful API

An Adaptive RESTful API is a library for automatic HTTP request handling based on the application's model. It's written in Java and is dedicated to use within the Java EE applications.

*NOTICE: This project is a result of the author's work on his diploma thesis.*

## Overview

The basic idea behind the library is to process the HTTP request in the chain of the filters represented by the `Filter` class. It's responsibility of the concrete filter to decide what to do with the request, if it handles completely (that means: creates the response) or makes something useful with the content (eq. converts JSON to POJO) of the request and then resigns the processing to the next filter in the chain.

You can see the example flow of the request in the picture.

_TODO image_

To be able to process the request through the filters in some manner there are two helpers: `model` and `configuration` of this model. The model consists of entities which contain properties (attributes and relationships with another entities). The configuration allows to add, or modify the default meaning of the model in a hierarchical way.

## Architecture

The library is divided into the seperate modules. On the base level is `core` module which depends on the `meta` module. The rest of the modules add support for more concrete types of the filter. The `example` module shows how to use the library in a `"real world"` application.

### meta

The `meta` module is used to build the [model](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Model.java) of the [entities](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Entity.java) with properties ([attributes](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Attribute.java) and [relationships](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/model/Relationship.java)) and it's [configuration](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/configuration/Configuration.java). The inspection process is done by the [inspector](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/Inspector.java). The first task is to inspect concrete `package` for classes in it. Then inspects `triplets` of the field, it's getter (starts with `is` or `get`) and setter (starts with `set`) methods. The `inspector` is not responsible for creating the instances of the entities and propeties, but delegates this work to it's model [listener](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/ModelInspectionListener.java). After the model is build, then the configuration is inspected. The inspector goes through the model and ask it's configuration [listener](https://github.com/bobisjan/adaptive-restful-api/blob/master/meta/src/main/java/cz/cvut/fel/adaptiverestfulapi/meta/ConfigurationInspectionListener.java) for the list of variables of the property, entity or model. The configuration respects the hierarchy of the model, so if you ask for some value on the attribute, it will flow through the hierarchy of configurations to find the most specific value.

### core

This module contains probably the most essesential part of the library, a [filter](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java). The filter is used to create the chain which defines the flow of the HTTP request goes through. Each filter accepts the HTTP [context](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/HttpContext.java), model and configuration via the [process](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java#L70) method. In this method you decide what to do with the request, there are basically three options:

1. set the response and stop chaining the process,
2. do something with the request and [resign](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/Filter.java#L55) the process to the next filter,
3. throw an exception if something goes wrong.

*There is no responsibility to create an HTTP context, model, nor configuration, it must be done somewhere else.*

<hr>

### caching

Adds default [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/caching/src/main/java/cz/cvut/fel/adaptiverestfulapi.caching/Cache.java) of the filter for the caching and provides abstract methods to handle the request. If the `load` method hits the cache, the filter returns the context immediately, otherwise it resigns the process and finally tries to `save` the context.

This module contains simple [implementation](https://github.com/bobisjan/adaptive-restful-api/blob/master/caching/src/main/java/cz/cvut/fel/adaptiverestfulapi.caching/IfModifiedSinceCache.java) of the `If-Modified-Since` caching mechanism.

### data

The purpose of this module is to deal with content of the HTTP context. The dispatcher resolves an HTTP method, and the entity via the [router](https://github.com/bobisjan/adaptive-restful-api/blob/master/core/src/main/java/cz/cvut/fel/adaptiverestfulapi/core/HttpRouter.java), then it loads data handler from the configuration and delegates the process to him. There are available interfaces for GET, POST, PUT and DELETE methods.

The module also provides basic implementation of the data handlers for JPA's entity manager, see [persistence](https://github.com/bobisjan/adaptive-restful-api/tree/master/data/src/main/java/cz/cvut/fel/adaptiverestfulapi/data/persistence) package. *It's not the responsibility of this module to create an entity manager.*

### security

*TODO*

### serialization

*TODO*

### servlet

*TODO*

<hr>

### example

*TODO*

## Contact

### Author

[Jan Bobisud](https://github.com/bobisjan)

### Supervisor

[Karel Čemus](https://github.com/KarelCemus)

## License

Adaptive RESTful API is available under the LGPL license. See the LICENSE file for more info.
