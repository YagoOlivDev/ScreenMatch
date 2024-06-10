package ProjectSpring.Screenmatch.Models;
import ProjectSpring.Screenmatch.Services.QueryLibreTranslate;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "series")
public class Serie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private double assessment;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String actores;
    private String poster;
    private String plot;

    @OneToMany(mappedBy = "serie")
    private List<Episodes> episodes = new ArrayList<>();

    public Serie(){}

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons)
    {
        this.totalSeasons = totalSeasons;
    }

    public double getAssessment()
    {
        return assessment;
    }

    public void setAssessment(double assessment)
    {
        this.assessment = assessment;
    }

    public Genre getGenre() {

        return genre;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    public String getActores()
    {
        return actores;
    }

    public void setActores(String actores)
    {
        this.actores = actores;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    public String getPlot()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public List<Episodes> getEpisodes()
    {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes)
    {
        this.episodes = episodes;
    }

    public Serie(DataSeries dataSeries)
    {
        this.title = dataSeries.title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.assessment = OptionalDouble.of
                (Double.valueOf(dataSeries.assessment())).orElse(0);
        this.genre = Genre.fromString(dataSeries.genre().split(",")[0].trim());
        this.actores = dataSeries.actores();
        this.poster = dataSeries.poster();
        this.plot = QueryLibreTranslate.obterTraducao(dataSeries.plot());
    }

    @Override
    public String toString() {
        return

                "Serie{" +
                "plot='" + this.plot + '\'' +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", genre=" + genre +
                ", assessment=" + assessment +
                ", totalSeasons=" + totalSeasons +
                ", title=" + title + '\'' +
                '}';

    }
}
