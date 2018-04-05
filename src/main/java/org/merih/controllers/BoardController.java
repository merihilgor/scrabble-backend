/**
 * 
 */
package org.merih.controllers;

import java.util.List;
import java.util.Map;

import org.merih.Model.Board;
import org.merih.Model.BoardHistory;
import org.merih.Model.BoardLetter;
import org.merih.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Merih
 *
 *         Operations pertaining to Board in Scrabble Game.
 * 
 */
@RestController
@RequestMapping("/board")
@Api(value = "scrabble-backend")
public class BoardController {

	@Autowired
	BoardService boardService;

	@ApiOperation(value = "Create a 15x15 Board left upper is x =0, y = 0 and returns boardId" )
	@RequestMapping(value = "/createboard", method = RequestMethod.POST)
	public Long createBoard() {
		return boardService.addBoard();
	}

	@ApiOperation(value = "put letters with x,y indexes on created Board with boardId which is returned from /createboard service")
	@RequestMapping(method = RequestMethod.PUT, value = "/{boardId}/play", produces = "application/json")
	public ResponseEntity<String> play(@PathVariable Long boardId, @RequestBody BoardLetter[] moves) {
		String message = boardService.play(boardId, moves);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@ApiOperation(value = "get words of a Board with spesified boardId")
	@RequestMapping(value = "/{boardId}/getwords", method = RequestMethod.GET, produces = "application/json")
	public Map<Object,Integer> gettWords(@PathVariable Long boardId) {
		return boardService.gelAllWords(boardId);
	}

	@ApiOperation(value = "get content of a Board with spesified boardId and move sequence(from 0 to play sequence)")
	@RequestMapping(value = "/{boardId}/{sequence}", method = RequestMethod.GET, produces = "application/json")
	public List<BoardHistory> getBoardContent(@PathVariable Long boardId, @PathVariable int sequence) {
		return boardService.getBoardHistory(boardId, sequence);
	}


	@ApiOperation(value = "Set status of a Board with spesified boardId")
	@RequestMapping(method = RequestMethod.PUT, value = "/updatestatus/{boardId}", produces = "application/json")
	public ResponseEntity<String> setStatus(@PathVariable Long boardId, @RequestBody Board.Status status) {
		String message = boardService.updateStatus(boardId, status);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	
/*
	@ApiOperation(value = "Get all Boards ")
	@RequestMapping(value = "/allboards", method = RequestMethod.GET, produces = "application/json")
	public List<Board> getAllBoards() {
		return boardService.getAllBoards();

	}

	@ApiOperation(value = "Get a Board with spesified id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public Board getBoard(@PathVariable Long id) {
		return boardService.getBoard(id);
	}
*/
}
