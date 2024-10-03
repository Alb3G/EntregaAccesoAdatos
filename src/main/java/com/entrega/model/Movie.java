package com.entrega.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representan las películas con las que vamos a operar
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private String id;
    private String title;
    private Integer year;
    private String director;
    private String genre;
}
