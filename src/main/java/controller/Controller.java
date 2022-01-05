package controller;

import model.Album;
import model.Model;
import model.Music;
import view.View;

import java.io.*;
import java.util.LinkedList;

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
        album.setTrackList(new LinkedList<>());
        return album;
    }

    private boolean isInvalidIndex(int index) {
        if (index < 0) {
            return true;
        }
        return index >= model.getTrackList().size();
    }

    private boolean isInvalidTrack(Music track) {
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

    public void add(Music track) {
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

    public void add(Music... tracks) {
        for (int i = 0; i != tracks.length; i++) {
            add(tracks[i]);
        }
    }

    public void set(int index, Music track) {
        if (isInvalidIndex(index) || isInvalidTrack(track)) {
            view.printWrongMessage();
            return;
        }
        if (!(track.getAlbumTitle().equals("not"))) {
            getAlbum(track.getAlbumTitle()).getTrackList().set(index, track);
        }
        model.getTrackList().set(index, track);
    }

    public void set(int[] indxs, Music[] tracks) {
        for (int i = 0; i != indxs.length; i++) {
            set(indxs[i], tracks[i]);
        }
    }

    public void del(int index) {
        if (isInvalidIndex(index)) {
            view.printWrongMessage();
            return;
        }
        Music el = model.getTrackList().get(index);
        if (!(el.getTitle().equals("not"))) {
            getAlbum(el.getTitle()).getTrackList().remove(el);
        }
        if (getAlbum(el.getTitle()).getTrackList().isEmpty()) {
            model.getAlbums().remove(getAlbum(el.getTitle()));
        }
        model.getTrackList().remove(el);
    }

    public void del(int... indxs) {
        for (int i = 0; i != indxs.length; i++) {
            del(indxs[i]);
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

}