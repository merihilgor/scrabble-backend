/**
 * 
 */
package org.merih.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.merih.Model.Board;
import org.merih.Model.BoardLetter;
import org.merih.Model.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Merih
 *
 */
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public Board getBoard(Long id) {
		return boardRepository.findById(id).orElse(null);
	}

	public List<String> gelAllWords(Long id) {
		return getWords(getBoard(id).getContent());
	}

	public Board getBoardHistory(Long id, int seq) {
		return boardRepository.findById(id).orElse(null);
	}

	public Long addBoard() {
		Board b = new Board();
		return boardRepository.save(b).getId();
	}

	public void updateBoard(Board b) {
		boardRepository.save(b);
	}

	public String updateStatus(Long boardId, Board.Status status) {
		Board b = getBoard(boardId);

		String message = "Status is undefimed !!";
		if (!Board.Status.PASSIVE.equals(b.getStatus())) {
			b.setStatus(status);
			updateBoard(b);
			message = "Status updated succesfully";
		} else {
			message = "You can not update a PASSIVE board";
		}

		return message;
	}

	public List<String> getWords(String[][] content) {
 
		return getEverything(content).stream()
				.filter(DictionaryService::isAWord)
				.collect(Collectors.toCollection(ArrayList::new));

	}

	public String play(Long boardId, BoardLetter... args) {
		int totalPoints = 0;
		Board b = getBoard(boardId);
		String message = "OK";
		if (Board.Status.PASSIVE.equals(b.getStatus())) {
			message = "You can't play on PASSIVE Boards !!";
		} else {

		
			final String[][] existingContent = b.getContent();
			 
			 
			final List<String> exisitngThings = getEverything(existingContent);
			System.out.println(" exisitngThings " +getEverything(existingContent));
			
			final List<String> existingWords = getWords(existingContent);
			System.out.println(" existingWords " +getWords(existingContent));
			
			
			if (isAnyLetterNotOverlapping(existingContent, args)) {			
				final String[][] newContent = putLetterstoContent(existingContent, args);

				
				// To Do check if is there a nearby letter.
				
				final List<String> newWords = getWords(newContent).stream()
						.filter(e -> !existingWords.contains(e))
						.collect(Collectors.toCollection(ArrayList::new));
						 

				System.out.println(" newWords " +newWords); 
					
				System.out.println("  getEverything(newContent) " + getEverything(newContent)); 
				System.out.println(" exisitngThings " +exisitngThings); 
				
				
				
				// Horizontal and vertical word candidates
				final List<String> newThings  = getEverything(newContent).stream()
						.filter(e -> !exisitngThings.contains(e))
						.collect(Collectors.toCollection(ArrayList::new));

				System.out.println(" newThings " +newThings);	
				
				
				final List<String> nonWords =newThings.stream()
						.filter(s -> s.length() > 1)
						.filter(e -> !newWords.contains(e))		
						.collect(Collectors.toCollection(ArrayList::new));
				System.out.println(" non words " +nonWords);	
				
				if (!nonWords.isEmpty()) {
					System.out.println(" non words " +nonWords);	
					message = "Words not found in Dictionary :  " + nonWords.toString();
				} else {
					System.out.println(" new words " + newWords);	
					totalPoints = newWords.stream()
							.filter(e -> !existingWords.contains(e))
							.mapToInt(DictionaryService::calculatePoints).sum();
					message = "Congrats ! you got " + totalPoints+ " points !";
					
					b.setContent(newContent);
					this.updateBoard(b);
				}

			
			} else {
				message = "You should find empty places to put your Letters !!";
			}

		}
		return message;
	}

	private boolean isAnyLetterNotOverlapping(String[][] content, BoardLetter... args) {

		boolean isNotOverLapping = true;
		for (BoardLetter L : args) {
			if (content[L.getX()][L.getY()] != null)
				isNotOverLapping = false;
			break;

		}
		return isNotOverLapping;
	}

	private List<String> getEverything(String[][] content) {

		List<String> words = new ArrayList<>();
		String terminator = "null";
		StringBuilder col = new StringBuilder(250);

		IntStream.range(0, content.length).forEach(x -> {
			final String row = Arrays.toString(content[x]).replaceAll("[\\W]|_", "").concat(terminator)
					.replaceAll(terminator, ",");
			// System.out.println(row);
			words.addAll(Arrays.asList(row.split(",")));

		});

		IntStream.range(0, content[0].length).forEach(x -> {
			IntStream.range(0, content.length).forEach(y -> {
				col.append(content[y][x]);
			});
			col.append(terminator);

		});
		String colStr = col.toString();

		// System.out.println(colStr);
		words.addAll(Arrays.asList(colStr.split(terminator)));

		// System.out.println(words);

		return words.stream().filter(s -> s.length() > 0)
				// .filter(StringUtils::isNotBlank)
				.collect(Collectors.toCollection(ArrayList::new));

	}

	private String[][] putLetterstoContent(String[][] content, BoardLetter... args) {	
		String[][] newcontent = Arrays.stream(content).toArray(String[][]::new);
		for (BoardLetter L : args) {
			newcontent[L.getX()][L.getY()] = String.valueOf(L.getLetter());
		}
		return newcontent;
	}

	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}

	public List<Board> getAllBoards() {
		List<Board> boards = new ArrayList<>();
		boardRepository.findAll().forEach(boards::add);
		return boards;
	}

}
