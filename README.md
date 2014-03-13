# Adaptive RESTful API

An Adaptive RESTful API is a library for automatic HTTP request handling based on the application's model. It's written in Java and is dedicated to use within the Java EE applications.

*NOTICE: This project is a result of the author's work on his diploma thesis.*

## Overview

The basic idea behind the library is to process the HTTP request in the chain of the filters represented by the `Filter` class. It's the responsibility of the concrete filter to decide what to do with the request, if it handles completely (that means: creates the response) or makes something useful with the content (eq. converts JSON to POJO) of the request and then resigns the processing to the next filter in the chain.

You can see the example flow of the request in the picture.

_TODO image_

To be able to process the request through the filters in some manner there are two helpers: `model` and `configuration` of this model. The model consists of entities which contain properties (attributes and relationships with another entities). The configuration allows to add, or modify the default meaning of the model in a hierarchical way.

## Architecture

The library is divided into the seperate modules. On the base level is `core` module which depends on the `meta` module. The rest of the modules add support for more concrete types of the filter. The `example` module shows how to use the library in the `"real world"` application.

### meta

*TODO*

### core

*TODO*

<hr>

### caching

*TODO*

### data

*TODO*

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
