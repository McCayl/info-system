package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    private ArrayList <Track> trackList;
    private ArrayList <Album> albums;

    public Model() {
        this.trackList = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public ArrayList<Track> getTrackList() { return trackList; }
    public void setTrackList(ArrayList <Track> trackList) { this.trackList = trackList; }
    public ArrayList <Album> getAlbums() { return albums; }
    public void setAlbums(ArrayList <Album> albums) { this.albums = albums; }
    
}