package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	// private fields
	private String name;
	private List<Song> songs;
	
	public Playlist(String name) {
		this.name = name;
		this.songs = new ArrayList<>();
	}
	
	// getter methods
	public String getName() {
		return name;
	}
	
	public List<Song> getSongs() {
		return songs;
	}
	
	public void addSong(Song song) {
   	       songs.add(song);  // Fix to add song not remove it
	}
	
	public void removeSong(Song song) {
		songs.remove(song);
	}
}
