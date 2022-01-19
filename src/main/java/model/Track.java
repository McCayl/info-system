package model;

import java.io.Serializable;

public class Track implements Serializable, Cloneable {
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
    public void setLength(int length) { this.length = length; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getAlbumTitle() { return albumTitle; }
    public void setAlbumTitle(String albumName) { this.albumTitle = albumName; }

    public String getFormatLength() {
        int min = length/60;
        int sec = length%60;
        return min + ":" + sec;
    }

    @Override
    public String toString() {
        if(albumTitle.equals("not")) {
            return "Name: " + title + ", author: " + author + ", genre: " + genre + ", length: " + getFormatLength();
        }
        else {
            return "Name: " + title + ", author: " + author + ", genre: " + genre + ", album: " + albumTitle +
                    ", length: " + getFormatLength();
        }
    }

    public Object clone() {
        Track track = new Track();
        track.setTitle(title);
        track.setAuthor(author);
        track.setGenre(genre);
        track.setAlbumTitle(albumTitle);
        track.setLength(length);
        return track;
    }
}