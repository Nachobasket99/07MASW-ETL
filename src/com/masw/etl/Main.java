package com.masw.etl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        for (String path : args) {
            processFile(path);
        }
    }

    private static void processFile(String path) {
        List<String> lines = new ArrayList<>();
        File file = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.readLine();
            String line;
            do {
                line = bufferedReader.readLine();
                lines.add(line);
            } while (Objects.nonNull(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<DataEntry> data = lines.stream()
                .filter(Objects::nonNull)
                .map(Main::mapLineToObject).collect(Collectors.toList());

        File output = new File("resources/output_v2.csv");
        PrintWriter pw;
        try {
            pw = new PrintWriter(output);

            data.stream()
                    .filter(DataEntry::isValid)
                    .map(Main::mapObjectToCSVLine)
                    .forEach(pw::println);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static DataEntry mapLineToObject(String line) {
        String[] elements = line.split(",");

        return new DataEntry(
                elements[2],
                elements[3],
                elements[4],
                elements[5],
                elements[6],
                getPlatforms(elements)
        );
    }

    private static List<DataEntry.Platform> getPlatforms(String[] elements) {
        List<DataEntry.Platform> platforms = new ArrayList<>();
        if (elements[7].equals("1")) {
            platforms.add(DataEntry.Platform.NETFLIX);
        }
        if (elements[8].equals("1")) {
            platforms.add((DataEntry.Platform.HULU));
        }
        if (elements[9].equals("1")) {
            platforms.add((DataEntry.Platform.PRIME_VIDEO));
        }
        if (elements[10].equals("1")) {
            platforms.add((DataEntry.Platform.DISNEY_PLUS));
        }
        return platforms;
    }

    private static String mapObjectToCSVLine(DataEntry object) {
        String platforms = String.format("%s,%s,%s,%s",
                object.getPlatforms().contains(DataEntry.Platform.NETFLIX) ? 1 : 0,
                object.getPlatforms().contains(DataEntry.Platform.HULU) ? 1 : 0,
                object.getPlatforms().contains(DataEntry.Platform.PRIME_VIDEO) ? 1 : 0,
                object.getPlatforms().contains(DataEntry.Platform.DISNEY_PLUS) ? 1 : 0);

        return String.format("%s,%s,%s,%s,%s,",
                object.getTitle(),
                object.getYear(),
                object.getAge(),
                object.getRatingIdmb(),
                object.getRatingRT()) + platforms;
    }

}
