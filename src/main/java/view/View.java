package view;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.common.collect.Multimap;
import model.Album;
import model.Track;

public class View {
    private Scanner scanner;
    public View() {
        scanner = new Scanner(System.in);
    }

    public String getString(String str) {
        System.out.print(str);
        String string;
        do {
            string = scanner.nextLine();
        } while(string.isEmpty());
        return string;
    }

    public void print(String str){
        System.out.print(str);
    }

    public void printTrack(Track track) {
        System.out.println(track);
    }

    public void printTrackList(ArrayList<Track> trackList) {
        if(trackList == null || trackList.isEmpty()) {
            System.out.println("List of tracks is empty\n");
            return;
        }
        else {
            System.out.println("List of tracks: ");
        }
        for (int i = 0; i != trackList.size(); i++) {
            System.out.print((i+1) + ". ");
            printTrack(trackList.get(i));
        }
    }
    
    public void printListOfAlbums(ArrayList <Album> albums) {
        if(albums == null) {
            System.out.println("No albums\n");
            return;
        }
        System.out.println("Album title list: ");
        for (int i = 0; i != albums.size(); i++) {
            System.out.printf(i+1 + ". %s\n", albums.get(i).getTitle());
        }
        System.out.println("\n");
    }

    public void printListOfAssociations(Multimap<String, String> associationMap){
        System.out.println("List of associations: ");
        for (String album: associationMap.keySet()) {
                System.out.print(album + ": " + associationMap.get(album) + "\n");
        }
    }

    public void printFirstLvlMenu(String result) {
        System.out.println(result + "\n");
        System.out.println(
                """
                        Music Library
                        Choose action:
                        1. Show list of tracks
                        2. Show list of albums
                        3. Show list of associations
                        4. Work with tracks
                        5. Work with albums
                        6. Save library
                        7. Load library
                        0. EXIT""");
    }

    public void printSecondLvlTrackMenu(String result, boolean isAlbum) {
        System.out.println(result + "\n");
        if(!isAlbum)
            System.out.println(
                    """
                            Choose action:
                            1. Add Track
                            2. Edit Track
                            3. Delete Track
                            0. Back""");
        else
            System.out.println(
                    """
                            Choose action:
                            1. Add Track
                            2. Delete Track
                            3. Change Year
                            0. Back""");
    }

    public void printSecondLvlAlbumMenu(String result) {
        System.out.println(result + "\n");
        System.out.println(
                """
                        Choose action:
                        1. Add Album
                        2. Edit Album
                        3. Delete Album
                        0. Back""");
    }

    public void printAlbumAddMenu(String result) {
        System.out.println(result + "\n");
        System.out.println(
                """
                        Choose action:
                        1. Add a new track
                        2. Add an existing track""");
    }

    public void printTrackEditMenu(Track track, String result) {
        System.out.println(result + "\n");
        printTrack(track);
        System.out.println(
                """
                        Choose action:
                        1. Edit name
                        2. Edit author
                        3. Edit genre
                        4. Edit length
                        0. Back""");
    }
}