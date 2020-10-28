package Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import Character.Character;

public class CharacterPicker extends VBox {
//	private ImageView tickBox;
	public static final int CHARACTER_PICKER_WIDTH = (GamePage.SIDE_BOX_WIDTH - 2*15 - 2*10)/3;
	public static final int CHARACTER_PICKER_HEIGHT = GamePage.BOX_HEIGHT - 2*15;
	private int characterNumber;
	private ImageView characterImage;
	private GameLabel nameLabel;
	private GameText characterInfo;
	private Character character;
	private boolean isSelected;
	
	public CharacterPicker(int num,Character character){
		
		
		setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignment(Pos.TOP_CENTER);
		setSpacing(20);
		setPrefSize( CHARACTER_PICKER_WIDTH , CHARACTER_PICKER_HEIGHT);
		unhighlight();
		

		this.character = character;
		characterNumber = num;
		
		nameLabel = new GameLabel(character.getName(),28);
		nameLabel.setPrefHeight(56);
		
		characterImage = new ImageView(new Image(character.getStillImgPath()));
		characterImage.setFitHeight(168);
		characterImage.setFitWidth(CHARACTER_PICKER_WIDTH - 2*10);
		
		characterInfo = new GameText(character.getInfo(),16);
	
		
		
		getChildren().addAll(nameLabel, characterImage,characterInfo);
	}
	
	public int getCharacterNumber() {
		return characterNumber;
	}
	
	public boolean getIsSelected() {
		return isSelected;
	}
	
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (this.isSelected) highlight();
		else unhighlight();
	
	}
	
	public Character getCharacter() {
		return character;
	}
		
	public void highlight() {
		setBorder(new Border(new BorderStroke(Color.LIGHTYELLOW, BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	public void unhighlight() {
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	
	
	
	
	

	
}
