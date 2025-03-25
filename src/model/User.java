package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * User.java
 * Authors: Asifur Rahman
 * Date: March 24, 2025
 * Description: Represents a user with a userName, password, and music library.
 * Handles user authentication and library data persistence. 
 */
public class User {

	private String userName;	// the user's name
	private String hashedPassword;	// the secret code version of their password, password + salt
	private LibraryModel library;	// their personal music library
	private String salt;	// stores the salt separately
	
	// constructor: runs while we make a new user (registration)
	public User(String userName, String password) {
		this.userName = userName;
		this.salt = generateSalt();		// generate salt
		this.hashedPassword = hashPassword(password, salt);	// hash the password, password + salt
		this.library = new LibraryModel();		// give users a new library
	}
	
	// constructor for loading users from file
	public User(String userName, String salt, String hashedPassword, String libraryData) {
		this.userName = userName;
		this.salt = salt;
		this.hashedPassword = hashedPassword;
		this.library = new LibraryModel();
		if (libraryData != null && !libraryData.isEmpty()) {
			loadLibraryData(libraryData);
		}
	}
	
	/**
     * Generates a random salt for password hashing.
     *
     * @return A Base64 encoded string representing the salt.
     */
	public String generateSalt() {
		SecureRandom randomSalt = new SecureRandom();	// construction
		byte[] saltBytes = new byte[16];	// 16 bytes = 128 bits
		randomSalt.nextBytes(saltBytes);	// Fill with random bytes
		return Base64.getEncoder().encodeToString(saltBytes);	// converts to readable string
	}
	
	
	/**
     * Hashes the user's password with the given salt using SHA-256.
     *
     * @param password The user's password.
     * @param salt The salt to use for hashing.
     * @return The hashed password as a hexadecimal string, or the plain password if an error occurs.
     */
	private String hashPassword(String password, String salt) {
		try {
			MessageDigest mD = MessageDigest.getInstance("SHA-256");
			// combine password and salt (password + salt)
			String saltedPassword = password + salt;
			
			byte[] hashedBytes = mD.digest(saltedPassword.getBytes());
			StringBuilder strBuilder =  new StringBuilder();
			for (byte b : hashedBytes) {
				strBuilder.append(String.format("%02x", b));
			}
			return strBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error hashing password. Using plain text (not secured)!");
			return password;
		}
	}
	
