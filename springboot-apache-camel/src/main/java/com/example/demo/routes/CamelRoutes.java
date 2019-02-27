package com.example.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class CamelRoutes extends RouteBuilder {
 @Override
 public void configure() throws Exception {
  from("direct:firstRoute")
   .log("Camel body: ${body}");
  from("file:E://inputFolder?noop=true").to("file:E://outputFolder");
 }
}