package rs.spaceinvade.mmdbjava.service;

import java.util.concurrent.CompletableFuture;

import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Async;

import rs.spaceinvade.mmdbjava.dto.MediaCard;

public interface IMDBFetcher {
	@Async
	public CompletableFuture<MediaCard> buildResultCard(Element titleElement);
	
}
