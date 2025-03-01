package model;

public class Song {
	// private fields
	private String title;
	private String artist;
	private String albumTitle;
	private int rating; // 0 means not rated yet
	private boolean favorite;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	public Song(String title, String artist, String albumTitle) {
		this.title = title;
		this.artist = artist;
		this.albumTitle = albumTitle;
		this.rating = 0;
		this.favorite = false;
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
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
