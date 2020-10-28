package Model;

import View.GameMenu;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameText extends Text {
	private static final String FONT_PATH = GameMenu.FONT_PATH;
	
	public GameText(String text,int size) {
		super(text);
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), size));
	}
}
