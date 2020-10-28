package View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Character.Character;
import Character.Human;
import Character.Santa;
import Character.SnowMan;
import Model.CharacterPicker;
import Model.GameLabel;
import Model.GamePage;
import Model.GameText;
import Model.MenuButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMenu {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 720;
	public static final int PAGE_HEIGHT = 500;
	public static final int OFFSET = WIDTH;
	private final int GAME_LABEL_HEIGHT = HEIGHT - PAGE_HEIGHT;
	
	public static final String FONT_PATH = "/Fleftex_M.ttf";
	
	private final String GAME_NAME = "A 4 Chirstmas Present";
	
	private final String BG_IMG_PATH = ClassLoader.getSystemResource("bgImage.jpg").toString();
	
	private final String BG_MUSIC_PATH = ClassLoader.getSystemResource("bgMusic.mp3").toString();
	private final String CLICK_SOUND_PATH = ClassLoader.getSystemResource("clickSound.mp3").toString();
	
	private HashMap<Integer, Character> allCharacter;
	private List<CharacterPicker> characterList;
	private Character chosenCharacter;
	
	private boolean backgroundMusicEnable = true,soundFXEnable = true;
	private MediaPlayer backgroundMusic;
	
	private GamePage mainMenu,optionMenu,instructionMenu,characterSelectMenu,exitMenu;
	private HBox gameLabelBox;
	private Pane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	public GameMenu(){
		mainPane = new Pane();
		mainScene = new Scene(mainPane,WIDTH,HEIGHT);
		mainStage = new Stage();
		mainStage.setTitle(GAME_NAME);
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		
		addCharacter();
		createBackground();
		createBackgroundMusic();
		createMenu();
		createGameLabel();
		
	}
	
	private void addCharacter() {
		allCharacter = new HashMap<Integer, Character>();
		allCharacter.put(1, new SnowMan());
		allCharacter.put(2, new Human());
		allCharacter.put(3, new Santa());
	}
	
	private void createGameLabel() {
		gameLabelBox = new HBox();
		gameLabelBox.setAlignment(Pos.CENTER);
		gameLabelBox.setPadding(new Insets(50));
		gameLabelBox.setPrefSize(WIDTH, GAME_LABEL_HEIGHT);
		gameLabelBox.setTranslateX(0);
		gameLabelBox.setTranslateY(0);
		
		Label gameLabel = new GameLabel(GAME_NAME,48);
		gameLabel.setAlignment(Pos.CENTER);
		gameLabel.setStyle("-fx-background-color: rgba(192,192,192,0.5)");
		gameLabel.setPrefSize(WIDTH-2*50, GAME_LABEL_HEIGHT-2*50);
		gameLabelBox.getChildren().add(gameLabel);
		
		mainPane.getChildren().add(gameLabelBox);
	}
	
	private void createMenu() {
		createMainMenu();
		createCharacterSelectMenu();
		createOptionMenu();
		createinstructionMenu();
		createExitMenu();
		
	}
	
	private void createExitMenu() {
		exitMenu = new GamePage(false, true);
		
		MenuButton yesButton = new MenuButton("Yes");
		yesButton.setOnMouseClicked(event->{
			playClickSound();
			mainStage.close();
		});
		
		MenuButton noButton = new MenuButton("No");
		noButton.setOnMouseClicked(event->{
			playClickSound();
			exitMenu.moveOut();
			mainMenu.moveIn();
		});
		
		//SideBox
		Label exitLabel = new GameLabel("Are You Sure?",35);
		
		exitMenu.addSideBoxContent(exitLabel);
		exitMenu.addButton(yesButton);
		exitMenu.addButton(noButton);
		
		mainPane.getChildren().add(exitMenu);
	}
	
	private void createCharacterSelectMenu() {
		// TODO Auto-generated method stub
		characterSelectMenu = new GamePage(false,true);
		
		MenuButton backButton = new MenuButton("Back");
		backButton.setOnMouseClicked(event->{
			playClickSound();
			characterSelectMenu.moveOut();
			mainMenu.moveIn();
		});
		
		MenuButton startButton = new MenuButton("Start");
		startButton.setOnMouseClicked(event->{
			playClickSound();
			GameMenu gameMenu = this;
			GameView gameView = new GameView();
			gameView.createNewGame(mainStage,gameMenu,chosenCharacter);
			characterSelectMenu.moveOut();
			mainMenu.moveIn();
			
		});
		
		
		//SideBox
		Label characterSelectLabel = new GameLabel("Select Character",35);
		characterSelectLabel.setPrefHeight(35);
		
		
		characterList = new ArrayList<CharacterPicker>();
		chosenCharacter = new SnowMan();
		HBox characterCatalog = new HBox(10);
		for (int num : allCharacter.keySet()) {
			CharacterPicker characterPicker = new CharacterPicker(num, allCharacter.get(num));
			
			characterList.add(characterPicker);
			characterList.get(0).setIsSelected(true);
			
			characterPicker.setOnMouseClicked(event ->{
				playClickSound();
				for (CharacterPicker character : characterList) {
					character.setIsSelected(false);
				}
				
				characterPicker.setIsSelected(true);
				chosenCharacter = characterPicker.getCharacter();
			});
			characterCatalog.getChildren().add(characterPicker);
		}
		
		
		
		characterSelectMenu.addSideBoxContent(characterSelectLabel);
		characterSelectMenu.addSideBoxContent(characterCatalog);
		characterSelectMenu.addButton(backButton);
		characterSelectMenu.addButton(startButton);
		mainPane.getChildren().add(characterSelectMenu);
	}

	private void createinstructionMenu() {
		// TODO Auto-generated method stub
		instructionMenu = new GamePage(false,true);
		instructionMenu.setSideBoxVisible(true);
		MenuButton backButton = new MenuButton("Back");
		backButton.setOnMouseClicked(event ->{
			playClickSound();
			instructionMenu.moveOut();
			mainMenu.moveIn();
		});
	
		Label instructionLabel = new GameLabel("How to Play",35);
	
		
		GameText instruction = new GameText("",24);
		instruction.setText("\n\n- Jump as high as you can -\n\n- Don't fall off the screen -\n\n- Good Luck (You need it) -");
		
		
		instructionMenu.addSideBoxContent(instructionLabel);
		instructionMenu.addSideBoxContent(instruction);
		
		instructionMenu.addButton(backButton);
		mainPane.getChildren().add(instructionMenu);
	}

	private void createOptionMenu() {
		// TODO Auto-generated method stub
		optionMenu = new GamePage(false,false);
		MenuButton backButton = new MenuButton("Back");
		backButton.setOnMouseClicked(event ->{
			playClickSound();
			optionMenu.moveOut();
			mainMenu.moveIn();
		});
		
		MenuButton musicButton = new MenuButton("Music On");
		musicButton.setOnMouseClicked(event ->{
			playClickSound();
			if (backgroundMusicEnable) musicButton.setText("Music Off");
			else musicButton.setText("Music On");
			toggleBackgroundMusic();
		});
	
		MenuButton soundFXButton = new MenuButton("Sound FX On");
		soundFXButton.setOnMouseClicked(event ->{
			playClickSound();
			if (soundFXEnable) soundFXButton.setText("Sound FX Off");
			else soundFXButton.setText("Sound FX On");
			toggleSoundFX();
		});
		
		optionMenu.addButton(backButton);
		optionMenu.addButton(musicButton);
		optionMenu.addButton(soundFXButton);
		mainPane.getChildren().add(optionMenu);
		
	}

	private void createMainMenu() {

		// TODO Auto-generated method stub
		mainMenu = new GamePage(true,false);
		MenuButton startButton = new MenuButton("Play");
		startButton.setOnMouseClicked(event ->{
			playClickSound();
			mainMenu.moveOut();
			characterSelectMenu.moveIn();
		});
		
		MenuButton optionButton = new MenuButton("Option");
		optionButton.setOnMouseClicked(event -> {
			playClickSound();
			mainMenu.moveOut();
			optionMenu.moveIn();
		});
		
		MenuButton instructionButton = new MenuButton("Instruction");
		instructionButton.setOnMouseClicked(event ->{
			playClickSound();
			mainMenu.moveOut();
			instructionMenu.moveIn();
		});
		
		MenuButton exitButton = new MenuButton("Exit");
		exitButton.setOnMouseClicked(event ->{
			playClickSound();
			mainMenu.moveOut();
			exitMenu.moveIn();
		});
		
		mainMenu.addButton(startButton);
		mainMenu.addButton(optionButton);
		mainMenu.addButton(instructionButton);
		mainMenu.addButton(exitButton);
		
		mainPane.getChildren().add(mainMenu);
		
	}
	
	private void createBackground(){
		Image bgImage = new Image(BG_IMG_PATH);
		ImageView imgView = new ImageView(bgImage);
		imgView.setFitWidth(WIDTH);
		imgView.setFitHeight(HEIGHT);
		mainPane.getChildren().add(imgView);
		
	}
	
	private void createBackgroundMusic() {
		backgroundMusic = new MediaPlayer(new Media(BG_MUSIC_PATH));
		backgroundMusic.setVolume(0.3);
		backgroundMusic.setOnEndOfMedia(new Runnable() {
			public void run() {
				backgroundMusic.seek(Duration.ZERO);
			}
		});
		backgroundMusic.setAutoPlay(true);
	}
	
	private void playClickSound() {
		if (soundFXEnable) {
			AudioClip clickSound = new AudioClip(CLICK_SOUND_PATH);
			clickSound.setVolume(0.5);
			clickSound.play();
		}		
	}
	
	
	public void setMusicEnable(boolean set) {
		backgroundMusicEnable = set;
		if (backgroundMusicEnable) backgroundMusic.play();
		else backgroundMusic.pause();
	}
	public boolean getSoundFXEnable() {
		return soundFXEnable;
	}
	public void toggleBackgroundMusic() {
		if (backgroundMusicEnable) {
			backgroundMusicEnable = false;
			backgroundMusic.pause();
		}
		else {
			backgroundMusicEnable = true;
			backgroundMusic.play();
		}
	}
	public void toggleSoundFX() {
		if (soundFXEnable) {
			soundFXEnable = false;
		}
		else {
			soundFXEnable = true;
		}
	}
	public Stage getMainStage() {
		return mainStage;
	}
	
	
	
}