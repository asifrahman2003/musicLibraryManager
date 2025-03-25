package model;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class represents a song in the music library.
 * Here, each Song object contains details such as title, artist, album title, a rating,
 * and a flag indicating whether it is marked as a favorite. rating of 0 indicates that
 * the song has not been rated yet. When the rating is set to 5, the song is automatically
 * marked as a favorite.
 */

public class Song {
	// private fields for encapsulation
	private String title;
	private String artist;
	private String albumTitle;
	private int rating; // 0 means not rated yet
	private boolean favorite;
	
	// new field for play tracking (LA2)
	private int playCount;
	
	/**
     * This constructs a new Song with the specified title, artist, and album title.
     * and initializes the rating to 0 and favorite flag to false.
     *
     * @param title - The title of the song.
     * @param artist - The artist performing the song.
     * @param albumTitle - The album title to which the song belongs.
     */
	public Song(String title, String artist, String albumTitle) {
		this.title = title;
		this.artist = artist;
		this.albumTitle = albumTitle;
		this.rating = 0;
		this.favorite = false;
		this.playCount = 0;		// initialize the new field for playCount
	}
	
	// getter methods
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbumTitle() {
		return albumTitle;
	}
	
	public int getRating() {
		return rating;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	// setter methods
	public void setRating(int rating) {
		if (rating < 1 || rating > 5) {
			return;
		}
	this.rating = rating;
	if (rating == 5) {
		this.favorite = true;
		}
	}
	
	/**
     * Sets the favorite status of the song.
     *
     * @param favorite true if the song is a favorite, false otherwise.
     */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	/**
     * Increments the play count of the song.
     */
	public void incrementPlayCount() {
		playCount++;
	}
	
	/**
     * Gets the play count of the song.
     *
     * @return The play count of the song.
     */
	public int getPlayCount() {
		return playCount;
	}
}
