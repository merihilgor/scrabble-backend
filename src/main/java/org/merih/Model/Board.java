/**
 * 
 */
package org.merih.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Merih
 *
 */

@Entity
public class Board {

	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "The database generated Board ID")
	private Long id;

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
		EMPTY, ACTIVE, PASSIVE;
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
		content[0][0] = "T";
		content[0][1] = "A";
		content[0][2] = "M";

		content[0][4] = "Y";
		content[0][5] = "E";
		content[0][6] = "M";

		content[4][0] = "G";
		content[4][1] = "E";
		content[4][2] = "M";

		content[4][4] = "Z";
		content[5][4] = "A";
		content[6][4] = "M";

		content[8][4] = "M";
		content[9][4] = "A";
		content[10][4] = "T";

		this.status = Status.EMPTY;
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
 
}
