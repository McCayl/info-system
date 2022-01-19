package view;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Track;

public class View {

    /**
     * Need menu, print methods or something :^) -Ok, it will be done
     */

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
    
    public void printAlbum(Album album) {
        System.out.println("Album title: " + album.getTitle());
        printTrackList(album.getTrackList());
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
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        else if(codeOfResult == -2){
            actionFailed();
        }
        else if(codeOfResult == 1){
            actionSuccess();
        }
        System.out.println("\tMusic Library by 32\n\n");
        System.out.println("  Choose action:");
        System.out.println("1. Show Tracks");
        System.out.println("2. Show Albums");
        System.out.println("3. Save");
        System.out.println("4. Load");
        System.out.println("0. EXIT");
    }

    public void printSecondLvlTrackMenu(ArrayList <Track> trackList, int codeOfResult) throws IOException {
        clearScreen();
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        else if(codeOfResult == -2){
            actionFailed();
        }
        else if(codeOfResult == 1){
            actionSuccess();
        }
        System.out.println("\tMusic Library by 32\n");
        printTrackList(trackList);
        System.out.println("  Choose action:");
        System.out.println("\n");
        System.out.println("1. Add Track");
        System.out.println("2. Edit Track");
        System.out.println("3. Delete Track");
        System.out.println("0. Back");
    }

    public void printSecondLvlAlbumMenu(ArrayList <Album> albums, int codeOfResult) throws IOException {
        clearScreen();
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        System.out.println("\tMusic Library by 32\n");
        printListOfAlbums(albums);
        System.out.println("  Choose action:");
        System.out.println("\n");
        System.out.println("1. Add Album");
        System.out.println("2. Edit Album");
        System.out.println("3. Delete Album");
        System.out.println("0. Back");
    }

    public void printAlbumAddMenu(int codeOfResult){
        clearScreen();
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        else if(codeOfResult == -3){
            noTracksAvailable();
        }
        else if(codeOfResult ==1){
            actionSuccess();
        }
        System.out.println("\tMusic Library by 32\n");
        System.out.println("  Choose action:");
        System.out.println("\n");
        System.out.println("1. Add a new track");
        System.out.println("2. Add an existing track");
    }

    public void printTrackEditMenu(Track track, int codeOfResult) throws IOException {
        clearScreen();
        if(codeOfResult == -1){
            wrongKeyTyped();
        }
        else if(codeOfResult == -2){
            actionFailed();
        }
        else if(codeOfResult == 1){
            actionSuccess();
        }
        System.out.println("Selected track: ");
        printTrack(track);
        System.out.println("\nChoose action: ");
        System.out.println("1. Edit name");
        System.out.println("2. Edit author");
        System.out.println("3. Edit genre");
        System.out.println("4. Edit length");
        System.out.println("5. Edit name of album");
        System.out.println("0. Back");
    }
}