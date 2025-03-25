package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Album;
import model.Song;
import java.util.List;

public class AlbumTest {

    @Test
    public void testAlbumProperties() {
        Album album = new Album("Tapestry", "Carole King", "Rock", 1971);
        
        assertEquals("Tapestry", album.getTitle(), "Album title should match constructor input");
        assertEquals("Carole King", album.getArtist(), "Album artist should match constructor input");
        assertEquals("Rock", album.getGenre(), "Album genre should match constructor input");
        assertEquals(1971, album.getYear(), "Album year should match constructor input");
        
        assertNotNull(album.getSongs(), "Songs list should be initialized");
        assertTrue(album.getSongs().isEmpty(), "Songs list should be empty initially");
    }
    
    @Test
    public void testAddSong() {
        Album album = new Album("Tapestry", "Carole King", "Rock", 1971);
        Song s1 = new Song("I Feel The Earth Move", "Carole King", "Tapestry");
        
        album.addSong(s1);
        List<Song> songs = album.getSongs();
        
        assertEquals(1, songs.size(), "Album should have 1 song after adding one");
        assertEquals(s1, songs.get(0), "The added song should be the first element in the list");
    }
    
    @Test
    public void testAddMultipleSongs() {
        Album album = new Album("Tapestry", "Carole King", "Rock", 1971);
        Song s1 = new Song("I Feel The Earth Move", "Carole King", "Tapestry");
        Song s2 = new Song("So Far Away", "Carole King", "Tapestry");
        
        album.addSong(s1);
        album.addSong(s2);
        List<Song> songs = album.getSongs();
        
        assertEquals(2, songs.size(), "Album should have 2 songs after adding two");
        assertEquals(s1, songs.get(0), "First added song should be at index 0");
        assertEquals(s2, songs.get(1), "Second added song should be at index 1");
    }
    
    @Test
    public void testAddSameSongMultipleTimes() {
        Album album = new Album("Tapestry", "Carole King", "Rock", 1971);
        Song s1 = new Song("I Feel The Earth Move", "Carole King", "Tapestry");
        
        album.addSong(s1);
        album.addSong(s1);
        
        List<Song> songs = album.getSongs();
        assertEquals(2, songs.size(), "If duplicates are allowed, the album should contain the song twice");
    }
}
