package com.example.dynamo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamo")
public class DynamoController {

    @GetMapping
    public String hello() {
        return "Hello Dynamo";
    }
}
