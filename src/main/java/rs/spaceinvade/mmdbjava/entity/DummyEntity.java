package rs.spaceinvade.mmdbjava.entity;

import lombok.Data;

@Data
public class DummyEntity extends BaseBean {

	private static final long serialVersionUID = 5841974111374202298L;
	
	private String firstName;
	private String lastName;
	private String dummyString;

}
