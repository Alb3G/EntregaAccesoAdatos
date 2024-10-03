package com.entrega;

import com.entrega.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Esta clase se va a encargar de mediante el metodo de leer peliculas desde el csv
 * rellenará la lista de peliculas para poder filtrarla por el año mas tarde.
 * @author Alberto Guzman
 */
public class MovieFilter {
    private final ArrayList<Movie> movies;

    public MovieFilter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Crea un archivo csv con el parametro pasado por argumento y dentro del csv
     * solo estarán las películas posteriores a dicho año.
     * @param year Parámetro para filtrar las peliculas
     */
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

    /**
     * Método para leer películas y almacenarlas en la lista
     * @param csv archivo a leer
     * @param movies lista a rellenar con películas
     */
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

    /**
     * Este método comprueba si hay un directorio existente si lo hay lo vacia lo borra
     * y crea otro sino lo crea directamente.
     * @param dir directorio a comprobar si existe o no para crearlo.
     */
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
