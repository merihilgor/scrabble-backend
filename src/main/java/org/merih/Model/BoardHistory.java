/**
 * 
 */
package org.merih.Model;

import javax.persistence.Lob;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author Merih
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "seqboardhistory", initialValue = 1, allocationSize = 100)
public class BoardHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqboardhistory")
	private Long historyId;

	private int sequence;

	@ManyToOne
	@JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "FK_board_history_board"))
	private Board board;

	@Lob
	private String[][] boardContent;

	@CreatedBy
	private String modifiedBy;
	
	@CreatedDate
	@Temporal(TIMESTAMP)
	private Date modifiedDate;
	
	@Enumerated(STRING)
	private Action action;

	public BoardHistory() {
	}

	public BoardHistory(Board board, Action action) {
		this.board = board;
		this.sequence = board.getSequence();
		this.boardContent = board.getContent();
		this.action = action;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return historyId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long historyId) {
		this.historyId = historyId;
	}

	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the board
	 */
	private Board getBoard() {
		return board;
	}

	/**
	 * @param board
	 *            the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the boardContent
	 */
	public String[][] getBoardContent() {
		return boardContent;
	}

	/**
	 * @param boardContent
	 *            the boardContent to set
	 */
	public void setBoardContent(String[][] boardContent) {
		this.boardContent = boardContent;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the boardId
	 */
	public Long getBoardId() {
		return this.board.getId();
	}

	/**
	 * @param boardId
	 *            the boardId to set
	 */
	public void setBoardId(Long boardId) {
		this.board.setId(boardId);
	}

}
