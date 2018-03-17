package rs.spaceinvade.mmdbjava.service;

import rs.spaceinvade.mmdbjava.model.TMDBMovieResult;

public interface TMDBSearch {
	
	public TMDBMovieResult multiSearch(String search) throws Exception;

}
