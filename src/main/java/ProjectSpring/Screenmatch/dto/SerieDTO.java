package ProjectSpring.Screenmatch.dto;

import ProjectSpring.Screenmatch.Models.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(long id,
                       String title,
                       Integer totalSeasons,
                       double assessment,
                       Genre genre,
                       String actores,
                       String poster,
                       String plot)
{

}
