package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmdb.business.actor.Actor;
import com.bmdb.business.actor.ActorRepository;
import com.bmdb.util.BMDBMaintenanceReturn;

@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
@Controller		
@RequestMapping(path="/Actors")
public class ActorController extends BaseController {
	@Autowired
	private ActorRepository actorRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Actor> getAllActors() {
		return actorRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<Actor> getActor(@RequestParam int id) {
		Optional<Actor> u = actorRepository.findById(id);
		return getReturnArray(u);
	}

	@PostMapping(path="/Add") 
	public @ResponseBody BMDBMaintenanceReturn addNewActor (@RequestBody Actor actor) {
		try {
			actorRepository.save(actor);
			return BMDBMaintenanceReturn.getMaintReturn(actor);
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.getMessage());
		}
	}
	
	@GetMapping(path="/Remove") // Map ONLY GET Requests
	public @ResponseBody BMDBMaintenanceReturn deleteActor (@RequestParam int id) {
		
		Optional<Actor> actor = actorRepository.findById(id);
		try {
			actorRepository.delete(actor.get());
			return BMDBMaintenanceReturn.getMaintReturn(actor.get());
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.toString());
		}
		
	}

	@PostMapping(path="/Change") 
	public @ResponseBody BMDBMaintenanceReturn updateActor (@RequestBody Actor actor) {
		try {
			actorRepository.save(actor);
			return BMDBMaintenanceReturn.getMaintReturn(actor);
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.toString());
		}
		
	}

//	@GetMapping(path="/Authenticate") // Map ONLY GET Requests
//	public @ResponseBody List<Actor> authenticate (@RequestParam String uname
//			, @RequestParam String pwd) {
//		Actor u = actorRepository.findByActorNameAndPassword(uname, pwd);
//		return getReturnArray(u);
//	}	
}