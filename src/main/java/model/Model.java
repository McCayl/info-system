package model;

import java.util.LinkedList;

public class Model {
    private LinkedList <Music> trackList;
    private LinkedList <Album> albums;
    
    public LinkedList <Music> getTrackList() { return trackList; }
    public void setTrackList(LinkedList <Music> trackList) { this.trackList = trackList; }
    public LinkedList <Album> getAlbums() { return albums; }
    public void setAlbums(LinkedList albums) { this.albums = albums; }
    
}