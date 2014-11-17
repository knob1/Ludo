/**
 * 
 */
package ApplicationLogic;

/**
 * @author
 *
 */
public class GameArea {

	private static GameArea instance= null;
	
	private Chip[] field = new Chip[52]; // -1 is reserved for all inactive chips; the playground
	private Chip[] redEnd = new Chip[5]; // chip is in home, when size + 1 (= 6)
	private Chip[] blueEnd = new Chip[5];
	private Chip[] yellowEnd = new Chip[5];
	private Chip[] greenEnd = new Chip[5];
	
	private GameArea() {
		
	}
	
	public static GameArea getInstance(){
		if(instance == null)
			return (instance= new GameArea());
		
		return instance;
	}
	
	public void prepareForNewGame(){
		field = new Chip[52]; 
		redEnd = new Chip[5]; 
		blueEnd = new Chip[5];
		yellowEnd = new Chip[5];
		greenEnd = new Chip[5];
	}
	
	public Chip[] getField() {
		return field;
	}

	public Chip[] getRedEnd() {
		return redEnd;
	}

	public Chip[] getBlueEnd() {
		return blueEnd;
	}

	public Chip[] getYellowEnd() {
		return yellowEnd;
	}

	public Chip[] getGreenEnd() {
		return greenEnd;
	}

	public int fieldSize() {
		return field.length;
	}
	
	public void reset(){
		field = new Chip[52]; 
		redEnd = new Chip[5]; 
		blueEnd = new Chip[5];
		yellowEnd = new Chip[5];
		greenEnd = new Chip[5];
	}
	
}
