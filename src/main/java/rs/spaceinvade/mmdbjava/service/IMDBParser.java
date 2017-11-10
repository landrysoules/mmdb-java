package rs.spaceinvade.mmdbjava.service;

import java.util.List;

import org.apache.http.nio.reactor.IOReactorException;

import rs.spaceinvade.mmdbjava.dto.MediaCard;

public interface IMDBParser {

	public List<MediaCard> searchMovie(String movieTitle);
	
}
