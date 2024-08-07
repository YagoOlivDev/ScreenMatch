package ProjectSpring.Screenmatch.Principal;
import ProjectSpring.Screenmatch.Models.*;
import ProjectSpring.Screenmatch.Repository.SerieRepository;
import ProjectSpring.Screenmatch.Services.APIconsumption;
import ProjectSpring.Screenmatch.Services.Convertsdata;
import net.suuft.libretranslate.Language;
import net.suuft.libretranslate.Translator;
import net.suuft.libretranslate.util.JsonUtil;

import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    private Scanner reading = new Scanner(System.in);
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f07566d2";
    private APIconsumption consumption = new APIconsumption();
    private Convertsdata convertsdata = new Convertsdata();
    private List<DataSeries> dataSeries = new ArrayList<>();
    private SerieRepository repository;
    private Optional<Serie> seriesBDD;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void ViewMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Séries buscadas
                    4 - Buscar séries pelo titulo
                    5 - Buscar séries pelo ator
                    6 - Buscar Top 5 Séries
                    7 - Buscar série por categoria
                    8 - Buscar série pela quantidade 
                        de temporadas e pela avaliação
                    9 - Buscar episódio por trecho
                    10 - Buscar top 5 Episódios por série
                    11 -  Buscar episódios por data
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = reading.nextInt();
            reading.nextLine();

            switch (opcao) {
                case 1:
                    searchSerieWeb();
                    break;
                case 2:
                    searchEpisodeoPerSerie();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    searchSerieForTitle();
                    break;
                case 5:
                    searchSeriesForActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSerieCategory();
                    break;
                case 8:
                    searchTemporadeAndAssessment();
                    break;
                case 9:
                    searchEpisodeSegment();
                    break;
                case 10:
                    searchTop5EpisodeoPerSerie();
                    break;
                case 11:
                    searchEpisodesPerDate();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void searchSerieWeb()
    {
        DataSeries dataSeries = getSeries();
        Serie serie = new Serie(dataSeries);
        repository.save(serie);
        System.out.println(dataSeries);

    }

    private DataSeries getSeries() {
        System.out.println("Enter the name of the series to search");
        var nameSerie = reading.nextLine();
        var json = consumption.extractData(ADDRESS + nameSerie.replace
                (" ", "+") + API_KEY);
        DataSeries dados = convertsdata.getData(json, DataSeries.class);
        return dados;
    }

    private void searchEpisodeoPerSerie() {
        listSearchedSeries();
        System.out.println("Choose the series you want to search for episodes");
        var nameSerie = reading.nextLine();

        seriesBDD = repository.findByTitleContainingIgnoreCase(nameSerie);
        ;

        if (seriesBDD.isPresent()) {
            var serieEncontrada = seriesBDD.get();
            List<Seasons> season = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalSeasons(); i++) {
                var json = consumption.extractData(ADDRESS + serieEncontrada
                        .getTitle().replace
                                (" ", "+") + "&Season=" + i + API_KEY);

                Seasons getseason = convertsdata.getData(json, Seasons.class);
                season.add(getseason);
            }

            List<Episodes> episodes = season.stream()
                    .flatMap(d -> d.episode().stream()
                            .map(e -> new Episodes(d.number(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodes(episodes);
            repository.save(serieEncontrada);
        } else {
            System.out.println("Series not found!");
        }
    }

    private void listSearchedSeries() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieForTitle() {
        System.out.println("Enter the name of the series to search");
        var nameSerie = reading.nextLine();
        seriesBDD = repository.findByTitleContainingIgnoreCase(nameSerie);

        if (seriesBDD.isPresent()) {
            System.out.println("Series data: " + seriesBDD.get());
        } else {
            System.out.println("Series not found");
        }
    }

    private void searchSeriesForActor() {
        System.out.println("Enter actor name");
        var nameActor = reading.nextLine();
        System.out.println("What is the minimum rating desired?");
        var assessment = reading.nextDouble();

        List<Serie> serieSearchActor = repository
                .findByActoresContainingIgnoreCaseAndAssessmentGreaterThanEqual
                        (nameActor, assessment);

        System.out.println("series that " + nameActor + " worked on: ");
        serieSearchActor.forEach(s -> System.out.println(s.getTitle() + " {assessment: " +
                s.getAssessment() + "}"));

    }

    private void searchTop5Series()
    {
        List<Serie> seriesTop = repository.findTop5ByOrderByAssessmentDesc();
        seriesTop.forEach(s -> System.out.println(s.getTitle() + " {assessment: " +
                s.getAssessment() + "}"));
    }

    private void searchSerieCategory()
    {
        System.out.println("What genre of series should I look for?");
        var nameGenre = reading.nextLine();

        Genre genre = Genre.fromPortugues(nameGenre);

        List<Serie> serieCategory = repository.findByGenre(genre);
        serieCategory.forEach(s -> System.out.println(s.getTitle() + " {assessment: " +
                s.getAssessment() + "}"));
    }

    private void searchTemporadeAndAssessment()
    {
        System.out.println("Enter the maximum number of seasons: ");
        var numberTemporade = reading.nextInt();
        reading.nextLine();

        System.out.println("Enter the review you want: ");
        var assessment = reading.nextDouble();
        reading.nextLine();

        List<Serie> seriesSearched = repository.seriesPerSeasonAndAssessment
                (numberTemporade, assessment);

        seriesSearched.forEach(s -> System.out.println(s.getTitle() + " {assessment: " +
                s.getAssessment() + "}"));
    }

    private void searchEpisodeSegment()
    {
        System.out.println("Type the excerpt you want to find the corresponding episode");
        var excerpt = reading.nextLine();
        List<Episodes> episodesSearched = repository.EpisodesPerExcerpt(excerpt);

        episodesSearched.forEach
                (e -> System.out.println(e.getSerie().getTitle() + " {"+ e.getTitle() +"}"));

    }

    private void searchTop5EpisodeoPerSerie()
    {
        searchSerieForTitle();
        if(seriesBDD.isPresent())
        {
            Serie serie = seriesBDD.get();
            List<Episodes> topEpisodes = repository.Top5EpisodesPerSerie(serie);

            topEpisodes.forEach(e -> System.out.println
                    (e.getSerie().getTitle() + " {"+e.getTitle()+" "+ e.getAssessement()+"}"));
        }
    }

    private void searchEpisodesPerDate()
    {
        searchSerieForTitle();
        if(seriesBDD.isPresent())
        {
            Serie serie = seriesBDD.get();

            System.out.println("Enter the launch year: ");
            var dateYear = reading.nextInt();
            reading.nextLine();

            List<Episodes> episodesYear = repository.EpisodesPerSerieYear(serie, dateYear);
            episodesYear.forEach(System.out::println);
        }
    }
}