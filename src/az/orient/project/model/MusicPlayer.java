package az.orient.project.model;
import java.io.*;
import java.util.*;
public class MusicPlayer {
    private static final String FILENAME = "playlist.txt";
    // Static nested class...
    static class SongLibrary {
        private List<String> songs;
        public SongLibrary() {
            songs = new ArrayList<>();
        }
        public void addSong(String song) {
            songs.add(song);
            System.out.println(song + " added to playlist");
        }
        public List<String> getSongs() {
            return songs;
        }
    }
    // Anonymous inner class...
    private SongLibrary playlist = new SongLibrary() {
        @Override
        public void addSong(String song) {
            super.addSong(song);
            System.out.println("Now playing: " + song);
        }
    };

    // Local inner class...
    public void sortPlaylist(String sortType) {
        class SongSorter {
            public List<String> sort(List<String> songs) {
                if (sortType.equals("alphabetical")) { Collections.sort(songs);
                } else if (sortType.equals("reverse-alphabetical")) { Collections.sort(songs, Collections.reverseOrder());
                } else if (sortType.equals("random")) { Collections.shuffle(songs);
                } else { throw new IllegalArgumentException("Sorry, not found!");}
                return songs; }}

        SongSorter songSorter = new SongSorter();
        List<String> sortedSongs = songSorter.sort(playlist.getSongs());

        System.out.println("Playlist sorted by " + sortType);
        System.out.println(sortedSongs.toString());
    }
    // Non-static inner class
        public void displaySongMetadata(String song, String artist, int year, String album) {
            class SongMetadata {
                private String artist;
                private int year;
                private String album;

                public SongMetadata(String artist, int year, String album) {
                    this.artist = artist;
                    this.year = year;
                    this.album = album;
                }
                public String getArtist() {
                    return artist;
                }
                public int getYear() {
                    return year;
                }
                public String getAlbum() {
                    return album;
                }
            }

            SongMetadata metadata = new SongMetadata(artist, year, album);
            System.out.println("Song: " + song);
            System.out.println("Artist: " + metadata.getArtist());
            System.out.println("Year: " + metadata.getYear());
            System.out.println("Album: " + metadata.getAlbum());
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("meta-data.txt", true));
                writer.write("Song: " + song + "\n");
                writer.write("Artist: " + metadata.getArtist() + "\n");
                writer.write("Year: " + metadata.getYear() + "\n");
                writer.write("Album: " + metadata.getAlbum() + "\n\n");
                writer.close();
                System.out.println("Metadata added file: meta-data.txt");
            } catch (IOException e) {
                System.out.println("An error occurred while adding metadata to file!");
            }
    }
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))){
            for (String song : playlist.getSongs()) {
                writer.write(song + "\n");
            }
            writer.close();
            System.out.println("Playlist saved to file: " + FILENAME);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the playlist to file!");
        }
    }
    private void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String song;
            while ((song = reader.readLine()) != null) {
                playlist.addSong(song);
            }
            System.out.println("Playlist loaded from file: " + FILENAME);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the playlist from file!");
        }
    }
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Music Player!");
        player.readFromFile();
        try {
            while (true) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Add song to playlist");
                System.out.println("2. Sort playlist");
                System.out.println("3. Display song metadata");
                System.out.println("4. Save playlist to file");
                System.out.println("5. Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter song name:");
                        String songName = sc.next();
                        player.playlist.addSong(songName);
                        break;
                    case 2:
                        System.out.println("Enter sort type:");
                        System.out.println("Options -> alphabetical\nreverse-alphabetical\nrandom");
                        try { String sortType = sc.next();
                            player.sortPlaylist(sortType);}
                        catch (IllegalArgumentException ex) {System.out.println(ex.getMessage());}
                        break;
                    case 3:
                        System.out.println("Enter song name:");
                        songName = sc.next();
                        System.out.println("Enter song artist:");
                        String songArtistName = sc.next();
                        System.out.println("Enter song year:");
                        int songYear = sc.nextInt();
                        System.out.println("Enter song album:");
                        String songAlbum = sc.next();
                        player.displaySongMetadata(songName, songArtistName, songYear, songAlbum);
                        break;
                    case 4:
                        player.writeToFile();
                        System.out.println("Succesfuly saved...");
                        break;
                    case 5:
                        System.out.println("Exiting..");
                        System.exit(0);
                        break;
                    default: System.out.println("Invalid choice!!!");break;
                }}}catch (Exception ex){System.out.println("An occured unexpected exception:/");}}}