package app;

import model.LibraryModel;
import store.MusicStore;
import view.MusicLibraryView;

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
		
		// create the view and start the UI
		MusicLibraryView view = new MusicLibraryView(library, store);
		view.start();
	}
}
