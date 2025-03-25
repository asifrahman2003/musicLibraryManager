# musicLibraryManager

## Overview
- **Author**: Asifur Rahman
- **Course**: CSc 335
- **Date**: March 25, 2025
- **Description**: A Java-based console application for managing a personal music library with user authentication, song management, playlists, and persistence via JSON.

## Features
- Secure user login/registration with SHA-256 password hashing.
- Add songs and albums from a store to a personal library.
- Create and manage playlists.
- Mark songs as favorites and rate them.
- Track recent and frequent plays.
- Save library data to `users.json`.

## File Structure
- `app/Main.java`: Application entry point and user management.
- `model/User.java`: User authentication and library persistence.
- `model/LibraryModel.java`: Core library management.
- `model/Song.java`: Song representation.
- `model/Album.java`: Album representation.
- `model/Playlist.java`: Playlist representation.
- `store/MusicStore.java`: Music catalog management.
- `view/MusicLibraryView.java`: Console UI.
- `users.json`: User data storage.
- `albums/albums.txt`: Music store catalog.

## Installation
1. **Requirements**: Java 22+
2. **Compile**: `javac -d bin app/*.java model/*.java store/*.java view/*.java`
3. **Run**: `java -cp bin app.Main`

** Run: ** `java -jar MusicLibrary.jar`

## Usage
1. Launch the app.
2. Log in or register with a username and password.
3. Use the menu to manage your library (options 1-12).
