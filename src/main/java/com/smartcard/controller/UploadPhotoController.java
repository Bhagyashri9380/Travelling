package com.smartcard.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo")
public class UploadPhotoController {

    @PostMapping("/upload")
    public String UploadPhoto(){
        return "upload";
    }
}
