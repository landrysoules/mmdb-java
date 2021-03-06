package rs.spaceinvade.mmdbjava.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rs.spaceinvade.mmdbjava.dto.MediaCard;

@Service
public class IMDBParserBasic implements IMDBParser {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMDBFetcher fetcher;

	@Value("${imdb.baseUrl}")
	private String baseUrl;
	@Value("${imdb.searchUrl.movies}")
	private String searchMovieUrl;
	@Value("${imdb.searchUrl.series}")
	private String searchSerieUrl;

	@Override
	public List<MediaCard> searchMovie(String title) {

		Instant start = Instant.now();
		Document doc = fetchIMDB(title);
		List<Element> titles = getTitles(doc, 10);
		List<CompletableFuture<MediaCard>> cfs = titles.stream().map(t -> fetcher.buildResultCard(t))
				.collect(Collectors.toList());
		CompletableFuture.allOf(cfs.toArray(new CompletableFuture[cfs.size()])).join();
		Instant end = Instant.now();
		List<MediaCard> mediaCards = cfs.stream().map(c -> {
			try {
				return c.get();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			} catch (ExecutionException e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		})
				.filter(Objects::nonNull) //remove null elements from collection
				.collect(Collectors.toList());
		// Sequential processing : 12 seconds
		// Multithreaded processing : 2 seconds !
		logger.debug("Search processed in {} seconds", Duration.between(start, end).getSeconds());
		return mediaCards;
	}

	protected Document fetchIMDB(String title) {
		Document doc = null;
		String requestUrl = "";
		try {
			requestUrl = baseUrl + String.format(searchMovieUrl, URLEncoder.encode(title, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.debug(String.format("Fetching URL '%s'", requestUrl));
		try {
			doc = Jsoup.connect(requestUrl).header("Accept-Language", "en").get();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return doc;
	}

	protected List<Element> getTitles(Document doc, int max) {
		Elements resultBlocks = doc.select("table.findList");
		Element titlesResultBlock = resultBlocks.first();
		Elements titles = titlesResultBlock.getElementsByClass("findResult");
		return titles.subList(0, max);
	}

}
