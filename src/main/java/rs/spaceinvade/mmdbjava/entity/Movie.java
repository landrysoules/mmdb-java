package rs.spaceinvade.mmdbjava.entity;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
public class Movie extends BaseBean {

	private static final long serialVersionUID = 5070328337916552007L;
	
	@Indexed
	private String title;
	private Integer year;
	private String summary;
	private List<Person> directors;
	private List<Person> writers;
	private List<Person> actors;

}
