package rs.spaceinvade.mmdbjava.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rs.spaceinvade.mmdbjava.entity.DummyEntity;


@RestController
@RequestMapping("dummy")
public class DummyController extends BaseController {

	@RequestMapping(method = GET, value = "/display")
	@ResponseStatus(value = OK)
	public DummyEntity display() throws Exception {
		DummyEntity dummyEntity = new DummyEntity();
		dummyEntity.setFirstName("Landry");
		dummyEntity.setLastName("Soules");
		dummyEntity.setDummyString("dummyString");
		logger.warn("Dummy Entity : " + dummyEntity);
		return dummyEntity;
	}
	
}
