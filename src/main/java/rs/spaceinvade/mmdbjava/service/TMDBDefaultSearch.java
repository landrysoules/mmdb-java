package rs.spaceinvade.mmdbjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import rs.spaceinvade.mmdbjava.model.TMDBMovieResult;

@Service
public class TMDBDefaultSearch implements TMDBSearch{

	@Value("${api.root}")
	private String apiRoot;
	
	@Value("${apiKey}")
	private String apiKey;
	
	@Value("${api.multisearch}")
	private String multiSearch;
	
	@Autowired
	private RestTemplateBuilder restBuilder;
	
	@Override
	public TMDBMovieResult multiSearch(String search) throws Exception {
		RestTemplate rest = restBuilder.build();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String url = apiRoot + "/" + multiSearch;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam("api_key", apiKey)
		        .queryParam("query", search);
		
		HttpEntity<TMDBMovieResult> entity = new HttpEntity<TMDBMovieResult>(headers);
		String uri = builder.build().encode().toUri().toString();
		ResponseEntity<TMDBMovieResult> resp = rest.exchange(
		        builder.build().encode().toUri(), 
		        HttpMethod.GET, 
		        entity, 
		        TMDBMovieResult.class);
		return resp.getBody();
	}
}
