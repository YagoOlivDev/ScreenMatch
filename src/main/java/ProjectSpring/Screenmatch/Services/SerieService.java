package ProjectSpring.Screenmatch.Services;

import ProjectSpring.Screenmatch.Models.Genre;
import ProjectSpring.Screenmatch.Models.Serie;
import ProjectSpring.Screenmatch.Repository.SerieRepository;
import ProjectSpring.Screenmatch.dto.EpisodeDTO;
import ProjectSpring.Screenmatch.dto.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService
{
    @Autowired
    SerieRepository repository;

    public List<SerieDTO> ObterSeries()
    {
        return convertData(repository.findAll());
    }

    public List<SerieDTO> obterTop5()
    {
        return convertData(repository.findTop5ByOrderByAssessmentDesc());
    }

    public List<SerieDTO> obterLancamentos()
    {
        return convertData(repository.topLancamentos());
    }

    public SerieDTO obterPorId(Long id)
    {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent())
        {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitle(),
                    s.getTotalSeasons(), s.getAssessment(), s.getGenre(),
                    s.getActores(), s.getPoster(), s.getPlot());
        }
        return null;
    }

    public List<EpisodeDTO> obterAllSeason(Long id)
    {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent())
        {
            Serie s = serie.get();
            return s.getEpisodes().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getNumberEpisode(), e.getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<SerieDTO> ObterSeriesPerGenre(String nameGenre)
    {
        Genre categoria = Genre.fromPortugues(nameGenre);
        return convertData(repository.findByGenre(categoria));
    }

    public List<EpisodeDTO> ObterPerSeason(Long id, Long number)
    {
        return repository.obterEpisodePerSeason(id, number)
                .stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getNumberEpisode(), e.getTitle()))
                .collect(Collectors.toList());
    }

    private List<SerieDTO> convertData(List<Serie> series)
    {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(),
                        s.getTotalSeasons(), s.getAssessment(), s.getGenre(),
                        s.getActores(), s.getPoster(), s.getPlot())).collect(Collectors.toList());
    }
}
