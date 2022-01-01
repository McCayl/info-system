package view;

import java.util.LinkedList;
import model.Album;
import model.Music;

public class View {
    
    public void printTrack(Music track) {
        System.out.println("Track title: " + track.getName());
        System.out.println("Track genre: " + track.getGenre());
        System.out.println("Track author: " + track.getAuthor());
        System.out.println("Track length: " + track.getLength());
    }
    
    public void printTrackList(LinkedList <Music> trackList) {
        for (int i = 0; i != trackList.size(); i++) {
            printTrack(trackList.get(i));
        }
    }
    
    public void printAlbum(Album album) {
        System.out.println("Album title: " + album.getAlbumName());
        printTrackList(album.getTrackList());
    }
    
    public void printListOfAlbums(LinkedList <Album> albums) {
        System.out.println("Album title list");
        for (int i = 0; i != albums.size(); i++) {
            System.out.printf("%s ", albums.get(i).getAlbumName());
        }
    }
    
}