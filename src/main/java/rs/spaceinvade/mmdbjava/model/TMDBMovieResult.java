package rs.spaceinvade.mmdbjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TMDBMovieResult {

private TMDBMovie[] results;
}
