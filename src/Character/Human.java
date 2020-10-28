package Character;

public class Human extends Character {
	private static final String STILL_IMG_PATH = ClassLoader.getSystemResource("HumanStill.gif").toString();
	private static final String MOVE_RIGHT_IMG_PATH = ClassLoader.getSystemResource("HumanMoveRight.gif").toString();
	private static final String MOVE_LEFT_IMG_PATH = ClassLoader.getSystemResource("HumanMoveLeft.gif").toString();
	private static final String JUMP_IMG_PATH = ClassLoader.getSystemResource("HumanJump.gif").toString();
	private static final String NAME = "Good Boy";
	private static final String INFO = "He wants A";
	
	public Human() {
		super(NAME, STILL_IMG_PATH, MOVE_RIGHT_IMG_PATH,MOVE_LEFT_IMG_PATH,JUMP_IMG_PATH,INFO);
		// TODO Auto-generated constructor stub
	}

}
