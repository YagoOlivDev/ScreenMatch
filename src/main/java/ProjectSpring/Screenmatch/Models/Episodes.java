package ProjectSpring.Screenmatch.Models;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name="episodes")
public class Episodes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer season;
    private String title;
    private Integer numberEpisode;
    private double assessement;
    private LocalDate realease;
    @ManyToOne
    private Serie serie;

    public Episodes(){}

    public Episodes(Integer numberSeason, EpisodeData episodeData)
    {

        this.season = numberSeason;
        this.title = episodeData.title();
        this.numberEpisode = episodeData.number();

        try
        {
            this.assessement = Double.valueOf(episodeData.assessment());
            this.realease = LocalDate.parse(episodeData.releaseDate());
        }
        catch (NumberFormatException e)
        {
            this.assessement = 0.0;
        }
        catch (DateTimeException e)
        {
            this.realease = null;
        }

    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Serie getSerie()
    {
        return serie;
    }

    public void setSerie(Serie serie)
    {
        this.serie = serie;
    }

    public Integer getSeason()
    {
        return season;
    }

    public void setSeason(Integer season)
    {
        this.season = season;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getNumberEpisode()
    {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode)
    {
        this.numberEpisode = numberEpisode;
    }

    public double getAssessement()
    {
        return assessement;
    }

    public void setAssessement(double assessement)
    {
        this.assessement = assessement;
    }

    public LocalDate getRealease()
    {
        return realease;
    }

    public void setRealease(LocalDate realease)
    {
        this.realease = realease;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", assessement=" + assessement +
                ", realease=" + realease +
                '}';
    }
}


