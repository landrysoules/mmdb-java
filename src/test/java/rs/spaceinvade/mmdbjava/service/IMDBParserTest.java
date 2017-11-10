package rs.spaceinvade.mmdbjava.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import rs.spaceinvade.mmdbjava.dto.MediaCard;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IMDBParserTest {

//	@ClassRule
//	public static HoverflyRule hoverflyRule = HoverflyRule.inCaptureOrSimulationMode("imdb-parser.json");

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "classpath:stay_trs.html")
	private Resource stayTrsPartial;

	@Autowired
	private IMDBParserBasic parser;

	@Test
	public void searchReturnsAListOfCards() {
		List<MediaCard> movies = parser.searchMovie("rambo");
		MediaCard mc1 = movies.get(0);
		MediaCard mc2 = movies.get(1);
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
	

}
