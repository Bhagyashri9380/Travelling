package com.smartcard.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Country")
public class AddCountry {

    @PostMapping("/addCountry")
    public String addController(){
        return "done";
    }
}