	/**
     * Loads the user's library data from a JSON string.
     *
     * @param libraryData The JSON string representing the library data.
     */
	private void loadLibraryData(String libraryData) {
	    try {
	        String data = libraryData.trim();
	        if (data.startsWith("{") && data.endsWith("}")) {
	            data = data.substring(1, data.length() - 1);
	        }

	        // Load songs
	        int songsStart = data.indexOf("\"songs\":");
	        if (songsStart != -1) {
	            songsStart += 8;
	            int songsEnd = data.indexOf("],", songsStart) + 1;
	            if (songsEnd == 0) {
	                songsEnd = data.indexOf("]", songsStart) + 1;
	            }
	            String songsContent = data.substring(songsStart, songsEnd);
	            String[] songEntries = songsContent.substring(1, songsContent.length() - 1).split("\\},\\s*\\{");
	            for (int i = 0; i < songEntries.length; i++) {
	                String entry = songEntries[i].replace("{", "").replace("}", "").trim();
	                if (i == 0 && entry.startsWith("[")) {
	                    entry = entry.substring(1);
	                }
	                if (i == songEntries.length - 1 && entry.endsWith("]")) {
	                    entry = entry.substring(0, entry.length() - 1);
	                }

	                String[] fields = entry.split(",\\s*");
	                String title = "", artist = "", album = "";
	                int playCount = 0, rating = 0;
	                boolean isFavorite = false;
	                for (String field : fields) {
	                    String[] keyValue = field.split(":\\s*", 2);
	                    if (keyValue.length < 2) continue;
	                    String key = keyValue[0].replace("\"", "").trim();
	                    String value = keyValue[1].replace("\"", "").trim();
	                    if (key.equals("title")) {
	                        title = value;
	                    } else if (key.equals("artist")) {
	                        artist = value;
	                    } else if (key.equals("album")) {
	                        album = value;
	                    } else if (key.equals("playCount")) {
	                        playCount = Integer.parseInt(value);
	                    } else if (key.equals("rating")) {
	                        rating = Integer.parseInt(value);
	                    } else if (key.equals("isFavorite")) {
	                        isFavorite = Boolean.parseBoolean(value);
	                    }
	                }

	                if (!title.isEmpty() && !artist.isEmpty() && !album.isEmpty()) {
	                    Song song = new Song(title, artist, album);
	                    for (int j = 0; j < playCount; j++) {
	                        song.incrementPlayCount();
	                    }
	                    song.setRating(rating);
	                    if (isFavorite) {
	                        library.markFavorite(song);
	                    }
	                    library.addSong(song);
	                }
	            }
	        }

	        // Load albums
	        int albumsStart = data.indexOf("\"albums\":");
	        if (albumsStart != -1) {
	            albumsStart += 9;
	            int albumsEnd = data.indexOf("],", albumsStart) + 1;
	            if (albumsEnd == 0) {
	                albumsEnd = data.indexOf("]", albumsStart) + 1;
	            }
	            String albumsContent = data.substring(albumsStart, albumsEnd);
	            if (!albumsContent.equals("[]")) {
	                String[] albumEntries = albumsContent.substring(1, albumsContent.length() - 1).split("\\},\\s*\\{");
	                for (int i = 0; i < albumEntries.length; i++) {
	                    String entry = albumEntries[i].replace("{", "").replace("}", "").trim();
	                    if (i == 0 && entry.startsWith("[")) {
	                        entry = entry.substring(1);
	                    }
	                    if (i == albumEntries.length - 1 && entry.endsWith("]")) {
	                        entry = entry.substring(0, entry.length() - 1);
	                    }

	                    String[] fields = entry.split(",\\s*");
	                    String title = "", artist = "", genre = "";
	                    int year = 0;
	                    List<Song> albumSongs = new ArrayList<>();
	                    for (String field : fields) {
	                        String[] keyValue = field.split(":\\s*", 2);
	                        if (keyValue.length < 2) continue;
	                        String key = keyValue[0].replace("\"", "").trim();
	                        String value = keyValue[1].replace("\"", "").trim();
	                        if (key.equals("title")) {
	                            title = value;
	                        } else if (key.equals("artist")) {
	                            artist = value;
	                        } else if (key.equals("genre")) {
	                            genre = value;
	                        } else if (key.equals("year")) {
	                            year = Integer.parseInt(value);
	                        } else if (key.equals("songs")) {
	                            String songsList = field.substring(field.indexOf("[") + 1, field.lastIndexOf("]"));
	                            String[] songTitles = songsList.split(",\\s*");
	                            for (String songTitle : songTitles) {
	                                songTitle = songTitle.replace("\"", "").trim();
	                                for (Song s : library.getSongs()) {
	                                    if (s.getTitle().equals(songTitle)) {
	                                        albumSongs.add(s);
	                                        break;
	                                    }
	                                }
	                            }
	                        }
	                    }
	                    if (!title.isEmpty() && !artist.isEmpty()) {
	                        Album album = new Album(title, artist, genre, year, albumSongs);
	                        library.addAlbum(album);
	                    }
	                }
	            }
	        }

	        // Load playlists
	        int playlistsStart = data.indexOf("\"playlists\":");
	        if (playlistsStart != -1) {
	            playlistsStart += 11;
	            int playlistsEnd = data.indexOf("},", playlistsStart) + 1;
	            if (playlistsEnd == 0) {
	                playlistsEnd = data.indexOf("}", playlistsStart) + 1;
	            }
	            String playlistsContent = data.substring(playlistsStart, playlistsEnd);
	            if (playlistsContent.length() > 2) {
	                String innerContent = playlistsContent.substring(1, playlistsContent.length() - 1);
	                String[] playlistEntries = innerContent.split(",\\s*(?=\"\\w+\":\\{)");
	                for (String entry : playlistEntries) {
	                    String[] keyValue = entry.split(":\\s*", 2);
	                    if (keyValue.length < 2) continue;
	                    String playlistData = keyValue[1].trim();
	                    if (playlistData.startsWith("{") && playlistData.endsWith("}")) {
	                        playlistData = playlistData.substring(1, playlistData.length() - 1);
	                        String[] fields = playlistData.split(",\\s*");
	                        String name = "";
	                        List<Song> playlistSongs = new ArrayList<>();
	                        for (String field : fields) {
	                            String[] subKeyValue = field.split(":\\s*", 2);
	                            if (subKeyValue.length < 2) continue;
	                            String subKey = subKeyValue[0].replace("\"", "").trim();
	                            String subValue = subKeyValue[1].replace("\"", "").trim();
	                            if (subKey.equals("name")) {
	                                name = subValue;
	                            } else if (subKey.equals("songs")) {
	                                String songsList = field.substring(field.indexOf("[") + 1, field.lastIndexOf("]"));
	                                String[] songTitles = songsList.split(",\\s*");
	                                for (String songTitle : songTitles) {
	                                    songTitle = songTitle.replace("\"", "").trim();
	                                    for (Song s : library.getSongs()) {
	                                        if (s.getTitle().equals(songTitle)) {
	                                            playlistSongs.add(s);
	                                            break;
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                        if (!name.isEmpty()) {
	                            library.createPlaylist(name);
	                            Playlist playlist = library.getPlaylist(name);
	                            for (Song song : playlistSongs) {
	                                playlist.addSong(song);
	                            }
	                        }
	                    }
	                }
	            }
	        }

	        // Load recentPlays
	        int recentStart = data.indexOf("\"recentPlays\":");
	        if (recentStart != -1) {
	            recentStart += 14;
	            int recentEnd = data.indexOf("]", recentStart) + 1;
	            String recentContent = data.substring(recentStart, recentEnd);
	            String innerContent = recentContent.substring(1, recentContent.length() - 1);
	            String[] recentEntries = innerContent.split(",\\s*");
	            List<Song> recentSongs = new ArrayList<>();
	            for (String title : recentEntries) {
	                title = title.replace("\"", "").trim();
	                for (Song song : library.getSongs()) {
	                    if (song.getTitle().equals(title)) {
	                        recentSongs.add(song);
	                        break;
	                    }
	                }
	            }
	            library.setRecentPlays(recentSongs);
	        }
	    } catch (Exception e) {
	        System.out.println("Error loading library data: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	/**
    * Serializes the user's library to a JSON string.
    * 
    * @return A JSON string representing the library (songs, albums, playlists, recent plays).
    */
    public String getLibraryData() {
        StringBuilder sb = new StringBuilder("{");
        // Songs
        sb.append("\"songs\":[");
        Set<Song> songs = library.getSongs();
        boolean first = true;
        for (Song song : songs) {
            if (!first) sb.append(",");
            sb.append(String.format(
                "{\"title\":\"%s\",\"artist\":\"%s\",\"album\":\"%s\",\"playCount\":%d,\"rating\":%d,\"isFavorite\":%b}",
                song.getTitle(), song.getArtist(), song.getAlbumTitle(), song.getPlayCount(), song.getRating(), song.isFavorite()
            ));
            first = false;
        }
        sb.append("],");

        // Albums
        sb.append("\"albums\":[");
        Set<Album> albums = library.getAlbums();
        first = true;
        for (Album album : albums) {
            if (!first) sb.append(",");
            sb.append(String.format(
                "{\"title\":\"%s\",\"artist\":\"%s\",\"genre\":\"%s\",\"year\":%d,\"songs\":[",
                album.getTitle(), album.getArtist(), album.getGenre(), album.getYear()
            ));
            boolean firstSong = true;
            for (Song song : album.getSongs()) {
                if (!firstSong) sb.append(",");
                sb.append(String.format("\"%s\"", song.getTitle()));
                firstSong = false;
            }
            sb.append("]}");
            first = false;
        }
        sb.append("],");

        // Playlists
        sb.append("\"playlists\":{");
        Collection<Playlist> playlists = library.getAllPlaylists();
        first = true;
        for (Playlist playlist : playlists) {
            if (!first) sb.append(",");
            sb.append(String.format(
                "\"%s\":{\"name\":\"%s\",\"songs\":[",
                playlist.getName(), playlist.getName()
            ));
            boolean firstSong = true;
            for (Song song : playlist.getSongs()) {
                if (!firstSong) sb.append(",");
                sb.append(String.format("\"%s\"", song.getTitle()));
                firstSong = false;
            }
            sb.append("]}");
            first = false;
        }
        sb.append("},");

        // Recent Plays
        sb.append("\"recentPlays\":[");
        List<Song> recent = library.getRecentPlays();
        first = true;
        for (Song song : recent) {
            if (!first) sb.append(",");
            sb.append(String.format("\"%s\"", song.getTitle()));
            first = false;
        }
        sb.append("]");

        sb.append("}");
        return sb.toString();
    }
	
	// getter method for getting the userName
	public String getUserName() {
		return userName;
	}
	
	// getter method for getting salt
	public String getSalt() {
		return salt;
	}
	
	// check if a password matches the stored hash
	public boolean checkPassword(String password) {
		String hashedInput = hashPassword(password, this.salt);		// salt last
		return hashedPassword.equals(hashedInput);
	}
	
	// getter method for user's library
	public LibraryModel getLibrary() {
		return library;
	}
	
	// for debugging: to see the hashedPassword
	public String getHashedPassword() {
		return hashedPassword;
	}
}
