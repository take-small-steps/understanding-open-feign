package com.example.feigndemo.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foo-api", url = "http://jsonplaceholder.typicode.com", configuration = FooConfiguration.class)
public interface FooClient {

    @GetMapping(value = "/todos/1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Response fooGetRequest();

    @GetMapping(value = "/todos/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FooResponse fooGetRequestWithResponse(@PathVariable("todoId") int id);

    @GetMapping(value = "/todos/{todoId}/no", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FooResponse fooGetRequestWithResponse2(@PathVariable("todoId") int id);
}
