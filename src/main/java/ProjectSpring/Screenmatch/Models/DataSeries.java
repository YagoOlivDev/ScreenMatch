package ProjectSpring.Screenmatch.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("imdbRating") String assessment,
                         @JsonAlias("Genre") String genre,
                         @JsonAlias("Actors") String actores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String plot)
{

}
