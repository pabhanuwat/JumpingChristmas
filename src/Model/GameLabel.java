package Model;

import View.GameMenu;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GameLabel extends Label {
	private static final String FONT_PATH = GameMenu.FONT_PATH;

	public GameLabel(String text, int size){
		super(text);
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), size));
	}
}
