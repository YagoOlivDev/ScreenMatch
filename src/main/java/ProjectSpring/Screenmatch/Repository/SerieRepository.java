package ProjectSpring.Screenmatch.Repository;

import ProjectSpring.Screenmatch.Models.Episodes;
import ProjectSpring.Screenmatch.Models.Genre;
import ProjectSpring.Screenmatch.Models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long>
{
    Optional<Serie> findByTitleContainingIgnoreCase(String nameSerie);

    List<Serie> findByActoresContainingIgnoreCaseAndAssessmentGreaterThanEqual
            (String nameActor, double assessment);

    List<Serie> findTop5ByOrderByAssessmentDesc();

    List<Serie> findByGenre(Genre genre);

    List<Serie> findByTotalSeasonsLessThanEqualAndAssessmentGreaterThanEqual
            (int totalSeason, double assessment);

    @Query("Select s from Serie s WHERE s.totalSeasons <= :totalSeasons and assessment >= :assessment")
    List<Serie> seriesPerSeasonAndAssessment(int totalSeasons, double assessment);

    @Query("SELECT e FROM Serie s JOIN s.episodes e where e.title ILIKE %:excerpt%")
    List<Episodes> EpisodesPerExcerpt(String excerpt);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :nameSerie ORDER BY assessement DESC LIMIT 5")
    List<Episodes> Top5EpisodesPerSerie(Serie nameSerie);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR(e.realease) >= :dateYear")
    List<Episodes> EpisodesPerSerieYear(Serie serie, int dateYear);

    @Query("SELECT s FROM Serie s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.realease) DESC LIMIT 5")
    List<Serie> topLancamentos();

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s.id = :id AND e.season = :number")
    List<Episodes> obterEpisodePerSeason(Long id, Long number);
}
