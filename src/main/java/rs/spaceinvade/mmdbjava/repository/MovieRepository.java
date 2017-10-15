package rs.spaceinvade.mmdbjava.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import rs.spaceinvade.mmdbjava.entity.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {

    public List<Movie> findByTitle(String title);
  
}