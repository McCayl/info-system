package model;

public class Music {
    private String name;
    private String author;
    private String albrum;
    private int length;
    private GenreOfMusic genre;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getAlbrum() { return albrum; }
    public int getLength() { return length; }
    public void serLength(int length) { this.length = length; }
    public GenreOfMusic getGenre() { return genre; }
    public void setGenre(GenreOfMusic genre) { this.genre = genre; }
    
}