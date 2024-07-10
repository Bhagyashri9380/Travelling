package com.smartcard.controller;

import com.smartcard.Service.CountryService;
import com.smartcard.entity.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Country")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

@PostMapping("/addCountries")
    public ResponseEntity<Country>addCountry(@RequestBody Country country){
        Country country1 = countryService.addCountry(country);
        return new ResponseEntity<>(country1, HttpStatus.CREATED);

    }
}
