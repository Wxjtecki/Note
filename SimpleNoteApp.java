//Autor Kamil Pajączkowski
import java.io.*;
import java.util.*;

class Note {
    private String title;
    private String content;
    private Date dateCreated;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.dateCreated = new Date();  // Data utworzenia
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nContent: " + content + "\nDate Created: " + dateCreated;
    }
}

class NoteManager {
    private List<Note> notes;

    public NoteManager() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String title, String content) {
        notes.add(new Note(title, content));
    }

    public void deleteNoteByTitle(String title) {
        notes.removeIf(note -> note.getTitle().equalsIgnoreCase(title));
    }

    public void editNoteContent(String title, String newContent) {
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                note.setContent(newContent);
                break;
            }
        }
    }

    public List<Note> getNotesSortedByDate() {
        notes.sort(Comparator.comparing(Note::getDateCreated));
        return notes;
    }

    public List<Note> getNotesSortedByTitle() {
        notes.sort(Comparator.comparing(Note::getTitle));
        return notes;
    }

    public void saveNotesToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(notes);
        }
    }

    public void loadNotesFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            notes = (List<Note>) in.readObject();
        }
    }

    public void displayNotes() {
        for (Note note : notes) {
            System.out.println(note);
            System.out.println("------------------");
        }
    }
}

public class SimpleNoteApp {
    public static void main(String[] args) {
        NoteManager manager = new NoteManager();
        
        // Dodawanie przykładowych notatek
        manager.addNote("Zakupy", "Kupić mleko, chleb, ser");
        manager.addNote("Spotkanie", "Spotkanie z Tomkiem w środę");

        // Wyświetlanie notatek posortowanych według daty
        System.out.println("Notatki posortowane według daty:");
        manager.getNotesSortedByDate().forEach(System.out::println);

        // Edycja notatki
        manager.editNoteContent("Zakupy", "Kupić mleko, chleb, ser, masło");

        // Zapis i odczyt z pliku
        try {
            manager.saveNotesToFile("notes.dat");
            manager.loadNotesFromFile("notes.dat");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Wyświetlanie wszystkich notatek
        System.out.println("\nWszystkie notatki:");
        manager.displayNotes();
    }
}
