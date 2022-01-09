package model;

import java.io.Serializable;
import java.util.LinkedList;

public class Model implements Serializable {
    private LinkedList <Music> trackList;
    private LinkedList <Album> albums;

    public Model(){
        this.trackList = new LinkedList<>();
        this.albums = new LinkedList<>();

    }

    public LinkedList <Music> getTrackList() { return trackList; }
    public void setTrackList(LinkedList <Music> trackList) { this.trackList = trackList; }
    public LinkedList <Album> getAlbums() { return albums; }
    public void setAlbums(LinkedList <Album> albums) { this.albums = albums; }
    
}