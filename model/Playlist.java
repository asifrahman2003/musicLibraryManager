package model;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class represents a user-created playlist in the music library.
 * Playlist has a name and an ordered list of songs. Songs can be added or removed,
 * allowing the user to customize their playlist.
 */

public class Playlist {
    // private fields
    private String name;
    private List<Song> songs;

    /**
     * This constructs a new Playlist with the specified name.
     * and initializes the songs list as an empty ArrayList.
     * 
     * @param name - The name of the playlist.
     */
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    // getter methods
    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }
}
