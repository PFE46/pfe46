package fr.unice.polytech.si5.pfe46.engine.inputtype.objects;

public class LibraryObject extends ConnectedObject {

    private LibraryType libraryType;
    private String id;

    public LibraryType getLibraryType() { return libraryType; }
    public void setLibraryType(LibraryType type) { this.libraryType = type; }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }

    public enum LibraryType {
        MAVEN, JAR, ZIP;
    }

}
