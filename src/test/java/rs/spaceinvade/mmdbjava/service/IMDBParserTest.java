package rs.spaceinvade.mmdbjava.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import io.specto.hoverfly.junit.rule.HoverflyRule;
import rs.spaceinvade.mmdbjava.dto.MovieCard;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IMDBParserTest {

	@ClassRule
	public static HoverflyRule hoverflyRule = HoverflyRule.inCaptureOrSimulationMode("imdb-parser.json");

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "classpath:stay_trs.html")
	private Resource stayTrsPartial;

	@Autowired
	private IMDBParserBasic parser;

	@Test
	public void searchReturnsAListOfCards() {
		List<MovieCard> movies = parser.searchMovie("rambo");
		MovieCard mc1 = movies.get(0);
		MovieCard mc2 = movies.get(1);
		assertEquals("First Blood", mc1.getTitle());
		assertEquals("Rambo", mc2.getTitle());
		assertEquals(1982, mc1.getYear());
		assertEquals(2008, mc2.getYear());
		assertEquals(
				"Former Green Beret John Rambo is pursued into the mountains surrounding a small town by a tyrannical sheriff and his deputies, forcing him to survive using his combat skills.",
				mc1.getSummary());
		assertEquals(
				"In Thailand, John Rambo joins a group of mercenaries to venture into war-torn Burma, and rescue a group of Christian aid workers who were kidnapped by the ruthless local infantry unit.",
				mc2.getSummary());
	}

	// @Test
	public void buildResultCardReturnsCorrectTitleAndYear() {
		try {
			String fileContent = FileUtils.readFileToString(stayTrsPartial.getFile());
			logger.debug("file content" + fileContent);
			Document partialDoc = Jsoup.parse(fileContent);
			logger.debug("partial doc\n" + partialDoc.outerHtml());
			Elements trs = partialDoc.getElementsByClass("findResult");
			MovieCard movieCard = parser.buildResultCard(trs.first());
			assertEquals("First Blood", movieCard.getTitle());
			assertEquals(1982, movieCard.getYear());
			MovieCard movieCard2 = parser.buildResultCard(trs.last());
			assertEquals("Rambo", movieCard2.getTitle());
			assertEquals(2008, movieCard2.getYear());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

}
