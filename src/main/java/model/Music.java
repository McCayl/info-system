package model;

public class Music {
    private String trackName;
    private String author;
    private String genre;
    private int length;
    
    public String getName() { return trackName; }
    public void setName(String name) { this.trackName = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getLength() { return length; }
    public void serLength(int length) { this.length = length; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
}