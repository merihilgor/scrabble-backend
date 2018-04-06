/**
 * 
 */
package org.merih.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merih.Model.Board;
import org.merih.Model.BoardLetter;
import org.merih.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * @author Merih
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoardControllerTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	BoardService boardService;

	/*
	 * REF :
	 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-
	 * testing.html
	 * 
	 */

	@Test
	public void testCreateBoardRestSuccess() {

		String result = this.webClient.post().uri("/board/createboard").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult().getResponseBody();

		assertThat(Long.valueOf(result)).isGreaterThan(0);
	}

	@Test
	public void testSetStatusRestSuccess() {

		Long boardId = boardService.addBoard();
		assertThat(this.webClient.put().uri("board/updatestatus/" + String.valueOf(boardId))
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject("\"ACTIVE\"")).exchange().expectStatus().isOk().expectBody(String.class)
				.returnResult().getResponseBody().contains("Status updated succesfully")).isTrue();

	}

	@Test
	public void testPlayRestSuccess() {
 
		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		List<BoardLetter> moves = new ArrayList<>();
		BoardLetter c = new BoardLetter();
		c.setX(0);
		c.setY(0);
		c.setLetter('C');

		BoardLetter a = new BoardLetter();
		a.setX(1);
		a.setY(0);
		a.setLetter('A');

		BoardLetter m = new BoardLetter();
		m.setX(2);
		m.setY(0);
		m.setLetter('M');

		moves.add(c);
		moves.add(a);
		moves.add(m);

		assertThat(this.webClient.put().uri("board/" + String.valueOf(boardId) + "/play")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(moves)).exchange().expectStatus().isOk().expectBody(String.class)
				.returnResult().getResponseBody().contains("Congrats")).isTrue();

	}

	@Test
	public void testGetWordsRestSuccess() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		BoardLetter c = new BoardLetter();
		c.setX(0);
		c.setY(0);
		c.setLetter('C');

		BoardLetter a = new BoardLetter();
		a.setX(1);
		a.setY(0);
		a.setLetter('A');

		BoardLetter m = new BoardLetter();
		m.setX(2);
		m.setY(0);
		m.setLetter('M');

		BoardLetter[] moves = { c, a, m };

		boardService.play(boardId, moves);

		assertThat(this.webClient.get().uri("board/" + String.valueOf(boardId) + "/getwords").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult().getResponseBody().contains("CAM")).isTrue();

	}

	@Test
	public void testGetBoardContentRestSuccess() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		BoardLetter c = new BoardLetter();
		c.setX(0);
		c.setY(0);
		c.setLetter('C');

		BoardLetter a = new BoardLetter();
		a.setX(1);
		a.setY(0);
		a.setLetter('A');

		BoardLetter m = new BoardLetter();
		m.setX(2);
		m.setY(0);
		m.setLetter('M');

		BoardLetter[] moves = { c, a, m };

		boardService.play(boardId, moves);

		assertThat(this.webClient.get().uri("board/" + String.valueOf(boardId) + "/0").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult().getResponseBody().replaceAll("[\\W]|_", "").replaceAll("null", "").contains("CAM")).isFalse();
 
		assertThat(this.webClient.get().uri("board/" + String.valueOf(boardId) + "/1").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult().getResponseBody().replaceAll("[\\W]|_", "").replaceAll("null", "").contains("CAM")).isTrue();
	}

}
