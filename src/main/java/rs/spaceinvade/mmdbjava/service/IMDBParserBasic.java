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
		String requestUrl = String.format("%s/find?s=all&q=%s", baseUrl, movieTitle);
		try {
			doc = Jsoup.connect(requestUrl)
					.header("Accept-Language", "de")
					.get();
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

	protected MovieCard buildResultCard(Element titleElement) {
		return getDataFromMoviePage(new MovieCard(), getUrlForMoviePage(titleElement));
	}
	
	protected String getUrlForMoviePage(Element titleBlock) {
		return titleBlock.select("td.result_text a").attr("href");
	}
	
	/**
	 * Complete MovieCard with data from movie page.
	 * @param movieCard
	 * @param url
	 * @return
	 */
	protected MovieCard getDataFromMoviePage(MovieCard movieCard, String url) {
		Document doc = null;
		String requestUrl = String.format("%s%s", baseUrl, url);
		try {
			doc = Jsoup.connect(requestUrl)
					.header("Accept-Language", "de")
					.get();
			Element titleElt = doc.select("h1[itemprop*=name]").first();
			String[] titleAndYear = extractTitleAndYear(titleElt.text());
			String summary = doc.select(".summary_text").first().text();
			movieCard.setTitle(titleAndYear[0]);
			movieCard.setYear(new Integer(titleAndYear[1]));
			movieCard.setSummary(summary);
			logger.debug(movieCard.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return movieCard;
	}
	
	protected String[] extractTitleAndYear(String blob) {
		String[] titleAndYear = new String[2];
		Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");
		Matcher matcher = pattern.matcher(blob);
		matcher.find();
		String rawTitle = matcher.group(1);
		rawTitle = rawTitle.replaceFirst("\u00A0", "");
		titleAndYear[0] = rawTitle;
		titleAndYear[1] = matcher.group(2).trim();
		return titleAndYear;
	}

}
