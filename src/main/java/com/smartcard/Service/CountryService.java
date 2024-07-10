package com.smartcard.Service;

import com.smartcard.Repository.CountryRepository;
import com.smartcard.entity.Country;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country addCountry(Country country) {
        Country saved = countryRepository.save(country);
        return saved;

    }
}
