package Model;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MenuButton extends StackPane {
	private GameText text;
	private Rectangle bg;
	public MenuButton(String name) {
		
		//set button's font
		text = new GameText(name,20);
		text.setFill(Color.WHITE);
		//////////////////////////
		
		//set button's background
		bg = new Rectangle(200, 30);
		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		////////////////////////////
		
		setAlignment(Pos.CENTER_LEFT);
		getChildren().addAll(bg, text);

		setOnMouseEntered(event -> {
			bg.setTranslateX(10);
			text.setTranslateX(10);
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});
		setOnMouseExited(event -> {
			bg.setTranslateX(0);
			text.setTranslateX(0);
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		setOnMouseClicked(event -> {
			AudioClip clickSound = new AudioClip(ClassLoader.getSystemResource("clickSound.mp3").toString());
			clickSound.play();
		});
		
		setOnMousePressed(event ->	setEffect(drop));
		setOnMouseReleased(event -> setEffect(null));

	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
}
