package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Song;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Test Description: This test class verifies the functionality of the Song class.
 * It tests the constructor, getters, and setters including the logic for
 * setting ratings and marking a song as a favorite.
 * 
 * Framework: JUnit 5
 */
public class SongTest {

    @Test
    public void testConstructorAndGetters() {
        // creates a new Song
        Song s = new Song("Hello", "Adele", "25");
        
        // verify constructor parameters are set
        assertEquals("Hello", s.getTitle(), "Title should match constructor argument");
        assertEquals("Adele", s.getArtist(), "Artist should match constructor argument");
        assertEquals("25", s.getAlbumTitle(), "Album title should match constructor argument");
        
        // by default, rating should be 0, and favorite should be false
        assertEquals(0, s.getRating(), "Default rating should be 0");
        assertFalse(s.isFavorite(), "Default favorite should be false");
    }
    
    @Test
    public void testSetRatingValid() {
        Song s = new Song("Rolling in the Deep", "Adele", "21");
        
        s.setRating(4);
        assertEquals(4, s.getRating(), "Rating should be updated to 4");
        assertFalse(s.isFavorite(), "Rating of 4 should not mark the song as favorite");
        
        s.setRating(5);
        assertEquals(5, s.getRating(), "Rating should be updated to 5");
        assertTrue(s.isFavorite(), "Rating of 5 should mark the song as favorite");
    }
    
    @Test
    public void testSetRatingInvalid() {
        Song s = new Song("Skyfall", "Adele", "Skyfall OST");
        
        s.setRating(-1);
        assertEquals(0, s.getRating(), "Rating should remain 0 if invalid rating is provided");
        assertFalse(s.isFavorite(), "Favorite should remain false");
        
        s.setRating(6);
        assertEquals(0, s.getRating(), "Rating should remain 0 if invalid rating is provided");
    }
    
    @Test
    public void testSetFavorite() {
        Song s = new Song("Someone Like You", "Adele", "21");
        assertFalse(s.isFavorite(), "Should not be favorite by default");
        
        s.setFavorite(true);
        assertTrue(s.isFavorite(), "Should be favorite after setting it to true");
        
        s.setFavorite(false);
        assertFalse(s.isFavorite(), "Should no longer be favorite after setting it to false");
    }
}
