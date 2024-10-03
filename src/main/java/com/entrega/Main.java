package com.entrega;

import com.entrega.model.Movie;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Movie> movies = new ArrayList<>();
        File csv = new File("peliculas.csv");
        MovieFilter mf = new MovieFilter(movies);

        mf.readMoviesFromCSV(csv, movies);
        mf.filterMoviesByYear(1972);
    }
}
