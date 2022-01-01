package model;

import java.util.LinkedList;

public class Album {
    private LinkedList <Music> trackList;
    private String albumName;
    
    public LinkedList <Music> getTrackList() { return trackList; }
    public void setTrackList(LinkedList <Music> trackList) { this.trackList = trackList; }
    public String getAlbumName() { return albumName; }
    public void setAlbumName(String albumName) { this.albumName = albumName; }
     
}