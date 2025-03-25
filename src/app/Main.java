package app;

import model.User;
import model.LibraryModel;
import store.MusicStore;
import view.MusicLibraryView;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;


/**
 * Author: Asifur Rahman
 * Date: February 28, 2025
 * Course: CSc 335
 * 
 * Program Description: This class serves as the entry point for the Music Library application.
 * And it initializes the MusicStore and the LibraryModel. It then creates a MusicLibraryView
 * to handle user interactions and starts the application. 
 * Now it also manages user login and registration, including secure password handling. 
 * Then it initializes the user's music library and starts the text-based user interface. And finally, 
 * saves the user data back to the JSON file when the application exits.
 */

public class Main {
	
	private static Map<String, User> users = new HashMap<>();
	private static Scanner scanner = new Scanner(System.in);
	private static final String USER_FILE = "users.json";
	
	/**
     * Main method that starts the application.
     * 
     * Steps:
     * 	 Load existing users from file.
     *   Initialize the MusicStore (loads album data).
     *   Log in or register a user.
     *   Initialize the user's library and start the UI.
     *   Save users back to the file when exiting.
     */
	public static void main(String[] args) {
		
		// load users from file at startup
		loadUsersFromFile();
		
		// create the store and library
		MusicStore store = new MusicStore();
		
		// load store data if needed
		try {
			store.loadAlbums("albums/albums.txt");
		} catch (Exception e) {
			System.out.println("Could not load albums: " + e.getMessage());
		}
		
		// login or register a new user
		User currentUser = loginOrRegister();
		LibraryModel library = currentUser.getLibrary();
		
		// creates the view and start the UI with the user's library
		MusicLibraryView view = new MusicLibraryView(library, store);
		view.start();
		
		// save users before exiting
		saveUsersToFile();
	}
	
	// method to handle user authentication
	private static User loginOrRegister() {
		System.out.print("Enter username: ");
		String userName = scanner.nextLine().trim();
		
		if (users.containsKey(userName)) {
			// existing user: login
			System.out.print("Enter password: ");
			String password = scanner.nextLine().trim();
			User user = users.get(userName);
			if (user.checkPassword(password)) {
				System.out.println("Login successful!");
				return user;
			} else {
				System.out.println("Incorrect password. Existing.");
				System.exit(1);
			}
		}
		
		// new user: register
		System.out.print("New user. Enter password: ");
		String password = scanner.nextLine().trim();
		User user = new User(userName, password);
		users.put(userName, user);
		saveUsersToFile();
		System.out.println("User registered!");
		return user;
	}
	
	/**
     * Loads users from the JSON file into the users map.
     */
	private static void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.out.println("No user data found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }

            String json = jsonContent.toString().trim();
            if (json.isEmpty() || !json.startsWith("{")) return;

            // Extract the "users" array content
            int start = json.indexOf("[") + 1;
            int end = json.lastIndexOf("]");
            if (start >= end) return;
            String userList = json.substring(start, end);

            // Manually parse user entries by tracking braces
            int braceCount = 0;
            int entryStart = 0;
            for (int i = 0; i < userList.length(); i++) {
                char c = userList.charAt(i);
                if (c == '{') braceCount++;
                else if (c == '}') braceCount--;
                if (braceCount == 0 && (i == userList.length() - 1 || userList.charAt(i + 1) == ',')) {
                    String entry = userList.substring(entryStart, i + 1).trim();
                    String userName = extractField(entry, "username");
                    String salt = extractField(entry, "salt");
                    String hashedPassword = extractField(entry, "hashedPassword");
                    String libraryData = extractLibraryField(entry);

                    if (userName != null && salt != null && hashedPassword != null) {
                        User user = new User(userName, salt, hashedPassword, libraryData);
                        users.put(userName, user);
                    }
                    entryStart = i + 2; // Skip comma and space
                }
            }
            System.out.println("Loaded " + users.size() + " users from file.");
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

	/**
     * Extracts a field value from a JSON entry.
     * 
     * @param entry The JSON string entry.
     * @param fieldName The name of the field to extract.
     * @return The field value, or null if not found.
     */
    private static String extractField(String entry, String fieldName) {
        String pattern = "\"" + fieldName + "\":\"([^\"]+)\"";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(entry);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Extracts the library field from a JSON entry.
     * 
     * @param entry The JSON string entry.
     * @return The library JSON string, or "{}" if not found or malformed.
     */
    private static String extractLibraryField(String entry) {
        int start = entry.indexOf("\"library\":");
        if (start == -1) return "{}";
        start += 9; // Move to colon after "\"library\":"
        while (start < entry.length() && entry.charAt(start) != '{') start++; // Find opening brace
        if (start >= entry.length()) return "{}";

        int braceCount = 1;
        int end = start + 1;
        while (end < entry.length() && braceCount > 0) {
            char c = entry.charAt(end);
            if (c == '{') braceCount++;
            else if (c == '}') braceCount--;
            end++;
        }
        if (braceCount != 0) return "{}"; // Malformed
        return entry.substring(start, end);
    }

    /**
     * Saves all users to the JSON file.
     */
    private static void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            bw.write("{\"users\":[");
            boolean first = true;
            for (User user : users.values()) {
                if (!first) bw.write(",");
                bw.write(String.format(
                    "{\"username\":\"%s\",\"salt\":\"%s\",\"hashedPassword\":\"%s\",\"library\":%s}",
                    user.getUserName(), user.getSalt(), user.getHashedPassword(), user.getLibraryData()
                ));
                first = false;
            }
            bw.write("]}");
            System.out.println("Saved " + users.size() + " users to file at: " + new File(USER_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}
