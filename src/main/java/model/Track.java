package model;

import com.google.common.base.Objects;

import java.io.Serializable;

public class Track implements Serializable {
    private String title;
    private String author;
    private String genre;
    private int length;
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getFormatLength() {
        int min = length/60;
        int sec = length%60;
        return min + ":" + sec;
    }

    @Override
    public String toString() {
        return "Name: " + title + ", author: " + author + ", genre: " + genre + ", length: " + getFormatLength();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return length == track.length &&
                Objects.equal(title, track.title) &&
                Objects.equal(author, track.author) &&
                Objects.equal(genre, track.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, author, genre, length);
    }
}