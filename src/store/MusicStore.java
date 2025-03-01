package store;

import java.io.*;
import java.util.*;
import model.Album;
import model.Song;

/*
 * 
 * 
 * */
public class MusicStore {
	// map to store albums using a composite key "AlbumTitle_Artist"
	private Map<String, Album> albums;
	
	/*
	 * 
	 * */
	public MusicStore() {
		albums = new HashMap<>();
	}
	
	/*
	 * 
	 * */
	public void loadAlbums(String albumsListFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(albumsListFile));
		String line;
		while ((line = br.readLine()) != null) {
			// split the line into album title and artist
			String[] parts = line.split(",");
			if (parts.length != 2) {
				continue; // skip malformed lines
			}
			String albumTitle = parts[0].trim();
			String artist = parts[1].trim();
			
			// construct the filename: for example, "Old Ideas_Leonard Cohen.txt"
			String fileName = albumTitle + "_" + artist + ".txt";
			loadAlbum(fileName);
		}
		br.close();
	}
	
	/*
	 * 
	 * 
	 * */
	private void loadAlbum(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String header = br.readLine();
		if (header == null) {
			br.close();
			return;
		}
		
		// parse the header: album title, artist, genre, year
		String[] headerParts = header.split(",");
		if (headerParts.length != 4) {
			br.close();
			return;
		}
	
	String albumTitle = headerParts[0].trim();
	String artist = headerParts[1].trim();
	String genre = headerParts[2].trim();
	int year = Integer.parseInt(headerParts[3].trim());
	
	// create a new Album object
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
	
	// store the album in the map using a composite key
	String key = albumTitle + "_" + artist;
	albums.put(key, album);
	}
	
	/*
	 * 
	 * 
	 * */
	public List<Album> searchAlbumByTitle(String title) {
		List<Album> result = new ArrayList<>();
		for (Album album : albums.values()) {
			if (album.getTitle().equalsIgnoreCase(title)) {
				result.add(album);
			}
		}
		return result;
	}
	
	/*
	 * 
	 * 
	 * */
	public List<Album> searchAlbumByArtist(String artist) {
		List<Album> result = new ArrayList<>();
		for (Album album : albums.values()) {
			if (album.getArtist().equalsIgnoreCase(artist)) {
				result.add(album);
			}
		}
		return result;
	}
	
	/*
	 * 
	 * 
	 * */
	public List<Album> searchAlbumByGenre(String genre) {
		List<Album> result = new ArrayList<>();
		for (Album album : albums.values()) {
			if (album.getGenre().equalsIgnoreCase(genre)) {
				result.add(album);
			}
		}
		return result;
	}
	
	/*
	 * 
	 * 
	 * */
	public List<Album> searchAlbumByYear(int year) {
		List<Album> result = new ArrayList<>();
		for (Album album : albums.values()) {
			if (album.getYear() == year) {
				result.add(album);
			}
		}
		return result;
	}
}
