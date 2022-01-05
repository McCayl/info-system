package model;

import java.io.Serializable;
import java.util.LinkedList;

public class Album implements Serializable {
    private LinkedList <Music> trackList;
    private String title;
    
    public LinkedList <Music> getTrackList() { return trackList; }
    public void setTrackList(LinkedList <Music> trackList) { this.trackList = trackList; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
     
}