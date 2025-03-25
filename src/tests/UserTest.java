/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.User;
import model.Song;

class UserTest {

    private User user;
    private final String testUsername = "testUser";
    private final String testPassword = "testPassword";

    @BeforeEach
    void setUp() {
        user = new User(testUsername, testPassword);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(testUsername, user.getUserName());
        assertNotNull(user.getSalt());
        assertNotNull(user.getHashedPassword());
        assertNotNull(user.getLibrary());
    }

    @Test
    void testCheckPasswordSuccess() {
        assertTrue(user.checkPassword(testPassword));
    }

    @Test
    void testCheckPasswordFailure() {
        assertFalse(user.checkPassword("wrongPassword"));
    }

    @Test
    void testGenerateSalt() {
        User anotherUser = new User("anotherUser", "anotherPass");
        assertNotEquals(user.getSalt(), anotherUser.getSalt());
    }

    @Test
    void testGetLibraryDataAndLoadLibraryData() {
        Song song1 = new Song("Song 1", "Artist 1", "Album 1");
        Song song2 = new Song("Song 2", "Artist 2", "Album 2");

        user.getLibrary().addSong(song1);
        user.getLibrary().addSong(song2);
        user.getLibrary().playSong(song1);
        user.getLibrary().playSong(song2);

        String libraryData = user.getLibraryData();
        User loadedUser = new User(testUsername, user.getSalt(), user.getHashedPassword(), libraryData);

        assertEquals(2, loadedUser.getLibrary().getSongs().size());
    }

    @Test
    void testLoadLibraryDataEmpty() {
        User loadedUser = new User(testUsername, user.getSalt(), user.getHashedPassword(), "");
        assertNotNull(loadedUser.getLibrary());
        assertEquals(0, loadedUser.getLibrary().getSongs().size());
    }

    @Test
    void testLoadLibraryDataNull() {
        User loadedUser = new User(testUsername, user.getSalt(), user.getHashedPassword(), null);
        assertNotNull(loadedUser.getLibrary());
        assertEquals(0, loadedUser.getLibrary().getSongs().size());
    }

    @Test
    void testGetLibraryDataEmptyLibrary() {
        String libraryData = user.getLibraryData();
        assertTrue(libraryData.contains("\"songs\":[]"));
        assertTrue(libraryData.contains("\"recentPlays\":[]"));
    }
}
