package org.acme;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class MyRoute extends EndpointRouteBuilder {
    @Override
    public void configure() {
        from(timer("tick").period(5000)).log("tick");
    }
}
