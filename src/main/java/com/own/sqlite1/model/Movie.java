package com.own.sqlite1.model;

/**
 * Created by alo_m on 11/04/2018.
 */

public class Movie {
    private String idMovie;
    private String name;
    private String director;
    private String clasification;
    private int year;
    private int duration;

    public Movie(String idMovie, String name, String director, String clasification, int year, int duration) {
        this.idMovie = idMovie;
        this.name = name;
        this.director = director;
        this.clasification = clasification;
        this.year = year;
        this.duration = duration;
    }

    public Movie() {
        this("","", "", "",0,0);
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getClasification() {
        return clasification;
    }

    public void setClasification(String clasification) {
        this.clasification = clasification;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "idMovie='" + idMovie + '\'' +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", clasification='" + clasification + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                '}';
    }
}
