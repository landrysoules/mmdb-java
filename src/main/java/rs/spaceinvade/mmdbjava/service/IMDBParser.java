package rs.spaceinvade.mmdbjava.service;

import java.util.List;

import rs.spaceinvade.mmdbjava.dto.MovieCard;

public interface IMDBParser {

	public List<MovieCard> searchMovie(String movieTitle);
	
}
