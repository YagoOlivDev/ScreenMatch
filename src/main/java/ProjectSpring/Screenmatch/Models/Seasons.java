package ProjectSpring.Screenmatch.Models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Seasons(@JsonAlias("Season") Integer number,
                      @JsonAlias("Episodes") List<EpisodeData> episode)
{
}
