package com.masw.etl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataEntry {

    private String title;

    private String year;

    private String age;

    private String ratingIdmb;

    private String ratingRT;

    private List<Platform> platforms;


    public enum Platform {
        NETFLIX("Netflix"),
        PRIME_VIDEO("PrimeVideo"),
        DISNEY_PLUS("DisneyPlus"),
        HULU("Hulu");

        Platform(String platform) {
        }
    }
}
