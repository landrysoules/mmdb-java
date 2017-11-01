package rs.spaceinvade.mmdbjava.dto;

import lombok.Data;

/**
 * Represents both movies and series.
 * @author landry
 *
 */
@Data
public class MediaCard implements BaseDTO{
	
	private static final long serialVersionUID = 6070593953490576303L;
	private String title;
	private int year;
	private String summary;
}
