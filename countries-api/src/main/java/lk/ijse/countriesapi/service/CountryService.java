package lk.ijse.countriesapi.service;

import lk.ijse.countriesapi.model.Country;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final WebClient webClient;
    private List<Country> cachedCountries;

    public CountryService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://restcountries.com/v3.1")
                .build();
    }

    @Cacheable(value = "countries", unless = "#result == null")
    public List<Country> getAllCountries() {
        if (cachedCountries != null) {
            return cachedCountries;
        }

        List<Country> countries = webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();

        this.cachedCountries = countries;
        return countries;
    }

    public List<Country> searchCountries(String searchTerm) {
        List<Country> allCountries = getAllCountries();

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return allCountries;
        }

        String lowerCaseSearch = searchTerm.toLowerCase().trim();

        return allCountries.stream()
                .filter(country ->
                        country.getCountryName() != null &&
                                country.getCountryName().toLowerCase().contains(lowerCaseSearch) ||
                                country.getCapitalCity() != null &&
                                        country.getCapitalCity().toLowerCase().contains(lowerCaseSearch) ||
                                country.getRegion() != null &&
                                        country.getRegion().toLowerCase().contains(lowerCaseSearch)
                )
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 600000) // 10 minutes in milliseconds
    public void refreshCache() {
        System.out.println("Refreshing countries cache...");
        this.cachedCountries = webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();
        System.out.println("Cache refreshed with " +
                (cachedCountries != null ? cachedCountries.size() : 0) + " countries");
    }
}
