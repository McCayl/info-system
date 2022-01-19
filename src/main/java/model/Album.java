package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private ArrayList <Track> trackList;
    private String title;

    public Album(){
        trackList = new ArrayList<>();
    }
    public ArrayList <Track> getTrackList() { return trackList; }
    public void setTrackList(ArrayList <Track> trackList) { this.trackList = trackList; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public void addTrack(Track track) {
        if (trackList.contains(track)) {
            System.out.println("This track is already exist");
            return;
        }
        if (!(track.getAlbumTitle().equals("not"))) {
            trackList.add(track);
        }
        else {
            System.out.println("This track is already on the album");
        }
    }
     
}