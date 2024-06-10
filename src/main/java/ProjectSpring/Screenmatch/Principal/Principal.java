package ProjectSpring.Screenmatch.Principal;
import ProjectSpring.Screenmatch.Models.Seasons;
import ProjectSpring.Screenmatch.Models.DataSeries;
import ProjectSpring.Screenmatch.Models.Serie;
import ProjectSpring.Screenmatch.Repository.SerieRepository;
import ProjectSpring.Screenmatch.Services.APIconsumption;
import ProjectSpring.Screenmatch.Services.Convertsdata;
import net.suuft.libretranslate.Language;
import net.suuft.libretranslate.Translator;

import java.util.*;


public class Principal {
    private Scanner reading = new Scanner(System.in);
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f07566d2";
    private APIconsumption consumption = new APIconsumption();
    private Convertsdata convertsdata = new Convertsdata();
    private List<DataSeries> dataSeries = new ArrayList<>();
    private SerieRepository repository;

    public Principal(SerieRepository repository)
    {
        this.repository = repository;
    }

    public void ViewMenu()
    {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Séries buscadas
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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

        private void searchSerieWeb ()
        {
            DataSeries dataSeries = getSeries();
            Serie serie = new Serie(dataSeries);
            repository.save(serie);
            System.out.println(dataSeries);

        }

        private DataSeries getSeries ()
        {
            System.out.println("Enter the name of the series to search");
            var nameSerie = reading.nextLine();
            var json = consumption.extractData(ADDRESS + nameSerie.replace
                    (" ", "+") + API_KEY);
            DataSeries dados = convertsdata.getData(json, DataSeries.class);
            return dados;
        }

        private void searchEpisodeoPerSerie ()
        {
            DataSeries serie = getSeries();
            List<Seasons> season = new ArrayList<>();

            for (int i = 1; i <= serie.totalSeasons(); i++) {
                var json = consumption.extractData(ADDRESS + serie.title().replace
                        (" ", "+") + "&Season=" + i + API_KEY);

                Seasons getseason = convertsdata.getData(json, Seasons.class);
                season.add(getseason);
            }
            season.forEach(System.out::println);
        }

        private void listSearchedSeries()
        {
            List<Serie> series = repository.findAll();
            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenre))
                    .forEach(System.out::println);

        }
    }