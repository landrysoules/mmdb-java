package rs.spaceinvade.mmdbjava.service;

import java.util.List;

import rs.spaceinvade.mmdbjava.dto.MediaCard;

public interface IMDBParser {

	public List<MediaCard> searchMovie(String movieTitle);
	
}
