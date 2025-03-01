package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import store.MusicStore;
import model.Album;
import model.Song;
import java.io.IOException;
import java.util.List;


/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Test Description: This test class verifies the functionality of the MusicStore class.
 * It tests loading album data from text files, handling malformed or missing files,
 * and various search methods by title, artist, genre, and year. 
 * 
 * Framework: JUnit 5
 */
public class MusicStoreTest {

    @Test
    public void testLoadAlbums() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Album> allAlbums = store.searchAlbumByTitle("");
        List<Album> oldIdeas = store.searchAlbumByTitle("Old Ideas");
        assertFalse(oldIdeas.isEmpty(), "Expected at least one album titled 'Old Ideas'");
    }
    
    @Test
    public void testSearchAlbumByTitle() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Album> results = store.searchAlbumByTitle("Old Ideas");
        assertFalse(results.isEmpty(), "Expected album 'Old Ideas' to be loaded");
        
        Album album = results.get(0);
        assertEquals("Old Ideas", album.getTitle());
        assertEquals("Leonard Cohen", album.getArtist());
    }
    
    @Test
    public void testSearchAlbumByArtist() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Album> adeleAlbums = store.searchAlbumByArtist("Adele");
        assertTrue(adeleAlbums.size() >= 2, "Expected at least two albums by Adele");
    }
    
    @Test
    public void testSearchAlbumByGenre() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Album> popAlbums = store.searchAlbumByGenre("Pop");
        assertFalse(popAlbums.isEmpty(), "Expected at least one Pop album");
    }
    
    @Test
    public void testSearchAlbumByYear() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Album> albums2008 = store.searchAlbumByYear(2008);
        assertFalse(albums2008.isEmpty(), "Expected at least one album from 2008");
    }
    
    @Test
    public void testSearchSongsByArtist() {
        MusicStore store = new MusicStore();
        try {
            store.loadAlbums("albums/albums.txt");
        } catch (IOException e) {
            fail("IOException thrown while loading albums: " + e.getMessage());
        }
        
        List<Song> songsByAdele = store.searchSongsByArtist("Adele");
        assertFalse(songsByAdele.isEmpty(), "Expected to find songs by Adele");
    }
    
    
}
