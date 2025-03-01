package model;

import java.util.*;

public class LibraryModel {
		private Set<Song> songs;
		private Set<Album> albums;
		private Map<String, Playlist> playlists;
		private Set<Song> favorites;
		
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
		 * 
		 * 
		 * */
		public boolean addAlbum(Album album) {
			boolean addedAlbum = albums.add(album);
			if (addedAlbum) {
				for (Song song : album.getSongs() ) {
					addSong(song);
				}
			}
			return addedAlbum;
		}
		
		/*
		 * 
		 * */
		public void markFavorite(Song song) {
			favorites.add(song);
			song.setFavorite(true);
		}
		
		/*
		 * 
		 * */
		public void rateSong(Song song, int rating) {
    			if (songs.contains(song)) {
        		    song.setRating(rating);
        		    if (rating == 5) {
            			markFavorite(song);
        		    }
    			}
		}

		
		/*
		 * 
		 * */
		public List<Song> searchSongByTitle(String title) {
			List<Song> result = new ArrayList<>();
			for (Song song : songs) {
				if (song.getTitle().equalsIgnoreCase(title)) {
					result.add(song);
				}
			}
			return result;
		}
		
		/*
		 * 
		 * */
		public boolean createPlaylist(String name) {
			if (playlists.containsKey(name)) {
				return false;
			}
			playlists.put(name, new Playlist(name));
			return true;
		}
		
		/*
		 * 
		 * */
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
