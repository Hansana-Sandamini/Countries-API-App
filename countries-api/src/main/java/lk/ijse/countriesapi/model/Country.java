package lk.ijse.countriesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private Name name;
    private String[] capital;
    private String region;
    private Long population;
    private Flags flags;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String common;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Flags {
        private String png;
        @JsonProperty("alt")
        private String description;
    }

    public String getCountryName() {
        return name != null ? name.getCommon() : null;
    }

    public String getCapitalCity() {
        return (capital != null && capital.length > 0) ? capital[0] : "N/A";
    }

    public String getFlagUrl() {
        return flags != null ? flags.getPng() : null;
    }
}
