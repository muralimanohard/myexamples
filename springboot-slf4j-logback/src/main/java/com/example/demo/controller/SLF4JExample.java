package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SLF4JExample {
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    @GetMapping
    public String testMethod() {

        logger.trace("This is a trace log example");
        logger.info("This is an info log example");
        logger.debug("This is a debug log example");
        logger.error("This is an error log example");
        logger.warn("This is a warn log example");

        return "Check the Console Logs";
    }
}
