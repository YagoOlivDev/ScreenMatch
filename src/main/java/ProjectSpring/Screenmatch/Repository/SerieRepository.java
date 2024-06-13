package ProjectSpring.Screenmatch.Repository;

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
}
