package com.masw.etl;

import java.util.List;

public class DataEntry {

    private String title;

    private String year;

    private String age;

    private String ratingIdmb;

    private String ratingRT;

    private List<Platform> platforms;

    public DataEntry(String title, String year, String age, String ratingIdmb, String ratingRT, List<Platform> platforms) {
        this.title = title;
        this.year = year;
        this.age = age;
        this.ratingIdmb = ratingIdmb;
        this.ratingRT = ratingRT;
        this.platforms = platforms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRatingIdmb() {
        return ratingIdmb;
    }

    public void setRatingIdmb(String ratingIdmb) {
        this.ratingIdmb = ratingIdmb;
    }

    public String getRatingRT() {
        return ratingRT;
    }

    public void setRatingRT(String ratingRT) {
        this.ratingRT = ratingRT;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }


    public enum Platform {
        NETFLIX("Netflix"),
        PRIME_VIDEO("PrimeVideo"),
        DISNEY_PLUS("DisneyPlus"),
        HULU("Hulu");

        Platform(String platform) {
        }
    }
}
