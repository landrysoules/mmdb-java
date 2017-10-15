package rs.spaceinvade.mmdbjava.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class Person extends BaseBean {

	private static final long serialVersionUID = -445079246333127960L;
	private String firstName;
	@Indexed
	private String lastName;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
}
