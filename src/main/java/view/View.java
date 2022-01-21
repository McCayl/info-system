package view;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.common.collect.Multimap;
import model.Album;
import model.Track;

public class View {
    Scanner scanner;
    public View() {
        scanner = new Scanner(System.in);
    }
    public void printWrongMessage() {
        System.out.println("Something went wrong");
    }

    public void printTrackExist() {
        System.out.println("This track is already exist");
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
                "Music Library" +
                "\nChoose action:" +
                "\n1. Show list of tracks" +
                "\n2. Show list of albums" +
                "\n3. Show list of associations" +
                "\n4. Work with tracks" +
                "\n5. Work with albums" +
                "\n6. Save library" +
                "\n7. Load library" +
                "\n0. EXIT");
    }

    public void printSecondLvlTrackMenu(String result, boolean isAlbum) {
        System.out.println(result + "\n");
        if(!isAlbum)
            System.out.println(
                "Choose action:" +
                "\n1. Add Track" +
                "\n2. Edit Track" +
                "\n3. Delete Track" +
                "\n0. Back");
        else
            System.out.println(
                "Choose action:" +
                        "\n1. Add Track" +
                        "\n2. Delete Track" +
                        "\n0. Back");
    }

    public void printSecondLvlAlbumMenu(String result) {
        System.out.println(result + "\n");
        System.out.println(
                "Choose action:" +
                "\n1. Add Album" +
                "\n2. Edit Album" +
                "\n3. Delete Album" +
                "\n0. Back");
    }

    public void printAlbumAddMenu(String result) {
        System.out.println(result + "\n");
        System.out.println(
                "Choose action:" +
                "\n1. Add a new track" +
                "\n2. Add an existing track");
    }

    public void printTrackEditMenu(Track track, String result) {
        System.out.println(result + "\n");
        printTrack(track);
        System.out.println(
                "Choose action:" +
                "\n1. Edit name" +
                "\n2. Edit author" +
                "\n3. Edit genre" +
                "\n4. Edit length" +
                "\n0. Back");
    }
}