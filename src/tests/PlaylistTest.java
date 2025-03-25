package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Playlist;
import model.Song;
import java.util.List;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Test Description: This test class verifies the behavior of the Playlist class.
 * It tests the creation of a playlist, adding songs, adding multiple songs,
 * and removing songs from the playlist.
 * 
 * Framework: JUnit 5
 */

public class PlaylistTest {

    @Test
    public void testCreatePlaylist() {
        Playlist pl = new Playlist("My Playlist");
        assertEquals("My Playlist", pl.getName());
        assertNotNull(pl.getSongs());
        assertTrue(pl.getSongs().isEmpty(), "Playlist should be empty initially");
    }

    @Test
    public void testAddSong() {
        Playlist pl = new Playlist("My Playlist");
        Song s1 = new Song("Song A", "Artist A", "Album A");
        pl.addSong(s1);
        List<Song> songs = pl.getSongs();
        assertEquals(1, songs.size(), "Playlist should have one song after adding");
        assertEquals(s1, songs.get(0), "The added song should be present in the playlist");
    }

    @Test
    public void testAddMultipleSongs() {
        Playlist pl = new Playlist("My Playlist");
        Song s1 = new Song("Song A", "Artist A", "Album A");
        Song s2 = new Song("Song B", "Artist B", "Album B");
        pl.addSong(s1);
        pl.addSong(s2);
        List<Song> songs = pl.getSongs();
        assertEquals(2, songs.size(), "Playlist should have two songs after adding two songs");
        assertEquals(s1, songs.get(0), "First song should be s1");
        assertEquals(s2, songs.get(1), "Second song should be s2");
    }

    @Test
    public void testRemoveSong() {
        Playlist pl = new Playlist("My Playlist");
        Song s1 = new Song("Song A", "Artist A", "Album A");
        Song s2 = new Song("Song B", "Artist B", "Album B");
        pl.addSong(s1);
        pl.addSong(s2);
        
        pl.removeSong(s1);
        List<Song> songs = pl.getSongs();
        assertEquals(1, songs.size(), "Playlist should have one song after removing one");
        assertFalse(songs.contains(s1), "Playlist should not contain the removed song");
        
        pl.removeSong(s2);
        assertTrue(pl.getSongs().isEmpty(), "Playlist should be empty after removing all songs");
    }

    @Test
    public void testRemoveNonExistentSong() {
        Playlist pl = new Playlist("My Playlist");
        Song s1 = new Song("Song A", "Artist A", "Album A");
        
        pl.removeSong(s1);
        assertTrue(pl.getSongs().isEmpty(), "Playlist should remain empty when removing a non-existent song");
        
        Song s2 = new Song("Song B", "Artist B", "Album B");
        pl.addSong(s1);
        pl.removeSong(s2);
        assertEquals(1, pl.getSongs().size(), "Playlist should still have one song if a non-existent song is removed");
        assertTrue(pl.getSongs().contains(s1), "Playlist should still contain the existing song");
    }
}
