package controller;

import com.google.common.collect.Multimap;
import model.Album;
import model.Model;
import model.Track;
import view.View;

import java.io.*;

import java.util.*;

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
            view.print("Track is invalid");
            return true;
        }
        if (track.getTitle() == null) {
            view.print("Name of track is invalid");
            return true;
        }
        if (track.getAuthor() == null) {
            view.print("Author of track is invalid");
            return true;
        }
        if (track.getGenre() == null) {
            view.print("Genre of track is invalid");
            return true;
        }
        if(track.getLength()<=0){
            view.print("Length of track is invalid");
        }
        return false;
    }

    private boolean canAddTrack(Track track){
        if(isInvalidTrack(track)){
            return false;
        }
        if (model.getTrackList().contains(track)) {
            view.print("Track already exist");
            return false;
        }
        return true;
    }

    private boolean canAddAlbum(Album album){
        if(isInvalidAlbum(album)){
            return false;
        }
        if (model.getAlbums().contains(album)) {
            view.print("Album already exist");
            return false;
        }
        return true;
    }

    private boolean isInvalidAlbum(Album album) {
        if (album == null) {
            view.print("Album doesn't exist");
            return true;
        }
        if (album.getTitle() == null) {
            view.print("Name of album is invalid");
            return true;
        }
        if(album.getYear() <= 0){
            view.print("Year of album is invalid");
            return true;
        }
        return false;
    }

    public void addTrack(Track track) {
        model.getTrackList().add(track);
        view.print("Action successfully performed(track create)");
    }

    public void addTrackToAlbum(String trackTitle, String albumTitle) {
        model.getAssociationMap().put(albumTitle, trackTitle);
        view.print("Action successfully performed(track add to album)");
    }

    public void addAlbum(Album album, String trackName) {
        model.getAssociationMap().put(album.getTitle(), trackName);
        model.getAlbums().add(album);
        view.print("Action successfully performed(album create)");
    }

    private void delTrackFromAlbums(String trackTitle) {
        Multimap <String, String> map = model.getAssociationMap();
        List <Map.Entry <String, String>> list = new ArrayList<>(map.entries());
        list.forEach((Map.Entry <String, String> it) -> map.remove(it.getKey(), trackTitle));
    }

    public void delTrack(String trackTitle) {
        Track track = getTrack(trackTitle);
        delTrackFromAlbums(trackTitle);
        model.getTrackList().remove(track);
        view.print("Action successfully performed(track delete)");
    }

    public void delAlbumTrack(String trackTitle, String albumTitle) {
        Multimap <String, String> map = model.getAssociationMap();
        map.remove(albumTitle, trackTitle);
        view.print("Action successfully performed(track delete from album)");
    }

    public void delAlbum(String albumTitle) {
        model.getAssociationMap().removeAll(albumTitle);
        model.getAlbums().remove(getAlbum(albumTitle));
        view.print("Action successfully performed(album delete)");
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
        while (true) {
            view.printFirstLvlMenu();
            if ((choose = isValidChoose(7)) == -1) {
                view.printIncorrectKey();
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
                    break;
                case (5):
                    workWithSecondLvlAlbumListEdit();
                    break;
                case (6):
                    try {
                        String path = view.askAndGetString("Enter the path to the file(with name) " +
                                "or just name(file will be created in the program directory) for save: ");
                        File file = new File(path);
                        file.createNewFile();
                        OutputStream output = new FileOutputStream(path);
                        serialize(output);
                        view.print("Action successfully completed");
                        output.close();
                    } catch (IOException exception) {
                        view.print("Action failed due to user error(wrong path)");
                    } catch (SecurityException exception) {
                        view.print("Action failed due to user error(there is no access to the file by this path)");
                    }
                    break;
                case (7):
                    try {
                        String path = view.askAndGetString("Enter the path to the file for load: ");
                        File file = new File(path);
                        if (!file.exists()) {
                            view.print("Action failed due to user error(wrong path)");
                            continue;
                        }
                        InputStream input = new FileInputStream(path);
                        deserialize(input);
                        input.close();
                        view.print("Action successfully completed");
                    } catch (IOException | ClassNotFoundException exception) {
                        view.print("Action failed due to user error(wrong path)");
                    } catch (SecurityException exception) {
                        view.print("Action failed due to user error(there is no access to the file by this path)");
                    }
                    break;
                case (0):
                    System.exit(0);
            }
        }
    }

    public void workWithSecondLvlTrackListMenu() {
        view.printTrackList(model.getTrackList());
        view.askAndGetString("Input something to return: ");
    }

    public void workWithSecondLvlAlbumListMenu() {
        view.printListOfAlbums(model.getAlbums());
        view.askAndGetString("Input something to return: ");
    }

    public void workWithSecondLvlAssociationsListMenu() {
        view.printListOfAssociations(model.getAssociationMap());
        view.askAndGetString("Input something to return: ");
    }

    private void workWithSecondLvlTrackListEdit() {
        int choose;
        while (true) {
            view.printSecondLvlTrackMenu(false);
            if ((choose = isValidChoose(3)) == -1) {
                view.printIncorrectKey();
                continue;
            }
            switch (choose) {
                case (1):
                    workWithAddTrackMenu();
                    break;
                case (2):
                    workWithEditTrackMenu();
                    break;
                case (3):
                    workWithDeleteTrackMenu();
                    break;
                case (0):
                    return;
            }
        }
    }

    private String workWithAddTrackMenu() {
        Track track = new Track(view.askAndGetString("Input track name: "),
                view.askAndGetString("Input author of track: "),
                view.askAndGetString("Input genre of track: "),
                view.askAndGetInt("Input length of track: "));
        if(canAddTrack(track)) {
            addTrack(track);
            return track.getTitle();
        }
        return null;
    }

    private void workWithEditTrackMenu() {
        int choose;
        Track track = getTrack(view.askAndGetString("Input name of track for edit: "));
        if(isInvalidTrack(track)){
            return;
        }
        while (true) {
            view.printTrackEditMenu(track);
            if ((choose = isValidChoose(3)) == -1) {
                view.printIncorrectKey();
                continue;
            }
            switch (choose) {
                case (1):
                    track.setAuthor(view.askAndGetString("Input new author of track: "));
                    view.print("Action successfully completed");
                    break;
                case (2):
                    track.setGenre(view.askAndGetString("Input new genre of track: "));
                    view.print("Action successfully completed");
                    break;
                case (3):
                    track.setLength(view.askAndGetInt("Input new length of track (in sec): "));
                    view.print("Action successfully completed");
                    break;
                case (0):
                    return;
            }
        }
    }

    private void workWithDeleteTrackMenu() {
        Track track = getTrack(view.askAndGetString("Input name of track for delete: "));
        if (!isInvalidTrack(track)) {
            delTrack(track.getTitle());
        }
    }

    private void workWithSecondLvlAlbumListEdit() {
        int choose;
        while (true) {
            view.printSecondLvlAlbumMenu();
            if ((choose = isValidChoose(3)) == -1) {
                view.printIncorrectKey();
                continue;
            }
            switch (choose) {
                case (1):
                    workWithAddAlbumMenu();
                    break;
                case (2):
                    workWithEditAlbumMenu();
                    break;
                case (3):
                    workWithDeleteAlbumMenu();
                    break;
                case (0):
                    return;
            }
        }
    }

    private void workWithAddAlbumMenu() {
        int choose;
        Album album = new Album(view.askAndGetString("Input name of album: "), view.askAndGetInt("Input year of album: "));
        if(!canAddAlbum(album)){
            return;
        }
        view.print("The album cannot be empty, so you need to create a track in it or select an existing one\n");
        while (true) {
            view.printAlbumAddMenu();
            if ((choose = isValidChoose(2)) == -1) {
                view.printIncorrectKey();
                continue;
            }
            switch (choose) {
                case (1) -> {
                    String trackName = workWithAddTrackMenu();
                    if(trackName != null) {
                        addAlbum(album, trackName);
                        return;
                    }
                }
                case (2) -> {
                    if (model.getTrackList().isEmpty()) {
                        view.print("Action failed due to user error(Tracks don't exist)");
                        continue;
                    }
                    String trackName = view.askAndGetString("Input name of track for add in album: ");
                    addAlbum(album, trackName);
                    return;
                }
                case (0) -> {
                    return;
                }
            }
        }
    }

    private void workWithEditAlbumMenu() {
        int choose;
        String albumName;
        String trackName;
        albumName = view.askAndGetString("Input name of album for edit: ");
        if(isInvalidAlbum(getAlbum(albumName))){
            return;
        }
        while (true) {
            view.printSecondLvlTrackMenu(true);
            if ((choose = isValidChoose(3)) == -1) {
                view.printIncorrectKey();
                continue;
            }
            switch (choose) {
                case (1):
                    trackName = view.askAndGetString("Input name of track for add to album: ");
                    if(isInvalidTrack(getTrack(trackName))){
                        continue;
                    }
                    if(!model.getAssociationMap().containsEntry(albumName,trackName)){
                        addTrackToAlbum(trackName, albumName);
                        continue;
                    }
                    break;
                case (2):
                    trackName = view.askAndGetString("Input name of track for delete from album: ");
                    if(isInvalidTrack(getTrack(trackName))){
                        continue;
                    }
                    delAlbumTrack(trackName, albumName);
                    if (model.getAssociationMap().get(albumName).isEmpty()) {
                        delAlbum(albumName);
                    }
                    break;
                case (3):
                    int year;
                    year = view.askAndGetInt("Input year to change: ");
                    getAlbum(albumName).setYear(year);
                    break;
                case (0):
                    return;
            }
        }
    }

    private void workWithDeleteAlbumMenu() {
        String albumName = view.askAndGetString("Input name of album for delete: ");
        if(!isInvalidAlbum(getAlbum(albumName))) {
            delAlbum(albumName);
        }
    }
}
