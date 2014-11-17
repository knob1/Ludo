/**
 * 
 */
package ApplicationLogic;

/**
 * @author 
 *
 */
public class Chip {

	private int position;
	private int stepCounter;
	private int color;
	private int status; // 0: inactive, 1: active, 2: stairs
	private int mergeCount = 0; // indicates how many chips are merged within
	
	public Chip(int color) {
		this.setColor(color);
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the stepCounter
	 */
	public int getStepCounter() {
		return stepCounter;
	}

	/**
	 * @param stepCounter the stepCounter to set
	 */
	public void setStepCounter(int stepCounter) {
		this.stepCounter = stepCounter;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the mergeCount
	 */
	public int getMergeCount() {
		return mergeCount;
	}

	/**
	 * @param mergeCount the mergeCount to set
	 */
	public void setMergeCount(int mergeCount) {
		this.mergeCount = mergeCount;
	}

	@Override
	public String toString() {
		return "Chip [position=" + position + ", stepCounter=" + stepCounter
				+ ", color=" + color + ", status=" + status + ", mergeCount="
				+ mergeCount + "]";
	}

}
