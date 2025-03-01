package model;

import java.util.*;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class represents the user's music library.
 * It manages the collection of songs, albums, playlists, and favorites.
 * LibraryModel provides methods to add songs or albums, search for songs,
 * create and retrieve playlists, and mark or rate songs.

 */
public class LibraryModel {
	
		// private fields
		private Set<Song> songs;
		private Set<Album> albums;
		private Map<String, Playlist> playlists;
		private Set<Song> favorites;
		
		/**
	     * Constructs a new, empty LibraryModel.
	     */
		public LibraryModel() {
			songs = new HashSet<>();
			albums = new HashSet<>();
			playlists = new HashMap<>();
			favorites = new HashSet<>();
		}
		
		public boolean addSong(Song song) {
			boolean added = songs.add(song);
			return added;
		}
		
		/**
	     * This class adds a song to the library.
	     * 
	     * @param song - The Song object to add.
	     * @return true if the song was successfully added and false if it was already present.
	     */
		public boolean addAlbum(Album album) {
			boolean addedAlbum = albums.add(album);
			if (addedAlbum) {
				for (Song song : album.getSongs() ) {
					addSong(song);
				}
			}
			return addedAlbum;
		}
		
		/**
	     * This class marks a song as a favorite.
	     * And adds the song to the favorites collection and sets its favorite flag.
	     * 
	     * @param song - The Song object to mark as favorite.
	     */
		public void markFavorite(Song song) {
			favorites.add(song);
			song.setFavorite(true);
		}
		
		/**
	     * This class rates a song. If the rating is 5, the song is automatically marked as favorite.
	     * 
	     * @param song - The Song object to rate.
	     * @param rating - An integer between 1 and 5.
	     */
		public void rateSong(Song song, int rating) {
			song.setRating(rating);
			if (rating == 5) {
				markFavorite(song);
			}
		}
		
		/**
	     * This class searches for songs in the library by title (case-insensitive).
	     * 
	     * @param - title The title of the song to search for.
	     * @return - A list of Song objects that match the given title.
	     */
		public List<Song> searchSongByTitle(String title) {
			List<Song> result = new ArrayList<>();
			for (Song song : songs) {
				if (song.getTitle().equalsIgnoreCase(title)) {
					result.add(song);
				}
			}
			return result;
		}
		
		/**
	     * This class creates a new playlist with the specified name.
	     * 
	     * @param name - The name of the playlist to create.
	     * @return true if the playlist was created successfully and false if a playlist with the same name already exists.
	     */
		public boolean createPlaylist(String name) {
			if (playlists.containsKey(name)) {
				return false;
			}
			playlists.put(name, new Playlist(name));
			return true;
		}
		
		/**
	     * This class retrieves a playlist by its name.
	     * 
	     * @param name - The name of the playlist.
	     * @return Playlist object if found and null otherwise.
	     */
		public Playlist getPlaylist(String name) {
			return playlists.get(name);
		}
		
		public Set<Song> getSongs() {
			return songs;
		}
		
		public Set<Album> getAlbums() {
			return albums;
		}
		
		public Collection<Playlist> getAllPlaylists() {
			return playlists.values();
		}
		
		public Set<Song> getFavorites() {
			return favorites;
		}
}
