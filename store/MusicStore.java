package store;

import java.io.*;
import java.util.*;
import model.Album;
import model.Song;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class MusicStore loads album data from text files and provides
 * search functionality. The master file "albums/albums.txt" contains lines in the format of
 * AlbumTitle,Artist. For each line, the corresponding album file is expected to be in the
 * "albums" folder, with a name formatted as: AlbumTitle_Artist.txt
 * The album file's first line should be: Album Title,Artist,Genre,Year
 */
public class MusicStore {
    // private field
    private Map<String, Album> albums;

    /**
     * This constructs a new MusicStore with an empty album collection.
     */
    public MusicStore() {
        albums = new HashMap<>();
    }

    /**
     * This class loads albums from the master albums file. Each line in the master file should
     * be in the format of AlbumTitle,Artist and for each album, the corresponding file is read
     * to load detailed album data.
     *
     * @param albumsListFile The relative path to the master albums file
     * @throws IOException If an I/O error occurs while reading the file
     */
    public void loadAlbums(String albumsListFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(albumsListFile));
        String line;
        while ((line = br.readLine()) != null) {
            // Split the line into album title and artist.
            String[] parts = line.split(",");
            if (parts.length != 2) {
                continue; // Skip malformed lines.
            }
            String albumTitle = parts[0].trim();
            String artist = parts[1].trim();
            // Construct the filename: for example, "albums/19_Adele.txt"
            String fileName = "albums/" + albumTitle + "_" + artist + ".txt";
            loadAlbum(fileName);
        }
        br.close();
    }

    /**
     * Loads a single album from its file. The album file should have:
     * The first line, header in the format of Album Title,Artist,Genre,Year.
     * Rest of the lines containing the song titles, in the order they appear on the album
     *
     * @param fileName The relative file path to the album file
     * @throws IOException If an I/O error occurs while reading the file
     */
    private void loadAlbum(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Could not load albums: " + fileName + " (No such file or directory)");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String header = br.readLine();
        if (header == null) {
            br.close();
            return;
        }
        // parse the header Album Title,Artist,Genre,Year
        String[] headerParts = header.split(",");
        if (headerParts.length != 4) {
            br.close();
            return;
        }
        String albumTitle = headerParts[0].trim();
        String artist = headerParts[1].trim();
        String genre = headerParts[2].trim();
        int year = Integer.parseInt(headerParts[3].trim());
        // creates a new Album object
        Album album = new Album(albumTitle, artist, genre, year);
        // read and add songs to the album
        String songTitle;
        while ((songTitle = br.readLine()) != null) {
            songTitle = songTitle.trim();
            if (songTitle.isEmpty()) {
                continue;
            }
            Song song = new Song(songTitle, artist, albumTitle);
            album.addSong(song);
        }
        br.close();
        // store the album in the map
        String key = albumTitle + "_" + artist;
        albums.put(key, album);
    }

    /**
     * This class searches for albums by title (case-insensitive)
     *
     * @param title - The album title to search for
     * @return list of Album objects matching the title
     */
    public List<Album> searchAlbumByTitle(String title) {
        List<Album> result = new ArrayList<>();
        for (Album album : albums.values()) {
            if (album.getTitle().equalsIgnoreCase(title)) {
                result.add(album);
            }
        }
        return result;
    }

    /**
     * This class searches for albums by artist (case-insensitive)
     *
     * @param artist - The artist name to search for
     * @return list of Album objects matching the artist
     */
    public List<Album> searchAlbumByArtist(String artist) {
        List<Album> result = new ArrayList<>();
        for (Album album : albums.values()) {
            if (album.getArtist().equalsIgnoreCase(artist)) {
                result.add(album);
            }
        }
        return result;
    }

    /**
     * This class searches for albums by genre (case-insensitive)
     *
     * @param genre - The genre to search for
     * @return list of Album objects matching the genre
     */
    public List<Album> searchAlbumByGenre(String genre) {
        List<Album> result = new ArrayList<>();
        for (Album album : albums.values()) {
            if (album.getGenre().equalsIgnoreCase(genre)) {
                result.add(album);
            }
        }
        return result;
    }

    /**
     * This class searches for albums by release year
     *
     * @param year - The release year to search for
     * @return list of Album objects released in that year
     */
    public List<Album> searchAlbumByYear(int year) {
        List<Album> result = new ArrayList<>();
        for (Album album : albums.values()) {
            if (album.getYear() == year) {
                result.add(album);
            }
        }
        return result;
    }
    
    /**
     * This class searches for songs by artist across all albums
     *
     * @param artist - The artist name to search for
     * @return list of Song objects by the specified artist
     */
    public List<Song> searchSongsByArtist(String artist) {
        List<Song> result = new ArrayList<>();
        for (Album album : albums.values()) {
            for (Song song : album.getSongs()) {
                if (song.getArtist().equalsIgnoreCase(artist)) {
                    result.add(song);
                }
            }
        }
        return result;
    }
    
    /**
     * This class returns a list of all albums loaded in the MusicStore.
     *
     * @return List of all Album objects.
     */
    public List<Album> getAllAlbums() {
        return new ArrayList<>(albums.values());
    }
}

