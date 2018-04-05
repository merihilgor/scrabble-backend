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
		boardService.updateStatus(boardId, Board.Status.ACTIVE).contains("succesfully");

	 
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
		
		boardService.play(boardId, moves).contains("Congrats");

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

		boardService.gelAllWords(boardId).containsValue("CAM");


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
		
		List<BoardHistory> history = boardService.getBoardHistory(boardId, 0);
		assertThat(history.get(0).getBoardContent().toString().contains("CAM")).isFalse();
		
		history = boardService.getBoardHistory(boardId, 1);
		history.get(0).getBoardContent().toString().contains("CAM");

 
	}
	
}
