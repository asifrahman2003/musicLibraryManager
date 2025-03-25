package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import model.LibraryModel;
import store.MusicStore;
import view.MusicLibraryView;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Test Description: This test class verifies the behavior of the MusicLibraryView class, which
 * provides a text-based user interface for the music library. It uses simulated user input and
 * output capturing to test various menu options and interactive functionality such as searching,
 * adding songs/albums, creating playlists, and marking songs as favorite.
 * 
 * Framework: JUnit 5
 */
public class MusicLibraryViewTest {

    @Test
    public void testExitOptionInView() {

        String simulatedInput = "0\n";
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(testOut));
        
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        MusicLibraryView view = new MusicLibraryView(library, store);
        
        view.start();
        
        System.setIn(originalIn);
        System.setOut(originalOut);
        
        String output = testOut.toString();
        
        assertTrue(output.contains("Exiting. Goodbye!"), "Output should contain 'Exiting. Goodbye!'");
    }
    
    private String runViewWithInput(String input, LibraryModel library, MusicStore store) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setIn(testIn);
        System.setOut(new PrintStream(testOut));
        
        MusicLibraryView view = new MusicLibraryView(library, store);
        view.start();
        
        System.setIn(originalIn);
        System.setOut(originalOut);
        
        return testOut.toString();
    }
    
    /**
     * Test the exit option - simulates user entering "0" to exit.
     */
    @Test
    public void testExitOption() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Exiting. Goodbye!"), "Output should contain 'Exiting. Goodbye!'");
    }
    
    @Test
    public void testSearchStoreEmpty() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore(); // store is empty
        String simulatedInput = "1\nSomeAlbum\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Enter album title to search in store:"), "Should prompt for album title");
        assertTrue(output.contains("No matching albums found in store."), "Should indicate no album found");
    }
    
    @Test
    public void testSearchLibraryEmpty() {
        LibraryModel library = new LibraryModel(); // library is empty
        MusicStore store = new MusicStore();
        String simulatedInput = "2\nHello\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Enter song title to search in your library:"), "Should prompt for song title");
        assertTrue(output.contains("No matching songs found in your library."), "Should indicate no song found");
    }
    
    @Test
    public void testAddSongToLibrary() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "3\nHello\nAdele\n25\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Song added to library."), "Should confirm song addition");
        assertFalse(library.searchSongByTitle("Hello").isEmpty(), "Library should contain the song 'Hello'");
    }
    
    @Test
    public void testCreatePlaylist() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "6\nChill Vibes\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Playlist created."), "Should indicate that the playlist was created");
        // Check that the playlist exists in the library.
        assertNotNull(library.getPlaylist("Chill Vibes"), "Playlist 'Chill Vibes' should be created");
    }
    
    @Test
    public void testAddSongToNonexistentPlaylist() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "7\nNonexistentPlaylist\nHello\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Playlist not found."), "Should indicate that the playlist does not exist");
    }
    
    @Test
    public void testMarkSongFavoriteEmptyLibrary() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "8\nHello\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Song not found in your library."), "Should indicate that the song is not found");
    }
    

    @Test
    public void testRateSongEmptyLibrary() {
        LibraryModel library = new LibraryModel();
        MusicStore store = new MusicStore();
        String simulatedInput = "9\nHello\n0\n";
        String output = runViewWithInput(simulatedInput, library, store);
        assertTrue(output.contains("Song not found in your library."), "Should indicate that the song is not found");
    }
}
