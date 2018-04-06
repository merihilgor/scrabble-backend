/**
 * 
 */
package org.merih.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merih.Model.Board;
import org.merih.Model.BoardHistory;
import org.merih.Model.BoardLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Merih
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoardServiceTest {

	@Autowired
	BoardService boardService;

	@Test
	public void testCreateBoardSuccess() {

		assertThat(boardService.addBoard()).isGreaterThan(0);

	}

	@Test
	public void testSetStatusSuccess() {

		Long boardId = boardService.addBoard();
		assertThat(boardService.updateStatus(boardId, Board.Status.ACTIVE).contains("succesfully")).isTrue();
	}

	@Test
	public void testSetStatusofPassiveBoard() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.PASSIVE);
		assertThat(
				boardService.updateStatus(boardId, Board.Status.ACTIVE).contains("You can not update a PASSIVE board"))
						.isTrue();
	}

	@Test
	public void testPlayonNonActiveBoard() {

		Long boardId = boardService.addBoard();
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

		assertThat(boardService.play(boardId, moves).contains("You can play on ACTIVE Boards only")).isTrue();

	}

	@Test
	public void testPlaySuccess() {

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

		assertThat(boardService.play(boardId, moves).contains("Congrats")).isTrue();

	}

	@Test
	public void testPlayonNonEmptyCells() {

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

		assertThat(boardService.play(boardId, moves).contains("Congrats")).isTrue();
		assertThat(boardService.play(boardId, moves).contains("You should find empty places to put your Letters"))
				.isTrue();

	}

	@Test
	public void testPlayonNotFoundInDictionary() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		BoardLetter c = new BoardLetter();
		c.setX(0);
		c.setY(0);
		c.setLetter('C');

		BoardLetter c1 = new BoardLetter();
		c1.setX(1);
		c1.setY(0);
		c1.setLetter('C');

		BoardLetter[] moves = { c, c1 };

		assertThat(boardService.play(boardId, moves).contains("Words not found in Dictionary")).isTrue();

	}

	@Test
	public void testPlayonNotNextoAnotherTest() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		BoardLetter m = new BoardLetter();
		m.setX(0);
		m.setY(0);
		m.setLetter('M');

		BoardLetter a = new BoardLetter();
		a.setX(1);
		a.setY(0);
		a.setLetter('A');

		BoardLetter s = new BoardLetter();
		s.setX(2);
		s.setY(0);
		s.setLetter('s');

		BoardLetter aa = new BoardLetter();
		aa.setX(3);
		aa.setY(0);
		aa.setLetter('A');

		BoardLetter[] move1 = { m, a, s, aa };

		boardService.play(boardId, move1);

		m.setX(5);
		m.setY(5);
		a.setX(6);
		a.setY(5);
		s.setX(7);
		s.setY(5);
		aa.setX(8);
		aa.setY(5);

		BoardLetter[] move2 = { m, a, s, aa };

		assertThat(boardService.play(boardId, move2).contains("You should start from next to another word ")).isTrue();

	}

	@Test
	public void testPlaytNextoAnotherTestSuccess() {

		Long boardId = boardService.addBoard();
		boardService.updateStatus(boardId, Board.Status.ACTIVE);

		BoardLetter m = new BoardLetter();
		m.setX(0);
		m.setY(0);
		m.setLetter('M');

		BoardLetter a = new BoardLetter();
		a.setX(1);
		a.setY(0);
		a.setLetter('A');

		BoardLetter s = new BoardLetter();
		s.setX(2);
		s.setY(0);
		s.setLetter('s');

		BoardLetter aa = new BoardLetter();
		aa.setX(3);
		aa.setY(0);
		aa.setLetter('A');

		BoardLetter[] move1 = { m, a, s, aa };

		boardService.play(boardId, move1);

		BoardLetter L = new BoardLetter();
		L.setX(4);
		L.setY(0);
		L.setLetter('L');

		BoardLetter A = new BoardLetter();
		A.setX(4);
		A.setY(1);
		A.setLetter('A');

		BoardLetter LL = new BoardLetter();
		LL.setX(4);
		LL.setY(2);
		LL.setLetter('L');

		BoardLetter E = new BoardLetter();
		E.setX(4);
		E.setY(3);
		E.setLetter('E');

		BoardLetter[] move2 = { L, A, LL, E };
		// MASAL and LALE total 12 points
		assertThat(boardService.play(boardId, move2).contains("Congrats ! you got 12 points !")).isTrue();
	}

	@Test
	public void testGetWordsSuccess() {

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

		assertThat(boardService.gelAllWords(boardId).keySet().toString().contains("CAM")).isTrue();

	}

	@Test
	public void testGetBoardContentSuccess() {

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

		List<BoardHistory> history = boardService.getBoardHistory(boardId, 0);
		assertThat(history.get(0).getBoardContent().toString().contains("CAM")).isFalse();

		history = boardService.getBoardHistory(boardId, 1);

		assertThat(boardService.getEverything(history.get(0).getBoardContent()).stream().anyMatch(e -> "CAM".equals(e)))
				.isTrue();

	}

}
