package rs.spaceinvade.mmdbjava.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * Represents everything related to people (actors, directors etc).
 * @author landry
 *
 */
@Data
public class PeopleCard implements BaseDTO{

	private static final long serialVersionUID = -200300388483715015L;
	private String firstName;
	private String lastName;
	private LocalDate dob;
}
