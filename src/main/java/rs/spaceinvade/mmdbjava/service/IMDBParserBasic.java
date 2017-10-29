package rs.spaceinvade.mmdbjava.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rs.spaceinvade.mmdbjava.dto.MovieCard;

@Service
public class IMDBParserBasic implements IMDBParser {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${imdb.baseUrl}")
	private String baseUrl;

	@Override
	public List<MovieCard> searchMovie(String movieTitle) {
		Document doc = null;
		logger.debug("***************" + String.format("%s/find?s=all&q=%s", baseUrl, movieTitle));
		try {
			doc = Jsoup.connect(String.format("http://www.imdb.com/find?s=all&q=%s", movieTitle)).get();
			Elements titles = getTitles(doc);
			return titles.stream().map(t -> buildResultCard(t)).collect(Collectors.toList());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<MovieCard>(0);
	}

	protected Elements getTitles(Document doc) {
		Elements resultBlocks = doc.select("table.findList");
		Element titlesResultBlock = resultBlocks.first();
		return titlesResultBlock.getElementsByClass("findResult");
	}

	//TODO: Have to follow link to movie page to retrieve correct info, and summary.
	protected MovieCard buildResultCard(Element titleElement) {
		MovieCard movieCard = new MovieCard();
		String rawTitle = titleElement.select("td.result_text").text();
		Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");
		Matcher matcher = pattern.matcher(rawTitle);
		matcher.find();
		movieCard.setTitle(matcher.group(1).trim());
		movieCard.setYear(new Integer(matcher.group(2)));
		return movieCard;
	}

}
