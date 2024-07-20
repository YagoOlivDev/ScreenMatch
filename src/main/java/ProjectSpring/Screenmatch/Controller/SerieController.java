package ProjectSpring.Screenmatch.Controller;

import ProjectSpring.Screenmatch.Services.SerieService;
import ProjectSpring.Screenmatch.dto.EpisodeDTO;
import ProjectSpring.Screenmatch.dto.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController
{
    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obterSerie()
    {
        return service.ObterSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5()
    {
        return service.obterTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos()
    {
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id)
    {
        return service.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> obterAllSeason(@PathVariable Long id)
    {
        return service.obterAllSeason(id);
    }

    @GetMapping("/{id}/temporadas/{number}")
    public List<EpisodeDTO> obterPerSeason(@PathVariable Long id, @PathVariable Long number)
    {
        return service.ObterPerSeason(id, number);
    }

    @GetMapping("/categoria/{nameGenre}")
    public List<SerieDTO> obterSeriesPerGenre(@PathVariable String nameGenre)
    {
        return service.ObterSeriesPerGenre(nameGenre);
    }
}
