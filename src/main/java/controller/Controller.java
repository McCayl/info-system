package controller;

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
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    private Album getAlbum(String title) {
        for (Album el : model.getAlbums()) {
            if (el.getTitle().equals(title)) {
                return el;
            }
        }
        Album album = new Album();
        album.setTitle(title);
        album.setTrackList(new ArrayList<>());
        model.getAlbums().add(album);
        return album;
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

    private boolean isInvalidIndex(int index) {
        if (index < 0) {
            return true;
        }
        return index >= model.getTrackList().size();
    }

    private boolean isInvalidTrack(Track track) {
        if (track == null) {
            return true;
        }
        if (track.getTitle() == null) {
            return true;
        }
        if (track.getAlbumTitle() == null) {
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

    public void addTrack(Track track) {
        if (isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (model.getTrackList().contains(track)) {
            view.printTrackExist();
            return;
        }
        if (!(track.getAlbumTitle().equals("not"))) {
            getAlbum(track.getAlbumTitle()).getTrackList().add(track);
        }
        model.getTrackList().add(track);
    }

    public void add(Track... tracks) {
        for (int i = 0; i != tracks.length; i++) {
            addTrack(tracks[i]);
        }
    }

    public void set(int index, Track track) {
        if (isInvalidIndex(index) || isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (!(track.getAlbumTitle().equals("not"))) {
            getAlbum(track.getAlbumTitle()).getTrackList().set(index, track);
        }
        model.getTrackList().set(index, track);
    }

    public void set(int[] indexes, Track[] tracks) {
        for (int i = 0; i != indexes.length; i++) {
            set(indexes[i], tracks[i]);
        }
    }

    public void del(int index) {
        if (isInvalidIndex(index)) {
            view.printWrongMessage();
            return;
        }
        Track el = model.getTrackList().get(index);
        if (!(el.getTitle().equals("not"))) {
            getAlbum(el.getTitle()).getTrackList().remove(el);
        }
        if (getAlbum(el.getTitle()).getTrackList().isEmpty()) {
            model.getAlbums().remove(getAlbum(el.getTitle()));
        }
        model.getTrackList().remove(el);
    }

    public void del(int... indexes) {
        for (int i = 0; i != indexes.length; i++) {
            del(indexes[i]);
        }
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
                    del(delInd);
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
            del(index-1);
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
