package rs.spaceinvade.mmdbjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import rs.spaceinvade.mmdbjava.entity.Actor;

public interface ActorRepository extends MongoRepository<Actor, String> {

  
}