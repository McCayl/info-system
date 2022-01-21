package model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private ArrayList <Track> trackList;
    private String title;
    private int year;

    public Album(){
        trackList = new ArrayList<>();
    }
    public Album(String title, int year){
        this.title = title;
        this.year = year;
        trackList = new ArrayList<>();
    }
    public ArrayList <Track> getTrackList() { return trackList; }
    public void setTrackList(ArrayList <Track> trackList) { this.trackList = trackList; }
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
                Objects.equal(trackList, album.trackList) &&
                Objects.equal(title, album.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(trackList, title, year);
    }

}