package view;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Track;

public class View {

    public void printWrongMessage() {
        System.out.println("Something went wrong");
    }

    public void printTrackExist() {
        System.out.println("This track is already exist");
    }

    public void actionFailed() {
        System.out.println("Action failed due to user error");
    }

    public void actionSuccess() {
        System.out.println("Action successfully completed");
    }

    public void wrongKeyTyped() {
        System.out.println("You typed to incorrect key, please try again ;)");
    }

    public void noTracksAvailable() {
        System.out.println("There are no tracks that are not in the album");
    }

    public void printTrack(Track track) {
        System.out.println(track);
    }

    public void printTrackList(ArrayList<Track> trackList) {
        if(trackList == null) {
            System.out.println("No tracks\n");
            return;
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

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("cls");
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    //add set del serialize
    public void printFirstLvlMenu(int codeOfResult) throws IOException {
        clearScreen();
        switch (codeOfResult) {
            case (-1) -> wrongKeyTyped();
            case (-2) -> actionFailed();
            case (1) -> actionSuccess();
        }
        System.out.println(
                "Music Library" +
                "\nChoose action:" +
                "\n1. Show Tracks" +
                "\n2. Show Albums" +
                "\n3. Save" +
                "\n4. Load" +
                "\n0. EXIT");
    }

    public void printSecondLvlTrackMenu(ArrayList <Track> trackList, int codeOfResult) throws IOException {
        clearScreen();
        switch (codeOfResult) {
            case (-1) -> wrongKeyTyped();
            case (-2) -> actionFailed();
            case (1) -> actionSuccess();
        }
        printTrackList(trackList);
        System.out.println(
                "Choose action:" +
                "\n1. Add Track" +
                "\n2. Edit Track" +
                "\n3. Delete Track" +
                "\n0. Back");
    }

    public void printSecondLvlAlbumMenu(ArrayList <Album> albums, int codeOfResult) throws IOException {
        clearScreen();
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        printListOfAlbums(albums);
        System.out.println(
                "Choose action:" +
                "\n1. Add Album" +
                "\n2. Edit Album" +
                "\n3. Delete Album" +
                "\n0. Back");
    }

    public void printAlbumAddMenu(int codeOfResult) {
        clearScreen();
        switch (codeOfResult) {
            case (-1) -> wrongKeyTyped();
            case (-3) -> noTracksAvailable();
            case (1) -> actionSuccess();
        }
        System.out.println(
                "Choose action:" +
                "\n1. Add a new track" +
                "\n2. Add an existing track");
    }

    public void printTrackEditMenu(Track track, int codeOfResult) throws IOException {
        clearScreen();
        switch (codeOfResult) {
            case (-1) -> wrongKeyTyped();
            case (-2) -> actionFailed();
            case (1) -> actionSuccess();
        }
        printTrack(track);
        System.out.println(
                "Choose action:" +
                "\n1. Edit name" +
                "\n2. Edit author" +
                "\n3. Edit genre" +
                "\n4. Edit length" +
                "\n5. Edit name of album" +
                "\n0. Back");
    }
}