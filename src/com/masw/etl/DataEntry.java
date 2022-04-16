package com.masw.etl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DataEntry {

    private static final double NORMALIZATION_FACTOR = 10.0;

    private String title;

    private String year;

    private String age;

    private String ratingIdmb;

    private String ratingRT;

    private List<Platform> platforms;

    private boolean valid = false;

    public DataEntry(String title, String year, String age, String ratingIdmb, String ratingRT, List<Platform> platforms) {
        this.title = title;
        this.year = year;
        this.age = age;
        this.ratingIdmb = ratingIdmb;
        this.ratingRT = ratingRT;
        this.platforms = platforms;

        this.validateData();

        if (valid) {
            this.normalizeRatings();
            this.removePlusSymbolFromAge();
        }
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public enum Platform {
        NETFLIX("Netflix"),
        PRIME_VIDEO("PrimeVideo"),
        DISNEY_PLUS("DisneyPlus"),
        HULU("Hulu");

        String value;

        Platform(String platform) {
            this.value = platform;
        }

        String getName() {
            return this.value;
        }
    }

    private void normalizeRatings() {
        String[] values = this.ratingIdmb.split("/");

        this.ratingIdmb = this.normalizeRating(Double.parseDouble(values[0]), Double.parseDouble(values[1]));

        values = this.ratingRT.split("/");

        this.ratingRT = this.normalizeRating(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
    }

    private String normalizeRating(double score, double maxScore) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);

        return df.format((score / maxScore) * NORMALIZATION_FACTOR).replace(',', '.');
    }

    private void removePlusSymbolFromAge() {
        this.age = this.age.split("[+]")[0];
    }

    private void validateData() {
        this.valid = true;

        if (anyFieldNull()) {
            this.valid = false;
        }

        if (!yearWithinRange()) {
            this.valid = false;
        }

        if (!ageIsValid()) {
            this.valid = false;
        }

        if (!ratingsAreWithinRange()) {
            this.valid = false;
        }

        if (platforms.isEmpty()) {
            this.valid = false;
        }
    }

    private boolean anyFieldNull() {
        return Stream.of(
                title,
                year,
                age,
                ratingIdmb,
                ratingRT,
                platforms
        ).anyMatch(Objects::isNull);
    }

    private boolean yearWithinRange() {
        try {
            return Integer.parseInt(year) <= LocalDate.now().get(ChronoField.YEAR)
                    && Integer.parseInt(year) >= 1950;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean ageIsValid() {
        int ageNumber;
        try {
            ageNumber = Integer.parseInt(age.split("[+]")[0]);
        } catch (NumberFormatException e) {
            ageNumber = -1;
        }
        return age.contains("+")
                && ageNumber <= 18 && ageNumber >= 0;
    }

    private boolean ratingsAreWithinRange() {
        return List.of(ratingIdmb, ratingRT).stream()
                .allMatch(rating -> {
                    if (!rating.contains("/")) {
                        return false;
                    }

                    float top = Float.parseFloat(rating.split("/")[1]);
                    float score = Float.parseFloat(rating.split("/")[0]);

                    return score >= 0 && score <= top;
                });
    }
}
