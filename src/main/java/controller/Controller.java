package controller;

import com.google.common.collect.Multimap;
import model.Album;
import model.Model;
import model.Track;
import view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    private Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    private Album getAlbum(String albumTitle) {
        for (Album el : model.getAlbums()) {
            if (el.getTitle().equals(albumTitle)) {
                return el;
            }
        }
        return null;
    }

    private Track getTrack(String trackTitle) {
        for (Track el : model.getTrackList()) {
            if (el.getTitle().equals(trackTitle)) {
                return el;
            }
        }
        return null;
    }

    private int getIndexOfAlbum(String title){
        for(int i = 0; i < model.getAlbums().size(); i++){
            if(model.getAlbums().get(i).getTitle().equals(title)){
                return i;
            }
        }
        return -1;
    }

    private int getIndexOfTrack(String title) {
        for (int i = 0; i < model.getTrackList().size(); i++) {
            if (model.getTrackList().get(i).getTitle().equals(title)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isInvalidTrackIndex(int index) {
        if (index < 0) { return true; }
        return index >= model.getTrackList().size();
    }

    private boolean isInvalidAlTrackIndex(String albumTitle, int trackIndex) {
        return getAlbum(albumTitle).getTrackList().get(trackIndex) == null;
    }

    private boolean isInvalidTrack(Track track) {
        if (track == null) {
            return true;
        }
        if (track.getTitle() == null) {
            return true;
        }
        if (track.getAuthor() == null) {
            return true;
        }
        if (track.getGenre() == null) {
            return true;
        }
        return track.getLength() <= 0;
    }

    private boolean isInvalidAlbum(Album album) {
        if (album == null) {
            return true;
        }
        if (album.getTitle() == null) {
            return true;
        }
        if (album.getTrackList() == null) {
            return true;
        }
        return album.getYear() <= 0;
    }

    private boolean isInvalidAlIndex(int index) {
        if (index < 0) { return true; }
        return index >= model.getAlbums().size();
    }

    public void addTrack(Track track) {
        if (isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (model.getTrackList().contains(track)) {
            view.printTrackExist();
            return;
        }
        model.getTrackList().add(track);
    }

    public void addTrackToAlbum(Track track, String albumTitle) {
        if (isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (albumTitle == null) {
            view.printWrongMessage();
            return;
        }
        Album currentAlbum = getAlbum(albumTitle);
        if (currentAlbum == null) {
            view.printWrongMessage();
            return;
        }
        if (currentAlbum.getTrackList().contains(track)) {
            view.printTrackExist();
            return;
        }
        if (!(model.getTrackList().contains(track))) {
            model.getTrackList().add(track);
        }
        currentAlbum.getTrackList().add(track);
        model.getAssosiationsMap().put(albumTitle, track.getTitle());
    }

    private ArrayList <String> getAlTrackTitles(Album album) {
        ArrayList <String> result = new ArrayList<>();
        for (Track track : album.getTrackList()) {
            result.add(track.getTitle());
        }
        return result;
    }

    public void addAlbum(Album album) {
        if (isInvalidAlbum(album)) {
            view.printWrongMessage();
            return;
        }
        if (model.getAlbums().contains(album)) {
            view.printWrongMessage();
            return;
        }
        model.getAssosiationsMap().putAll(album.getTitle(), getAlTrackTitles(album));
        model.getAlbums().add(album);
    }

    public void setTrack(int index, Track track) {
        if (isInvalidTrackIndex(index) || isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (!(model.getTrackList().contains(track))) {
            view.printWrongMessage();
            return;
        }
        model.getTrackList().set(index, track);
    }

    public void setAlbumTrack(String albumTitle, int index, Track track) {
        if (getAlbum(albumTitle) == null ||
            getTrack(track.getTitle()) == null) {
            view.printWrongMessage();
            return;
        }
        if (isInvalidAlTrackIndex(albumTitle, index)) {
            view.printWrongMessage();
            return;
        }
        getAlbum(albumTitle).getTrackList().set(index, track);
    }

    public void setAlbum(int index, Album album) {
        if (isInvalidAlbum(album) || isInvalidAlIndex(index)) {
            view.printWrongMessage();
            return;
        }
        if (!(model.getAlbums().contains(album))) {
            view.printWrongMessage();
            return;
        }
        model.getAlbums().set(index, album);
    }

    private void delTrackFromAlbums(String trackTitle) {
        Multimap <String, String> map = model.getAssosiationsMap();
        Track track = getTrack(trackTitle);
        for (String key : map.keySet()) {
            if (map.get(key).contains(trackTitle)) {
                map.remove(key, trackTitle);
                getAlbum(key).getTrackList().remove(track);
            }
        }
    }

    public void delTrack(String trackTitle) {
        if (trackTitle == null) {
            view.printWrongMessage();
            return;
        }
        if (getTrack(trackTitle) == null) {
            view.printWrongMessage();
            return;
        }
        Track track = getTrack(trackTitle);
        delTrackFromAlbums(trackTitle);
        model.getTrackList().remove(track);
    }

    public void delAlbumTrack(String trackTitle, String albumTitle) {
        if (albumTitle == null ||
            trackTitle == null) {
            view.printWrongMessage();
            return;
        }
        if (getAlbum(albumTitle) == null ||
            getTrack(trackTitle) == null) {
            view.printWrongMessage();
            return;
        }
        model.getAssosiationsMap().remove(albumTitle, trackTitle);
        getAlbum(albumTitle).getTrackList().remove(getTrack(trackTitle));
    }

    public void delAlbum(String albumTitle) {
        if (albumTitle == null || getAlbum(albumTitle) == null) {
            view.printWrongMessage();
            return;
        }
        model.getAssosiationsMap().removeAll(albumTitle);
        model.getAlbums().remove(getAlbum(albumTitle));
    }

    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream objouts = new ObjectOutputStream(out);
        objouts.writeObject(model);
    }

    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objins = new ObjectInputStream(in);
        model = (Model) objins.readObject();
    }

    private int isValidChoose(int countOfChoose) {
        int choose;
        try {
            Scanner scanner = new Scanner(System.in);
            choose = scanner.nextInt();
            if (choose > countOfChoose || choose < 0) {
                return -1;
            }
        } catch (InputMismatchException exception) {
            return -1;
        }
        return choose;
    }

    public void workWithFirstLvlMenu() throws IOException {
        int choose;
        int codeOfResult = 0;
        while (true) {
            view.printFirstLvlMenu(codeOfResult);
            if ((choose = isValidChoose(4)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    workWithSecondLvlTrackMenu();
                    break;
                case (2):
                    workWithSecondLvlAlbumMenu();
                    break;
                case (3):
                    System.out.println("Enter the path to the folder to save: ");
                    try {
                        Scanner scanner = new Scanner(System.in);
                        String path = scanner.nextLine() + "/library";
                        File file = new File(path);
                        if (!file.exists()) {
                            if (!file.createNewFile()) {
                                throw new IOException();
                            }
                        }
                        OutputStream output = new FileOutputStream(path);
                        serialize(output);
                        codeOfResult = 1;
                        output.close();
                    } catch (IOException exception) {
                        codeOfResult = -2;
                    }
                    break;
                case (4):
                    System.out.println("Enter the path to the folder to load: ");
                    try {
                        Scanner scanner = new Scanner(System.in);
                        String path = scanner.nextLine() + "/library";
                        File file = new File(path);
                        if (!file.exists()) {
                            codeOfResult = -2;
                            continue;
                        }
                        InputStream input = new FileInputStream(path);
                        deserialize(input);
                        codeOfResult = 1;
                        input.close();
                    } catch (IOException | ClassNotFoundException exception) {
                        exception.printStackTrace();
                        codeOfResult = -2;
                    }
                    break;
                case (0):
                    System.exit(0);
            }
        }
    }

    private void workWithSecondLvlTrackMenu() throws IOException {
        int choose;
        int codeOfResult = 0;
        while (true) {
            view.printSecondLvlTrackMenu(model.getTrackList(), codeOfResult);
            if ((choose = isValidChoose(3)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    codeOfResult = workWithAddTrackMenu(false);
                    break;
                case (2):
                    codeOfResult = workWithEditTrackMenu(model.getTrackList(), false);
                    break;
                case (3):
                    codeOfResult = workWithDeleteTrackMenu(model.getTrackList());
                    break;
                case (0):
                    return;
            }
        }
    }

    private int workWithAddTrackMenu(boolean isAlbumTrack) {
        View.clearScreen();
        Scanner scanner = new Scanner(System.in);
        Track track = new Track();
        System.out.print("Input track name: ");
        track.setTitle(scanner.nextLine());
        System.out.print("\nInput author of track: ");
        track.setAuthor(scanner.nextLine());
        System.out.print("\nInput genre of track: ");
        track.setGenre(scanner.nextLine());
        if (!isAlbumTrack) {
            System.out.print("\nInput album title of track: ");
            track.setAlbumTitle(scanner.nextLine());
        } else {
            track.setAlbumTitle("not");
        }
        System.out.print("\nInput length of track: ");
        try {
            track.setLength(scanner.nextInt());
        } catch (InputMismatchException exception) {
            return -2;
        }
        addTrack(track);
        return 1;
    }

    private int workWithEditTrackMenu(ArrayList<Track> trackList, boolean isAlbumTrack) throws IOException {
        View.clearScreen();
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Input index of track for edit: ");
        int choose;
        int index;
        int codeOfResult = 0;
        try {
            index = scanner.nextInt();
            index--;
        } catch (InputMismatchException exception) {
            return -2;
        }
        while (true) {
            view.printTrackEditMenu(trackList.get(index), codeOfResult);
            if ((choose = isValidChoose(5)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    System.out.println("Input new name of track: ");
                    trackList.get(index).setTitle(scanner.nextLine());
                    codeOfResult = 1;
                    break;
                case (2):
                    System.out.println("Input new author of track: ");
                    trackList.get(index).setAuthor(scanner.nextLine());
                    codeOfResult = 1;
                    break;
                case (3):
                    System.out.println("Input new genre of track: ");
                    trackList.get(index).setGenre(scanner.nextLine());
                    codeOfResult = 1;
                    break;
                case (4):
                    System.out.println("Input new length of track (in sec): ");
                    try {
                        int length = scanner.nextInt();
                        trackList.get(index).setLength(length);
                    } catch (InputMismatchException exception) {
                        codeOfResult = -2;
                        break;
                    }
                    codeOfResult = 1;
                    break;
                case (5):

                    boolean last = false;
                    System.out.println("Input new album name of track: ");
                    Track newTrack = (Track) trackList.get(index).clone();
                    String oldName = newTrack.getAlbumTitle();
                    scanner.nextLine();
                    newTrack.setAlbumTitle(scanner.nextLine());
                    if (isAlbumTrack) {
                        trackList.remove(index);
                        if(trackList.isEmpty()) {
                            last = true;
                            model.getAlbums().remove(getIndexOfAlbum(oldName));
                        }
                    }
                    int delInd = getIndexOfTrack(newTrack.getTitle());
                    delTrack(delInd);
                    addTrack(newTrack);
                    codeOfResult = 1;
                    if (last) {
                        return 1;
                    }
                    break;
                case (0):
                    return 0;
            }
        }
    }

    private int workWithDeleteTrackMenu(ArrayList<Track> trackList) {
        View.clearScreen();
        System.out.println("Input index of track for delete: ");
        int index;
        if ((index = isValidChoose(trackList.size() - 1)) == -1) {
            return -2;
        } else {
            delTrack(index-1);
        }
        return 1;
    }

    private void workWithSecondLvlAlbumMenu() throws IOException {
        int choose;
        int codeOfResult = 0;
        while (true) {
            view.printSecondLvlAlbumMenu(model.getAlbums(), codeOfResult);
            if ((choose = isValidChoose(3)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    codeOfResult = workWithAddAlbumMenu();
                    break;
                case (2):
                    codeOfResult = workWithEditAlbumMenu();
                    break;
                case (3):
                    codeOfResult = workWithDeleteAlbumMenu();
                    break;
                case (0):
                    return;
            }
        }
    }

    private int addExistTrackToAlbum(Album album) {
        int count = 1;
        int index;
        View.clearScreen();
        ArrayList<Track> tmp = new ArrayList<>();
        for (int i = 0; i < model.getTrackList().size(); i++) {
            if (model.getTrackList().get(i).getAlbumTitle().equals("not")) {
                System.out.println(count + ". " + model.getTrackList().get(i).getTitle());
                tmp.add(model.getTrackList().get(i));
                count++;
            }
        }
        count--;
        if (count == 0) {
            return -3;
        }
        while (true) {
            System.out.print("Choose index of track: ");
            if ((index = isValidChoose(count)) == -1) {
                view.wrongKeyTyped();
            } else {
                album.addTrack(tmp.get(index));
                return 1;
            }
        }
    }

    private int workWithAddAlbumMenu() {
        View.clearScreen();
        int choose;
        int codeOfResult = 0;
        Scanner scanner = new Scanner(System.in);
        Album album = new Album();
        System.out.print("Input album name: ");
        album.setTitle(scanner.nextLine());
        System.out.println("The album cannot be empty, so you need to create a track in it or select an existing one\n");
        while (true) {
            view.printAlbumAddMenu(codeOfResult);
            if ((choose = isValidChoose(2)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    workWithAddTrackMenu(true);
                    model.getAlbums().add(album);
                    model.getTrackList().get(model.getTrackList().size() - 1).setAlbumTitle(album.getTitle());
                    album.addTrack(model.getTrackList().get(model.getTrackList().size() - 1));
                    return 1;
                case (2):
                    int result;
                    if ((result = addExistTrackToAlbum(album)) == -3) {
                        codeOfResult = -3;
                    }
                    else
                        return result;
            }
        }
    }

    private int workWithEditAlbumMenu() throws IOException {
        View.clearScreen();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input index of album for edit: ");
        int choose;
        int index;
        int codeOfResult = 0;
        try {
            index = scanner.nextInt() - 1;
        } catch (InputMismatchException exception) {
            return -2;
        }
        while (true) {
            view.printSecondLvlTrackMenu(model.getAlbums().get(index).getTrackList(), codeOfResult);
            if ((choose = isValidChoose(3)) == -1) {
                codeOfResult = -1;
                continue;
            }
            switch (choose) {
                case (1):
                    workWithAddTrackMenu(true);
                    break;
                case (2):
                    workWithEditTrackMenu(model.getAlbums().get(index).getTrackList(), true);
                    break;
                case (3):
                    workWithDeleteTrackMenu(model.getAlbums().get(index).getTrackList());
                    if (model.getAlbums().get(index).getTrackList().isEmpty()) {
                        model.getAlbums().remove(index);
                        return 1;
                    }
                    break;
                case (0):
                    return 0;
            }
        }
    }

    private int workWithDeleteAlbumMenu() {
        View.clearScreen();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input index of album for delete: ");
        int index;
        try {
            index = scanner.nextInt() - 1;
        } catch (InputMismatchException exception) {
            return -2;
        }
        for (Track track : model.getTrackList()) {
            if (track.getAlbumTitle().equals(model.getAlbums().get(index).getTitle())) {
                track.setAlbumTitle("not");
            }
        }
        model.getAlbums().remove(index);
        return 1;
    }
}
