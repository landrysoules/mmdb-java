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
	public static HoverflyRule hoverflyRule = HoverflyRule.inCaptureOrSimulationMode("stay.json");

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "classpath:stay_trs.html")
	private Resource stayTrsPartial;
	
	@Autowired
	private IMDBParserBasic parser;

	@Test
	public void searchReturnsAListOfCards() {
		List<MovieCard> movies = parser.searchMovie("stay");
		assertEquals("Stay (I)", movies.get(0).getTitle());
		assertEquals("Stay (I)", movies.get(1).getTitle());
		assertEquals(2005, movies.get(0).getYear());
		assertEquals(2006, movies.get(1).getYear());
	}

	@Test
	public void buildResultCardReturnsCorrectTitleAndYear() {
		try {
			String fileContent = FileUtils.readFileToString(stayTrsPartial.getFile());
			logger.debug("file content" + fileContent);
			Document partialDoc = Jsoup.parse(fileContent);
			logger.debug("partial doc\n" + partialDoc.outerHtml());
			Elements trs = partialDoc.getElementsByClass("findResult");
			MovieCard movieCard = parser.buildResultCard(trs.first());
			assertEquals("Stay (I)", movieCard.getTitle());
			assertEquals(2005, movieCard.getYear());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}
}
