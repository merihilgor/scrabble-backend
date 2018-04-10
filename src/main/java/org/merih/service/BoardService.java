/**
 * 
 */
package org.merih.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.merih.Model.Board;
import org.merih.Model.BoardHistory;
import org.merih.Model.BoardHistoryRepository;
import org.merih.Model.BoardLetter;
import org.merih.Model.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Merih
 *
 */
@Service
public class BoardService {

	@Autowired
	@Resource
	private BoardRepository boardRepository;

	@Autowired
	@Resource
	private BoardHistoryRepository boardHistoryRepository;

	public Board getBoard(Long id) {
		return boardRepository.findById(id).orElse(null);
	}

	public Map<Object, Integer> gelAllWords(Long id) {
		Map<Object, Integer> map = getWords(getBoard(id).getContent()).stream()
				.collect(Collectors.toMap(e -> e, DictionaryService::calculatePoints));
		return map;
	}

	public List<BoardHistory> getBoardHistory(Long id, int seq) {
		return boardHistoryRepository.findByBoardAndSequence(getBoard(id), seq);

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

	public List<String> getWords(final String[][] content) {

		return getEverything(content).stream().filter(DictionaryService::isAWord)
				.collect(Collectors.toCollection(ArrayList::new));

	}

	public String play(Long boardId, BoardLetter... args) {
		int totalPoints = 0;
		Board b = getBoard(boardId);
		String message = "OK";
		if (!Board.Status.ACTIVE.equals(b.getStatus())) {
			message = "You can play on ACTIVE Boards only  !!";
		} else {

			final String[][] existingContent = b.getContent();

			final List<String> exisitngThings = getEverything(existingContent);

			final List<String> existingWords = getWords(existingContent);

			if (b.isEmpty() || isThereAnyLetterNearBy(existingContent, args)) {

				if (isAnyLetterNotOverlapping(existingContent, args)) {
					final String[][] newContent = putLetterstoContent(existingContent, args);

					List<String> newWords = getWords(newContent);
					newWords = new ArrayList<>(CollectionUtils.subtract(newWords, existingWords));

					List<String> newThings = getEverything(newContent);
					newThings = new ArrayList<>(CollectionUtils.subtract(newThings, exisitngThings));

					List<String> nonWords = newThings.stream().filter(s -> s.length() > 1)
							.collect(Collectors.toCollection(ArrayList::new));

					nonWords = new ArrayList<>(CollectionUtils.subtract(nonWords, newWords));

					if (!nonWords.isEmpty()) {
						message = "Words not found in Dictionary :  " + nonWords.toString();
					} else {
						if (newWords.isEmpty()) {
							message = "Words not found in Dictionary :  " + newThings.toString();

						} else {

							totalPoints = newWords.stream().mapToInt(DictionaryService::calculatePoints).sum();

							if (b.isEmpty()) {
								b.setEmpty(false);
							}
							b.setContent(newContent);
							b.incrementSequence();
							this.updateBoard(b);

							message = "Congrats ! you got " + totalPoints + " points !";
						}
					}

				} else {
					message = "You should find empty places to put your Letters !!";
				}
			} else {
				message = "You should start from next to another word !!";
			}
		}
		return message;
	}

	private boolean isThereAnyLetterNearBy(final String[][] content, BoardLetter... args) {

		boolean isThereAny = false;
		StringBuilder nearbyLetters = new StringBuilder();

		for (BoardLetter L : args) {

			if (L.getY() > 0)
				nearbyLetters.append(content[L.getX()][L.getY() - 1]);
			if (L.getY() < content.length)
				nearbyLetters.append(content[L.getX()][L.getY() + 1]);
			if (L.getX() > 0)
				nearbyLetters.append(content[L.getX() - 1][L.getY()]);
			if (L.getX() < content.length)
				nearbyLetters.append(content[L.getX() + 1][L.getY()]);

		}
 
		isThereAny = !nearbyLetters.toString().replaceAll("null", "").isEmpty();

		return isThereAny;
	}

	private boolean isAnyLetterNotOverlapping(final String[][] content, BoardLetter... args) {

		boolean isNotOverLapping = true;
		for (BoardLetter L : args) {
			if (content[L.getX()][L.getY()] != null)
				isNotOverLapping = false;
			break;

		}
		return isNotOverLapping;
	}

	List<String> getEverything(final String[][] content) {

		List<String> words = new ArrayList<>();
		String terminator = "null";
		StringBuilder col = new StringBuilder(250);

		IntStream.range(0, content.length).forEach(x -> {
			final String row = Arrays.toString(content[x]).replaceAll("[\\W]|_", "").concat(terminator)
					.replaceAll(terminator, ",");

			words.addAll(Arrays.asList(row.split(",")));

		});

		IntStream.range(0, content[0].length).forEach(x -> {
			IntStream.range(0, content.length).forEach(y -> {
				col.append(content[y][x]);
			});
			col.append(terminator);

		});
		String colStr = col.toString();

		words.addAll(Arrays.asList(colStr.split(terminator)));

		return words.stream().filter(s -> s.length() > 0).collect(Collectors.toCollection(ArrayList::new));

	}

	private String[][] putLetterstoContent(final String[][] content, BoardLetter... args) {
		String[][] newcontent = Arrays.stream(content).map(String[]::clone).toArray(String[][]::new);
		for (BoardLetter L : args) {
			newcontent[L.getX()][L.getY()] = String.valueOf(L.getLetter()).toUpperCase();
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
