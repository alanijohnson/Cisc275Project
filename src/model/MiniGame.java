package model;

/**
 * @author Group 4
 * MiniGame model
 */
public class MiniGame extends MainModel{

	// amount of trash to start with
	private int startingTrash = 10;
	private int startingFood = 0;
	
	private int width;
	private int height;
	
	// methods
	
	
	// constructors
	
	
	// default
	/**
	 * Constructor
	 */
	public MiniGame() {
		super();
		map = new MiniMap();
		inMiniGame = true;
		everyThing = new MiniStuffSet();
		fishy = new MiniMainCharacter(map);
		accumulateAll();
	}
	
	
	// set amount of starting trash
	/**
	 * Constructor - sets amount of trash
	 * @param num 	amount of trash to start with
	 */
	public MiniGame(int num) {
		this();
		everyThing = new MiniStuffSet();
		setStartingTrash(num);
		accumulateAll();
	}
	
	/**
	 * @param width 	width of minigame	
	 * @param height	height of minigame
	 */
	public MiniGame(int width, int height){
		this.width = width;
		this.height = height;
		this.map = new MiniMap(width, height);
		this.fishy = new MiniMainCharacter(map);
		accumulateAll();
	}
	
	
	// trash and food accumulation
	
	
	// initial setting
	/** (non-Javadoc)
	 * Presets all the trash
	 */
	public void accumulateAll() {
		for (int i=0; i<getStartingTrash(); i++) {
			boolean trashAdded = false;
			int[] trashLoc = {0, 0};
			while (!trashAdded) {
				//generate random coordinates for stuff
				trashLoc[0] = MainModel.randint(75, getMap().getLength()-75);
				trashLoc[1] = MainModel.randint(75, getMap().getHeight()-75);
				trashAdded = getStuffSet().add(trashLoc,"trash");
				//System.out.println("trash"+trashLoc[0]+"/"+getMap().getLength());
			}
		}
	}
	
	
	// in game accumulation
	/** (non-Javadoc)
	 * @see model.MainModel#accumulate()
	 * This prevents the minigame from accumulating more trash
	 */
	@Override
	public void accumulate() {/* do nothing */}
	
	
	
	// printing
	/** (non-Javadoc)
	 * @see model.MainModel#toString()
	 * Prints out the minigame, with the tag mini game in front
	 */
	@Override
	public String toString() {
		String str = "Mini game: \n";
		str += super.toString();
		return str;
	}
	
	
	// updater
	/** (non-Javadoc)
	 * @see model.MainModel#update(int, int)
	 * Update the position of all objects
	 */
	@Override
	public void update(int newSpeed, int deltaTheta) {
		// setup
		getMainCharacter().setSpeed(newSpeed);
		getMainCharacter().setAngle(deltaTheta);
		
		// if move is allowed
		if (getMap().moveMap(getMainCharacter())) {
			// move everything
			System.out.println("Valid move");
			//getStuffSet().move(getMainCharacter());
			getMainCharacter().move();
		}
		
		// move not allowed
		else {
			System.out.println("Invalid move, not moving");
		}
		
		// display the state of the world
		System.out.println(this);
		
		// check collisions
		String collision = getStuffSet().whatCollided(getMainCharacter());
		System.out.println("Checking collisions");
		System.out.println("Collisions: " + collision);
		
		// collision detected
		if (collision.equals("trash")) {
			setStartingTrash(getStartingTrash() - 1);
		}
	}
	
	
	// getters
	
	
	// mini gameover
	/**
	 * Determines if the minigame is over
	 * @return 			whether the minigame is over
	 */
	public boolean getMiniGameOver() {
		return (getStartingTrash() == 0);
	}
	
	
	// trash initialization
	/**
	 * Trash initialization amount
	 * @return 		 initial amount of trash
	 */
	public int getStartingTrash() {
		return startingTrash;
	}
	
	
	// setters
	/**
	 * Set starting amount of trash
	 * @param num 		amount of starting trash
	 */
	public void setStartingTrash(int num) {
		startingTrash = num;
	}
	
	/** (non-Javadoc)
	 * @see model.MainModel#setStartingFood(int)
	 */
	public void setStartingFood(int num) {
		startingFood = num;
	}
}
