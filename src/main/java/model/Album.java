package model;

import java.io.Serializable;
import java.util.LinkedList;

public class Album implements Serializable {
    private LinkedList <Music> trackList;
    private String title;

    public Album(){
        trackList = new LinkedList<>();
    }
    public LinkedList <Music> getTrackList() { return trackList; }
    public void setTrackList(LinkedList <Music> trackList) { this.trackList = trackList; }
    public void addTrack(Music track) {
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
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
     
}