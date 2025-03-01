package app;

import model.LibraryModel;
import store.MusicStore;
import view.MusicLibraryView;

/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class serves as the entry point for the Music Library application.
 * And it initializes the MusicStore and the LibraryModel. It then creates a MusicLibraryView
 * to handle user interactions and starts the application.
 */

public class Main {
	public static void main(String[] args) {
		// create the store and library
		MusicStore store = new MusicStore();
		LibraryModel library = new LibraryModel();
		
		// load store data if needed
		try {
			store.loadAlbums("albums/albums.txt");
		} catch (Exception e) {
			System.out.println("Could not load albums: " + e.getMessage());
		}
		
		// creates the view and start the UI
		MusicLibraryView view = new MusicLibraryView(library, store);
		view.start();
	}
}
