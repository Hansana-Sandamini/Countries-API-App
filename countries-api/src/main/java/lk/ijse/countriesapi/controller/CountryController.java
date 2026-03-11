package lk.ijse.countriesapi.controller;

import lk.ijse.countriesapi.model.Country;
import lk.ijse.countriesapi.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "http://localhost:3000")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Country>> searchCountries(
            @RequestParam(required = false, defaultValue = "") String q) {
        return ResponseEntity.ok(countryService.searchCountries(q));
    }
}
