package model;

import java.io.Serializable;

public class Music implements Serializable {
    private String title;
    private String author;
    private String genre;
    private String albumTitle;
    private int length;
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getLength() { return length; }
    public void serLength(int length) { this.length = length; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getAlbumTitle() { return albumTitle; }
    public void setAlbumTitle(String albumName) { this.albumTitle = albumName; }

}