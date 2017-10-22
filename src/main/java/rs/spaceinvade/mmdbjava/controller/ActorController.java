package rs.spaceinvade.mmdbjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.spaceinvade.mmdbjava.entity.Actor;
import rs.spaceinvade.mmdbjava.repository.ActorRepository;

@RestController
@RequestMapping("actors")
public class ActorController extends BaseController{
	
	@Autowired
	private ActorRepository actorRepository;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public Actor save(@RequestBody Actor actor, Model model) throws Exception {
		logger.warn("Actor : " + actor);
		return actorRepository.save(actor);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Actor> getAll() throws Exception {
		return actorRepository.findAll();
	}

}
