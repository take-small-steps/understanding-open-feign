package com.example.feigndemo;

import com.example.feigndemo.client.FooClient;
import com.example.feigndemo.client.FooResponse;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

import java.io.IOException;

@SpringBootTest
@ImportAutoConfiguration({FeignAutoConfiguration.class})
class FooClientTest {


    @Autowired
    FooClient fooClient;

    private static final String SCRIPT_REG_EX = "<script([^'\"]|\"[^\"]*\"|'[^']*')*?</script>";
    private static final String STYLE_REG_EX = "<style([^'\"]|\"[^\"]*\"|'[^']*')*?</style>";
    private static final String HTML_REG_EX = "<(/)?[a-zA-Z]+[^>]*>";


    @Test
    void name() throws IOException {

        extracted();

    }

    @Test
    void name22() {
        FooResponse fooResponse = fooClient.fooGetRequestWithResponse(10);
        System.out.println(fooResponse);
    }

    @Test
    void name33() {
        FooResponse fooResponse = fooClient.fooGetRequestWithResponse2(10);
        System.out.println(fooResponse);
    }

    private void extracted() {
        Response response = null;

        response = fooClient.fooGetRequest();
        Response.Body body = response.body();
        String s = String.valueOf(body);
        System.out.println(s);

    }
}