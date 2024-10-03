package com.entrega;

import com.entrega.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MovieFilter {
    private final ArrayList<Movie> movies;

    public MovieFilter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void filterMoviesByYear(Integer year) {
        File dir = new File("salida");
        checkIfDirExists(dir);

        File csvFile = new File(dir, year + ".csv");
        boolean fileExists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             CSVPrinter printer = new CSVPrinter(fw, CSVFormat.DEFAULT)) {

            if (!fileExists) {
                csvFile.createNewFile();
            }
            for (Movie m : this.movies) {
                if (m.getYear() > year) {
                    printer.printRecord(m.getId(), m.getTitle(), m.getYear(), m.getDirector(), m.getGenre());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void readMoviesFromCSV(File csv, ArrayList<Movie> movies) {
        try(BufferedReader bfr = new BufferedReader(new FileReader(csv))) {
            String line;
            while((line = bfr.readLine()) != null) {
                var lineParts = line.split(",");
                movies.add(new Movie(
                        lineParts[0], // id
                        lineParts[1], // title
                        Integer.parseInt(lineParts[2]), // year
                        lineParts[3], // director
                        lineParts[4] // genre
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkIfDirExists(File dir) {
        if(dir.exists() && dir.isDirectory()) {
            for(File file : Objects.requireNonNull(dir.listFiles())) {
                file.delete();
            }
            dir.delete();
        } else {
            dir.delete();
        }
        dir.mkdirs();
    }
}
