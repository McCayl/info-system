package model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private String title;
    private int year;

    public Album(String title, int year){
        this.title = title;
        this.year = year;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return year == album.year &&
                Objects.equal(title, album.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, year);
    }

}