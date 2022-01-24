package view;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.common.collect.Multimap;
import model.Album;
import model.Track;

public class View {
    private final Scanner scanner;
    public View() {
        scanner = new Scanner(System.in);
    }

    public void printIncorrectKey(){
        System.out.println("You typed to incorrect key, please try again ;)");
    }

    public String askAndGetString(String str) {
        System.out.print(str);
        String string;
        do {
            string = scanner.nextLine();
        } while(string.isEmpty());
        return string;
    }

    public int askAndGetInt(String str){
        System.out.print(str);
        int number;
        do {
            try {
                number = getInt();
            }
            catch (NoSuchElementException exception){
                System.out.println("You didn't enter a number, try again");
                number = 0;
            }
        } while (number <= 0);
        return number;
    }

    public int getInt(){
        int num;
        num = scanner.nextInt();
        if (num<=0){
           System.out.print("The number must be greater than 0, try again");
        }
        return num;
    }

    public void print(String str){
        System.out.println(str);
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
        if(albums == null || albums.isEmpty()) {
            System.out.println("List of albums is empty\n");
            return;
        }
        System.out.println("Album title list: ");
        for (int i = 0; i != albums.size(); i++) {
            System.out.printf(i+1 + ". %s\n", albums.get(i).getTitle());
        }
        System.out.println("\n");
    }

    public void printListOfAssociations(Multimap<String, String> associationMap){
        if(associationMap == null || associationMap.isEmpty()) {
            System.out.println("List of associations is empty\n");
            return;
        }
        System.out.println("List of associations: ");
        for (String album: associationMap.keySet()) {
                System.out.print(album + ": " + associationMap.get(album) + "\n");
        }
    }

    public void printFirstLvlMenu(String result) {
        System.out.println(result);
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

    public void printSecondLvlTrackMenu(boolean isAlbum) {
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

    public void printSecondLvlAlbumMenu() {
        System.out.println(
                """
                        Choose action:
                        1. Add Album
                        2. Edit Album
                        3. Delete Album
                        0. Back""");
    }

    public void printAlbumAddMenu() {
        System.out.println(
                """
                        Choose action:
                        1. Add a new track
                        2. Add an existing track
                        0. Back""");
    }

    public void printTrackEditMenu(Track track) {

        printTrack(track);
        System.out.println(
                """
                        Choose action:
                        1. Edit author
                        2. Edit genre
                        3. Edit length
                        0. Back""");
    }
}