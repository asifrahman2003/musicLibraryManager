package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class represents an album in the music library.
 * An Album has a title, an artist, a genre, a release year, and a list of songs.
 * Songs can be added to the album in the order they appear.
 */

public class Album {
	
	// Private fields for encapsulation.
	private String title;
	private String artist;
	private String genre;
	private int year;
	private List<Song> songs;
	
	/**
     * This class constructs a new Album with the specified details.
     * And initializes the songs list as an empty ArrayList.
     *
     * @param title - The title of the album.
     * @param artist - The artist or band of the album.
     * @param genre - The genre of the album.
     * @param year - The release year of the album.
     */
	public Album(String title, String artist, String genre, int year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.songs = new ArrayList<>();
	}
	
	// getter methods
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getYear() {
		return year;
	}
	
	public List<Song> getSongs() {
		return songs;
	}
	
	/**
     * This adds a song to the album.
     * The song is appended to the end of the songs list, preserving the track order.
     *
     * @param song The Song object to add to the album.
     */
	public void addSong(Song song) {
		songs.add(song);
	}
}
