/**
 * 
 */
package org.merih.Model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Merih
 *
 */

@Entity
@EntityListeners(BoardEntityListener.class)
@SequenceGenerator(name = "seqboard", initialValue = 1, allocationSize = 100)
public class Board extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqboard")
	@ApiModelProperty(notes = "The database generated Board ID")
	private Long id;

	private int sequence = 0;

	private boolean isEmpty;

	@Lob
	private String[][] content;

	/**
	 * @return the content
	 */
	public String[][] getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String[][] content) {
		this.content = content;
	}

	public enum Status {
		ACTIVE, PASSIVE;
	}

	@Lob
	private Status status;

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 
	 */
	public Board() {
		super();
		this.content = new String[15][15];
		this.isEmpty = true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	public void incrementSequence() {
		this.sequence = this.sequence + 1;
	}

	/**
	 * @return the isEmpty
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * @param isEmpty
	 *            the isEmpty to set
	 */
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

}
