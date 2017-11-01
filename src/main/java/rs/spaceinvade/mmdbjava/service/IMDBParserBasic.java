package rs.spaceinvade.mmdbjava.service;

import java.io.IOException;
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

import rs.spaceinvade.mmdbjava.dto.MediaCard;

@Service
public class IMDBParserBasic implements IMDBParser {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${imdb.baseUrl}")
	private String baseUrl;
	@Value("${imdb.searchUrl.movies}")
	private String searchMovieUrl;
	@Value("${imdb.searchUrl.series}")
	private String searchSerieUrl;

	@Override
	public List<MediaCard> searchMovie(String title) {
		Document doc = fetchIMDB(title);
		List<Element> titles = getTitles(doc, 10);
		//TODO: run this in async
		return titles.stream().map(t -> buildResultCard(t)).collect(Collectors.toList());
	}

	protected Document fetchIMDB(String title) {
		Document doc = null;
		String requestUrl = baseUrl + String.format(searchMovieUrl, title);
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

	protected MediaCard buildResultCard(Element titleElement) {
		return getDataFromMoviePage(new MediaCard(), getUrlForMoviePage(titleElement));
	}

	protected String getUrlForMoviePage(Element titleBlock) {
		return titleBlock.select("td.result_text a").attr("href");
	}

	/**
	 * Complete MovieCard with data from movie page.
	 * 
	 * @param movieCard
	 * @param url
	 * @return
	 */
	protected MediaCard getDataFromMoviePage(MediaCard mediaCard, String url) {
		Document doc = null;
		String requestUrl = String.format("%s%s", baseUrl, url);
		try {
			doc = Jsoup.connect(requestUrl).header("Accept-Language", "de").get();
			Element titleElt = doc.select("h1[itemprop*=name]").first();
			String[] titleAndYear = extractTitleAndYear(titleElt.text());
			if (titleAndYear.length == 0)
				return null;
			String summary = doc.select(".summary_text").first().text();
			mediaCard.setTitle(titleAndYear[0]);
			mediaCard.setYear(new Integer(titleAndYear[1]));
			mediaCard.setSummary(summary);
			logger.debug(mediaCard.toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (NullPointerException npe) {
			logger.error(npe.getMessage(), npe);
			return null;
		}
		return mediaCard;
	}

	protected String[] extractTitleAndYear(String blob) {
		logger.debug("Extracting title and year for string '{}'", blob);
		String[] titleAndYear = new String[2];
		Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");
		Matcher matcher = pattern.matcher(blob);
		if (matcher.find()) {
			String rawTitle = matcher.group(1);
			rawTitle = rawTitle.replaceFirst("\u00A0", "");
			titleAndYear[0] = rawTitle;
			titleAndYear[1] = matcher.group(2).trim();
			return titleAndYear;
		} else {
			logger.info("Incomplete media title : we won't process it");
			return new String[0];
		}
	}

}
