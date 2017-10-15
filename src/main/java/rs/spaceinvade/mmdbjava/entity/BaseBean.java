package rs.spaceinvade.mmdbjava.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public abstract class BaseBean implements Serializable {
//FIXME: Check why dates are stored as 1 day before in mongo (when the REST calls return correct dates though)
	private static final long serialVersionUID = 3468202273062408218L;
	
	  @Id
	  private String id;

}
