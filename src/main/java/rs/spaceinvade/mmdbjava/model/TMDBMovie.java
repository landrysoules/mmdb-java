package rs.spaceinvade.mmdbjava.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class TMDBMovie {
	@JsonAlias({"title", "name"})
	private String title;
	
	@JsonAlias({"media_type"})
	private String mediaType;
}
