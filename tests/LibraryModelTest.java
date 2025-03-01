package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import model.LibraryModel;
import model.Song;
import model.Album;
import model.Playlist;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Test Description: This test class verifies the functionality of the LibraryModel class.
 * It tests adding songs and albums, marking favorites, rating songs, searching by title,
 * creating playlists, and ensuring initial collections are empty.
 * 
 * Framework: JUnit 5
 */

public class LibraryModelTest {

    @Test
    public void testAddSong() {
        LibraryModel library = new LibraryModel();
        Song s1 = new Song("Hello", "Adele", "25");

        boolean addedFirst = library.addSong(s1);
        assertTrue(addedFirst, "First addition should succeed");
        
        boolean addedDuplicate = library.addSong(s1);
        assertFalse(addedDuplicate, "Duplicate song addition should fail");
        
        List<Song> results = library.searchSongByTitle("Hello");
        assertEquals(1, results.size(), "Only one instance of the song should be present");
    }

    @Test
    public void testAddAlbum() {
        LibraryModel library = new LibraryModel();
        Album album = new Album("Tapestry", "Carol King", "Rock", 1971);
        Song s1 = new Song("I Feel The Earth Move", "Carol King", "Tapestry");
        Song s2 = new Song("So Far Away", "Carol King", "Tapestry");
        album.addSong(s1);
        album.addSong(s2);
        
        boolean addedAlbum = library.addAlbum(album);
        assertTrue(addedAlbum, "Album should be added successfully");
        
        List<Song> resultsS1 = library.searchSongByTitle("I Feel The Earth Move");
        List<Song> resultsS2 = library.searchSongByTitle("So Far Away");
        assertEquals(1, resultsS1.size(), "Song 'I Feel The Earth Move' should be added");
        assertEquals(1, resultsS2.size(), "Song 'So Far Away' should be added");
        
        boolean addedAlbumAgain = library.addAlbum(album);
        assertFalse(addedAlbumAgain, "Duplicate album addition should fail");
    }

    @Test
    public void testMarkFavorite() {
        LibraryModel library = new LibraryModel();
        Song s1 = new Song("Rolling in the Deep", "Adele", "21");
        library.addSong(s1);
        
        library.markFavorite(s1);
        assertTrue(s1.isFavorite(), "Song should be marked as favorite");
        
        Set<Song> favorites = library.getFavorites();
        assertTrue(favorites.contains(s1), "Favorites should contain the song");
    }

    @Test
    public void testRateSong() {
        LibraryModel library = new LibraryModel();
        Song s1 = new Song("Hello", "Adele", "25");
        library.addSong(s1);
        
        library.rateSong(s1, 4);
        assertEquals(4, s1.getRating(), "Rating should update to 4");
        assertFalse(s1.isFavorite(), "Rating 4 should not mark the song as favorite");
        
        library.rateSong(s1, 5);
        assertEquals(5, s1.getRating(), "Rating should update to 5");
        assertTrue(s1.isFavorite(), "Rating 5 should mark the song as favorite");
    }

    @Test
    public void testSearchSongByTitle() {
        LibraryModel library = new LibraryModel();
        Song s1 = new Song("Hello", "Adele", "25");
        Song s2 = new Song("Hello", "Lionel Richie", "Can't Slow Down");
        library.addSong(s1);
        library.addSong(s2);
        
        List<Song> results = library.searchSongByTitle("hello");
        assertEquals(2, results.size(), "Search should return two songs with title 'Hello'");
    }

    @Test
    public void testCreatePlaylistAndGetPlaylist() {
        LibraryModel library = new LibraryModel();
        
        boolean created = library.createPlaylist("Chill Vibes");
        assertTrue(created, "Playlist should be created successfully");
        
        Playlist pl = library.getPlaylist("Chill Vibes");
        assertNotNull(pl, "Playlist 'Chill Vibes' should be retrievable");
        
        boolean duplicate = library.createPlaylist("Chill Vibes");
        assertFalse(duplicate, "Duplicate playlist creation should fail");
    }

    @Test
    public void testGetCollectionsInitiallyEmpty() {
        LibraryModel library = new LibraryModel();
        assertTrue(library.getSongs().isEmpty(), "Songs set should be empty initially");
        assertTrue(library.getAlbums().isEmpty(), "Albums set should be empty initially");
        assertTrue(library.getAllPlaylists().isEmpty(), "Playlists should be empty initially");
        assertTrue(library.getFavorites().isEmpty(), "Favorites should be empty initially");
    }
}
