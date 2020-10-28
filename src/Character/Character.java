package Character;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends ImageView implements Walkable, Jumpable {

	private static final int CHARACTER_WIDTH = 20;
	private static final int CHARACTER_HEIGHT = 50;
	private String name, info;
	private String stillImgPath,moveRightImgPath,moveLeftImgPath,jumpImgPath;

	public Character(String name, String stillImgPath,String moveRightImgPath,String moveLeftImgPath, String jumpImgPath,String info) {
		super(new Image(stillImgPath));
		setFitHeight(CHARACTER_HEIGHT);
		setFitWidth(CHARACTER_WIDTH);
		this.name = name;
		this.stillImgPath = stillImgPath;
		this.moveRightImgPath = moveRightImgPath;
		this.moveLeftImgPath = moveLeftImgPath;
		this.jumpImgPath = jumpImgPath;
		this.info = info;

		// TODO Auto-generated constructor stub
	}

	public int getCharacterHeight() {
		return CHARACTER_HEIGHT;
	}
	
	public int getCharacterWidth() {
		return CHARACTER_WIDTH;
	}


	
	@Override
	public void moveY(int y) {
		setTranslateY(getTranslateY() + y);
		
	}
	

	@Override
	public void moveX(boolean moveRight) {
		// TODO Auto-generated method stub
		setTranslateX(getTranslateX() + (moveRight ? 1 : -1));
	}
	
	public void setImage(String path) {
		setImage(new Image(path));
	}
	
	public String getMoveRightImgPath() {
		return moveRightImgPath;
	}
	
	public String getMoveLeftImgPath() {
		return moveLeftImgPath;
	}
	
	public String getStillImgPath() {
		return stillImgPath;
	}
	
	public String getJumpImgPath() {
		return jumpImgPath;
	}
	
	public String getName() {
		return name;
	}
	
	public String getInfo() {
		return info;
	}

}
