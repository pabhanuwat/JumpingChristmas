package Character;

public class SnowMan extends Character {
	private static final String STILL_IMG_PATH = ClassLoader.getSystemResource("SnowmanStill.gif").toString();
	private static final String MOVE_RIGHT_IMG_PATH = ClassLoader.getSystemResource("SnowmanMove.gif").toString();
	private static final String MOVE_LEFT_IMG_PATH = ClassLoader.getSystemResource("SnowmanMove.gif").toString();
	private static final String JUMP_IMG_PATH = ClassLoader.getSystemResource("SnowmanJump.gif").toString();
	private static final String NAME = "Snow Man";
	private static final String INFO = "He's made by Elsa";
	
	public SnowMan() {
		super(NAME, STILL_IMG_PATH, MOVE_RIGHT_IMG_PATH,MOVE_LEFT_IMG_PATH,JUMP_IMG_PATH,INFO);
		// TODO Auto-generated constructor stub
	}

}
