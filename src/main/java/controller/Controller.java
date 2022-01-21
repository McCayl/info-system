package controller;

import com.google.common.collect.Multimap;
import model.Album;
import model.Model;
import model.Track;
import view.View;

import java.io.*;

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
        return album.getYear() <= 0;
    }

    public void addTrack(Track track) {
        if (isInvalidTrack(track)) {
            view.print("Track is invalid");
            return;
        }
        if (model.getTrackList().contains(track)) {
            view.print("Track doesn't exist");
            return;
        }
        model.getTrackList().add(track);
    }

    public void addTrackToAlbum(String trackTitle, String albumTitle) {
        Multimap <String, String> map = model.getAssociationMap();
        if (!(map.containsKey(albumTitle))) {
            view.print("Album doesn't exist");
            return;
        }
        if (map.containsEntry(albumTitle, trackTitle)) {
            view.print("The album already contain a track with that name");
            return;
        }
        map.put(albumTitle, trackTitle);
        view.print("Action successfully performed");
    }

    public void addAlbum(Album album) {
        if (isInvalidAlbum(album)) {
            view.print("Album is invalid");
            return;
        }
        if (model.getAlbums().contains(album)) {
            view.print("Album doesn't exist");
            return;
        }
        model.getAssociationMap().put(album.getTitle(), "");
        model.getAlbums().add(album);
    }

    private void delTrackFromAlbums(String trackTitle) {
        Multimap <String, String> map = model.getAssociationMap();
        for (String key : map.keySet()) {
            if (map.get(key).contains(trackTitle)) {
                map.remove(key, trackTitle);
            }
            view.print("Action successfully performed");
        }
    }

    public void delTrack(String trackTitle) {
        if (trackTitle == null) {
            view.print("Album name is invalid");
            return;
        }
        if (getTrack(trackTitle) == null) {
            view.print("Track doesn't exist");
            return;
        }
        Track track = getTrack(trackTitle);
        delTrackFromAlbums(trackTitle);
        model.getTrackList().remove(track);
    }

    public void delAlbumTrack(String trackTitle, String albumTitle) {
        if (albumTitle == null ||
            trackTitle == null) {
            view.print("Album or track name is invalid");
            return;
        }
        if (getAlbum(albumTitle) == null ||
            getTrack(trackTitle) == null) {
            view.print("Album or track doesn't exist");
            return;
        }
        Multimap <String, String> map = model.getAssociationMap();
        if (!(map.containsEntry(albumTitle, trackTitle))) {
            view.print("The album does not contain a track with that name");
            return;
        }
        map.remove(albumTitle, trackTitle);
    }

    public void delAlbum(String albumTitle) {
        if (albumTitle == null || getAlbum(albumTitle) == null) {
            view.print("Album doesn't exist");
            return;
        }
        model.getAssociationMap().removeAll(albumTitle);
        model.getAlbums().remove(getAlbum(albumTitle));
    }

    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(model);
    }

    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(in);
        model = (Model) objectIn.readObject();
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

    public void workWithFirstLvlMenu() {
        int choose;
        String codeOfResult = "";
        while (true) {
            view.printFirstLvlMenu(codeOfResult);
            if ((choose = isValidChoose(7)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
                continue;
            }
            switch (choose) {
                case (1):
                    workWithSecondLvlTrackListMenu();
                    break;
                case (2):
                    workWithSecondLvlAlbumListMenu();
                    break;
                case (3):
                    workWithSecondLvlAssociationsListMenu();
                    break;
                case (4):
                    workWithSecondLvlTrackListEdit();
                    codeOfResult = "";
                    break;
                case (5):
                    workWithSecondLvlAlbumListEdit();
                    codeOfResult = "";
                    break;
                case (6):
                    try {
                        String path = view.getString("Enter the path to the file for save: ");
                        File file = new File(path);
                        if (!file.exists()) {
                            if (!file.createNewFile()) {
                                throw new IOException();
                            }
                        }
                        OutputStream output = new FileOutputStream(path);
                        serialize(output);
                        codeOfResult = "Action successfully completed";
                        output.close();
                    } catch (IOException exception) {
                        codeOfResult = "Action failed due to user error(wrong path)";
                    }
                    break;
                case (7):
                    try {
                        String path = view.getString("Enter the path to the file for load: ");
                        File file = new File(path);
                        if (!file.exists()) {
                            codeOfResult = "Action failed due to user error(wrong path)";
                            continue;
                        }
                        InputStream input = new FileInputStream(path);
                        deserialize(input);
                        codeOfResult = "Action successfully completed";
                        input.close();
                    } catch (IOException | ClassNotFoundException exception) {
                        exception.printStackTrace();
                        codeOfResult = "Action failed due to user error(wrong path)";
                    }
                    break;
                case (0):
                    System.exit(0);
            }
        }
    }

    public void workWithSecondLvlTrackListMenu() {
        view.printTrackList(model.getTrackList());
        view.getString("Input something to return: ");
    }

    public void workWithSecondLvlAlbumListMenu() {
        view.printListOfAlbums(model.getAlbums());
        view.getString("Input something to return: ");
    }

    public void workWithSecondLvlAssociationsListMenu() {
        view.printListOfAssociations(model.getAssociationMap());
        view.getString("Input something to return: ");
    }

    private void workWithSecondLvlTrackListEdit() {
        int choose;
        String codeOfResult = "";
        while (true) {
            view.printSecondLvlTrackMenu(codeOfResult, false);
            if ((choose = isValidChoose(3)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
                continue;
            }
            switch (choose) {
                case (1):
                    codeOfResult = workWithAddTrackMenu();
                    break;
                case (2):
                    codeOfResult = workWithEditTrackMenu();
                    break;
                case (3):
                    codeOfResult = workWithDeleteTrackMenu();
                    break;
                case (0):
                    return;
            }
        }
    }

    private String workWithAddTrackMenu() {
        Track track = new Track();
        track.setTitle(view.getString("Input track name: "));
        track.setAuthor(view.getString("Input author of track: "));
        track.setGenre(view.getString("Input genre of track: "));
        try {
            track.setLength(Integer.parseInt(view.getString("Input length of track: ")));
        } catch (NumberFormatException exception) {
            return "Action failed due to user error(non-numeric value of the length)";
        }
        addTrack(track);
        return "Action successfully completed";
    }

    private String workWithEditTrackMenu() {
        int choose;
        Track track;
        String codeOfResult = "";
        track = getTrack(view.getString("Input name of track for edit: "));
        if(track==null){
            return "Action failed due to user error(Track doesn't exist)";
        }
        while (true) {
            view.printTrackEditMenu(track, codeOfResult);
            if ((choose = isValidChoose(4)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
                continue;
            }
            switch (choose) {
                case (1):
                    track.setTitle(view.getString("Input new name of track: "));
                    codeOfResult = "Action successfully completed";
                    break;
                case (2):
                    track.setAuthor(view.getString("Input new author of track: "));
                    codeOfResult = "Action successfully completed";
                    break;
                case (3):
                    track.setGenre(view.getString("Input new genre of track: "));
                    codeOfResult = "Action successfully completed";
                    break;
                case (4):
                    try {
                        track.setLength(Integer.parseInt(view.getString("Input new length of track (in sec): ")));
                    } catch (NumberFormatException exception) {
                        codeOfResult = "Action failed due to user error(non-numeric value of the length)";
                        break;
                    }
                    codeOfResult = "Action successfully completed";
                    break;
                case (0):
                    return "";
            }
        }
    }

    private String workWithDeleteTrackMenu() {
        Track track = getTrack(view.getString("Input name of track for delete: "));
        if (track==null) {
            return "Action failed due to user error(Track doesn't exist)";
        } else {
            delTrack(track.getTitle());
        }
        return "Action successfully completed";
    }

    private void workWithSecondLvlAlbumListEdit() {
        int choose;
        String codeOfResult = "";
        while (true) {
            view.printSecondLvlAlbumMenu(codeOfResult);
            if ((choose = isValidChoose(3)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
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

    private String workWithAddAlbumMenu() {
        int choose;
        String codeOfResult = "";
        Album album;
        try {
            album = new Album(view.getString("Input name of album: "), Integer.parseInt(view.getString("Input year of album: ")));
        }catch (NumberFormatException exception){
            return "Action failed due to user error(non-numeric value of the year)";
        }
        view.print("The album cannot be empty, so you need to create a track in it or select an existing one\n");
        while (true) {
            view.printAlbumAddMenu(codeOfResult);
            if ((choose = isValidChoose(2)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
                continue;
            }
            switch (choose) {
                case (1) -> {
                    workWithAddTrackMenu();
                    addAlbum(album);
                    addTrackToAlbum(model.getTrackList().get(model.getTrackList().size() - 1).getTitle(), album.getTitle());
                    model.getAssociationMap().remove(album.getTitle(), "");

                }
                case (2) -> {
                    if (model.getTrackList().isEmpty()) {
                        codeOfResult = "Action failed due to user error(Tracks don't exist)";
                        continue;
                    }
                    addAlbum(album);
                    String trackName = view.getString("Input name of track for add in album: ");
                    addTrackToAlbum(trackName, album.getTitle());
                    model.getAssociationMap().remove(album.getTitle(), "");
                }
            }
            if (model.getAssociationMap().containsEntry(album.getTitle(),
                    model.getTrackList().get(model.getTrackList().size() - 1).getTitle()))
                return "Action successfully completed";
            else
                return "Action failed";

        }
    }

    private String workWithEditAlbumMenu() {
        int choose;
        String albumName;
        String trackName;
        String codeOfResult = "";
        albumName = view.getString("Input name of album for edit: ");
        while (true) {
            view.printSecondLvlTrackMenu(codeOfResult, true);
            if ((choose = isValidChoose(2)) == -1) {
                codeOfResult = "You typed to incorrect key, please try again ;)";
                continue;
            }
            switch (choose) {
                case (1):
                    trackName = view.getString("Input name of track for add to album: ");
                    if(getTrack(trackName)!=null){
                        addTrackToAlbum(trackName, albumName);
                    }
                    else return "Action failed due to user error(Track doesn't exist)";
                    break;
                case (2):
                    trackName = view.getString("Input name of track for delete from album: ");
                    delAlbumTrack(trackName, albumName);
                    if(model.getAssociationMap().containsEntry(albumName, trackName)){
                        return "Action successfully completed";
                    }
                    if (model.getAssociationMap().get(albumName).isEmpty()) {
                        delAlbum(albumName);
                        return "Action successfully completed";
                    }
                    break;
                case (3):
                    int year;
                    try{
                        year = Integer.parseInt(view.getString("Input year to change: "));
                    } catch (NumberFormatException exception){
                        return "Action failed due to user error(non-numeric value of the year)";
                    }
                    getAlbum(albumName).setYear(year);
                    if(getAlbum(albumName).getYear() == year)
                        return "Action successfully performed";
                    break;
                case (0):
                    return "";
            }
        }
    }

    private String workWithDeleteAlbumMenu() {
        String albumName = view.getString("Input name of album for delete: ");
        if(getAlbum(albumName)!=null) {
            delAlbum(albumName);
            return "Action successfully completed";
        }
        else return "Action failed due to user error(Album doesn't exist)";
    }
}
