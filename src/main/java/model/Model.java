package model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    private ArrayList <Track> trackList;
    private ArrayList <Album> albums;
    private Multimap <String, String> associationMap;

    public Model() {
        trackList = new ArrayList<>();
        albums = new ArrayList<>();
        associationMap = ArrayListMultimap.create();
    }

    public ArrayList<Track> getTrackList() { return trackList; }
    public void setTrackList(ArrayList <Track> trackList) { this.trackList = trackList; }
    public ArrayList <Album> getAlbums() { return albums; }
    public void setAlbums(ArrayList <Album> albums) { this.albums = albums; }
    public Multimap<String, String> getAssociationMap() { return associationMap; }
    public void setAssociationMap(Multimap <String, String> map) { associationMap = map; }

}