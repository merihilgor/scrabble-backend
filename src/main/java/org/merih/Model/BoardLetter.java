/**
 * 
 */
package org.merih.Model;

/**
 * @author Merih
 *
 */
public class BoardLetter {
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the l
	 */
	public char getLetter() {
		return Letter;
	}
	/**
	 * @param l the l to set
	 */
	public void setLetter(char l) {
		Letter = l;
	}
	private int x;
	private int y;
	private char Letter;

}
