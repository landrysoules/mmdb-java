package rs.spaceinvade.mmdbjava.dto;

import lombok.Data;



/**
 * Represents a movie search result.
 * @author landry
 *
 */
@Data
public class MovieCard implements SearchResultCard{

	private String title;
	private int year;
}
