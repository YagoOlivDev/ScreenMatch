package ProjectSpring.Screenmatch.Models;

public enum Genre {
    ACAO("Action"),
    COMEDIA("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    TERROR("Terror"),
    CRIME("Crime");

    private String categoryOmdb;

    Genre(String categoryOmdb) {
        this.categoryOmdb = categoryOmdb;
    }

    public static Genre fromString(String text) {
        for (Genre categoria : Genre.values()) {
            if (categoria.categoryOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No categories found for the string found: " + text);
    }
}
