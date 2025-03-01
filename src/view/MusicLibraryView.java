package view;

import model.Album;
import model.LibraryModel;
import model.Playlist;
import model.Song;
import store.MusicStore;
import java.util.List;
import java.util.Scanner;

/*
 * 
 * 
 * */
public class MusicLibraryView {
	// reference to the user's library (model)
	private LibraryModel library;
	// reference to the music store
	private MusicStore store;
	// scanner for reading user input
	private Scanner scanner;
	
	/*
	 * 
	 */
	public MusicLibraryView(LibraryModel library, MusicStore store) {
		this.library = library;
		this.store = store;
		this.scanner = new Scanner(System.in);
	}
	
	/*
	 * 
	 * 
	 * */
	public void start() {
		boolean exit = false;
        while (!exit) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                searchStore();
            } else if (choice.equals("2")) {
                searchLibrary();
            } else if (choice.equals("3")) {
                addSongToLibrary();
            } else if (choice.equals("4")) {
                addAlbumToLibrary();
            } else if (choice.equals("5")) {
                listLibraryItems();
            } else if (choice.equals("6")) {
                createPlaylist();
            } else if (choice.equals("7")) {
                addSongToPlaylist();
            } else if (choice.equals("8")) {
                markSongFavorite();
            } else if (choice.equals("9")) {
                rateSong();
            } else if (choice.equals("0")) {
                exit = true;
                System.out.println("Exiting. Goodbye!");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
	}
        
        /**
         * Displays the main menu options.
         */
        private void displayMenu() {
            System.out.println("\n--- Music Library Menu ---");
            System.out.println("1. Search Music Store");
            System.out.println("2. Search Library");
            System.out.println("3. Add Song to Library");
            System.out.println("4. Add Album to Library");
            System.out.println("5. List Library Items");
            System.out.println("6. Create Playlist");
            System.out.println("7. Add Song to Playlist");
            System.out.println("8. Mark Song as Favorite");
            System.out.println("9. Rate a Song");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
        }
        
        /**
         * Prompts the user to search the Music Store by album title and displays matching results.
         */
        private void searchStore() {
            System.out.print("Enter album title to search in store: ");
            String title = scanner.nextLine().trim();
            List<Album> results = store.searchAlbumByTitle(title);
            if (results.isEmpty()) {
                System.out.println("No matching albums found in store.");
            } else {
                for (Album album : results) {
                    System.out.println("\nAlbum: " + album.getTitle() + " by " + album.getArtist());
                    System.out.println("Genre: " + album.getGenre() + ", Year: " + album.getYear());
                    System.out.println("Songs:");
                    for (Song s : album.getSongs()) {
                        System.out.println(" - " + s.getTitle());
                    }
                }
            }
        }
        
        /**
         * Prompts the user to search their library by song title and displays matching songs.
         */
        private void searchLibrary() {
            System.out.print("Enter song title to search in your library: ");
            String title = scanner.nextLine().trim();
            List<Song> results = library.searchSongByTitle(title);
            if (results.isEmpty()) {
                System.out.println("No matching songs found in your library.");
            } else {
                for (Song s : results) {
                    System.out.println("Found: " + s.getTitle() + " by " + s.getArtist());
                }
            }
        }
        
        /**
         * Prompts the user to add a new song to their library.
         */
        private void addSongToLibrary() {
            System.out.print("Enter song title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Enter artist name: ");
            String artist = scanner.nextLine().trim();
            System.out.print("Enter album title: ");
            String album = scanner.nextLine().trim();

            Song newSong = new Song(title, artist, album);
            if(library.addSong(newSong)) {
                System.out.println("Song added to library.");
            } else {
                System.out.println("Song already exists in the library.");
            }
        }
        
        /**
         * Prompts the user to add an album from the store to their library.
         */
        private void addAlbumToLibrary() {
            System.out.print("Enter album title to add to your library: ");
            String title = scanner.nextLine().trim();
            List<Album> results = store.searchAlbumByTitle(title);
            if (results.isEmpty()) {
                System.out.println("Album not found in the store.");
            } else {
                // For simplicity, we add the first matching album.
                if(library.addAlbum(results.get(0))) {
                    System.out.println("Album added to library.");
                } else {
                    System.out.println("Album already exists in your library.");
                }
            }
        }
        
        /**
         * Lists all items in the user's library: songs, albums, playlists, and favorites.
         */
        private void listLibraryItems() {
            System.out.println("\n--- Your Library ---");
            System.out.println("Songs:");
            for (Song song : library.getSongs()) {
                System.out.println(" - " + song.getTitle());
            }
            System.out.println("Albums:");
            for (Album album : library.getAlbums()) {
                System.out.println(" - " + album.getTitle());
            }
            System.out.println("Playlists:");
            for (Playlist pl : library.getAllPlaylists()) {
                System.out.println(" - " + pl.getName());
            }
            System.out.println("Favorites:");
            for (Song song : library.getFavorites()) {
                System.out.println(" - " + song.getTitle());
            }
        }
        
        /**
         * Prompts the user to create a new playlist.
         */
        private void createPlaylist() {
            System.out.print("Enter a new playlist name: ");
            String name = scanner.nextLine().trim();
            if(library.createPlaylist(name)) {
                System.out.println("Playlist created.");
            } else {
                System.out.println("A playlist with that name already exists.");
            }
        }
        
        /**
         * Prompts the user to add a song to an existing playlist.
         */
        private void addSongToPlaylist() {
            System.out.print("Enter the name of the playlist: ");
            String playlistName = scanner.nextLine().trim();
            Playlist playlist = library.getPlaylist(playlistName);
            if (playlist == null) {
                System.out.println("Playlist not found.");
                return;
            }
            System.out.print("Enter the song title to add: ");
            String songTitle = scanner.nextLine().trim();
            List<Song> songs = library.searchSongByTitle(songTitle);
            if (songs.isEmpty()) {
                System.out.println("Song not found in your library.");
            } else {
                playlist.addSong(songs.get(0));
                System.out.println("Song added to playlist.");
            }
        }
        
        /**
         * Prompts the user to mark a song as a favorite.
         */
        private void markSongFavorite() {
            System.out.print("Enter song title to mark as favorite: ");
            String songTitle = scanner.nextLine().trim();
            List<Song> songs = library.searchSongByTitle(songTitle);
            if (songs.isEmpty()) {
                System.out.println("Song not found in your library.");
            } else {
                library.markFavorite(songs.get(0));
                System.out.println("Song marked as favorite.");
            }
        }
        
        /**
         * Prompts the user to rate a song.
         */
        private void rateSong() {
            System.out.print("Enter song title to rate: ");
            String songTitle = scanner.nextLine().trim();
            List<Song> songs = library.searchSongByTitle(songTitle);
            if (songs.isEmpty()) {
                System.out.println("Song not found in your library.");
            } else {
                System.out.print("Enter rating (1-5): ");
                try {
                    int rating = Integer.parseInt(scanner.nextLine().trim());
                    library.rateSong(songs.get(0), rating);
                    System.out.println("Song rated.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                }
            }
        }
}
