package ProjectSpring.Screenmatch.Models;

public enum Genre {
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    TERROR("Terror", "Terror"),
    CRIME("Crime", "Crime");

    private String categoryOmdb;
    private String categoryPortugues;

    Genre(String categoryOmdb, String categoryPortugues)
    {
        this.categoryOmdb = categoryOmdb;
        this.categoryPortugues = categoryPortugues;
    }

    public static Genre fromString(String text)
    {
        for (Genre categoria : Genre.values())
        {
            if (categoria.categoryOmdb.equalsIgnoreCase(text))
            {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No categories found for the string found: " + text);
    }

    public static Genre fromPortugues(String text)
    {
        for (Genre categoria : Genre.values())
        {
            if (categoria.categoryPortugues.equalsIgnoreCase(text))
            {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No categories found for the string found: " + text);
    }
}
