/**
 * 
 */
package ApplicationLogic;

import java.util.ArrayList;

/**
 * @author
 *
 */
public class Player {

	private String playerName;
	private boolean human;
	private int playerColor;
	private boolean host;
	private int startPosition;
	private int endPosition;
	private int lastDiceValue;
	private String IP;
	
	public int getLastDiceValue() {
		return lastDiceValue;
	}

	public void setLastDiceValue(int lastDiceValue) {
		this.lastDiceValue = lastDiceValue;
	}

	private ArrayList<Chip> chips = new ArrayList<Chip>();
	
	/**
	 * 
	 */
	public Player() {
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the human
	 */
	public boolean isHuman() {
		return human;
	}

	/**
	 * @param human the human to set
	 */
	public void setHuman(boolean human) {
		this.human = human;
	}

	/**
	 * @return the playerColor
	 */
	public int getPlayerColor() {
		return playerColor;
	}

	/**
	 * @param playerColor the playerColor to set
	 */
	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}

	/**
	 * @return the host
	 */
	public boolean isHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(boolean host) {
		this.host = host;
	}

	/**
	 * @return the startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * @param startPosition the startPosition to set
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * @return the endPosition
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * @param endPosition the endPosition to set
	 */
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public ArrayList<Chip> getChips() {
		return chips;
	}
}
