package Character;

public class Santa extends Character {
	private static final String STILL_IMG_PATH = ClassLoader.getSystemResource("SantaStill.gif").toString();
	private static final String MOVE_RIGHT_IMG_PATH = ClassLoader.getSystemResource("SantaMove.gif").toString();
	private static final String MOVE_LEFT_IMG_PATH = ClassLoader.getSystemResource("SantaMove.gif").toString();
	private static final String JUMP_IMG_PATH = ClassLoader.getSystemResource("SantaJump.gif").toString();
	private static final String NAME = "Santa";
	private static final String INFO = "Ho Ho";
	
	public Santa() {
		super(NAME, STILL_IMG_PATH, MOVE_RIGHT_IMG_PATH,MOVE_LEFT_IMG_PATH,JUMP_IMG_PATH,INFO);
		// TODO Auto-generated constructor stub
	}

}
