package view;

import java.util.LinkedList;
import model.Album;
import model.Music;

public class View {

    /**
     * Need menu, print methods or something :^)
     */

    public void printWrongMessage() {
        System.out.println("Something went wrong");
    }

    public void printTrackExist() {
        System.out.println("This track is already exist");
    }

    public void printTrack(Music track) {
        System.out.printf("%s %s %s %s %d",
                track.getTitle(),
                track.getAuthor(),
                track.getGenre(),
                track.getAlbumTitle(),
                track.getLength());
    }

    public void printNotAlbumTrack(Music track) {
        System.out.printf("%s %s %s %d",
                track.getTitle(),
                track.getAuthor(),
                track.getGenre(),
                track.getLength());
    }

    public void printTrackList(LinkedList <Music> trackList) {
        for (int i = 0; i != trackList.size(); i++) {
            if (!(trackList.get(i).getAlbumTitle().equals("not"))) {
                printTrack(trackList.get(i));
            } else {
                printNotAlbumTrack(trackList.get(i));
            }
        }
    }
    
    public void printAlbum(Album album) {
        System.out.println("Album title: " + album.getTitle());
        printTrackList(album.getTrackList());
    }
    
    public void printListOfAlbums(LinkedList <Album> albums) {
        System.out.println("Album title list");
        for (int i = 0; i != albums.size(); i++) {
            System.out.printf("%s ", albums.get(i).getTitle());
        }
    }
    
}