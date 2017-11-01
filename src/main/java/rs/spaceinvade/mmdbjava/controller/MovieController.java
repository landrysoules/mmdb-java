package rs.spaceinvade.mmdbjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.spaceinvade.mmdbjava.dto.MediaCard;
import rs.spaceinvade.mmdbjava.entity.Movie;
import rs.spaceinvade.mmdbjava.repository.MovieRepository;
import rs.spaceinvade.mmdbjava.service.IMDBParser;

@RestController
@RequestMapping("movies")
public class MovieController extends BaseController{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private IMDBParser parser;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "/save")
	public Movie save(@RequestBody Movie movie, Model model) throws Exception {
		return movieRepository.save(movie);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public List<Movie> searchByTitle(String title) throws Exception {
		return movieRepository.findByTitle(title);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/searchIMDB")
	public List<MediaCard> searchIMDBByTitle(String title) throws Exception {
		return parser.searchMovie(title);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Movie> getAll() throws Exception {
		return movieRepository.findAll();
	}

}
