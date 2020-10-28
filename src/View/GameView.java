package View;

import Character.Character;
import Model.GameLabel;
import Model.GameLevel;
import Model.GameText;
import Model.MenuButton;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class GameView {
	public static final int GAME_WIDTH = 540;
	public static final int GAME_HEIGHT = 720;
	public static final int OBSTACLE_SIZE = 45;
	public static final int GRAVITY = 15;
	public static final double FINAL_LEVEL_VELOCITY = 4.5;

	private static final String GAME_BACKGROUND_IMAGE_PATH = ClassLoader.getSystemResource("gameBackgroundImage.png")
			.toString();

	private static final String JUMP_SOUND_PATH = ClassLoader.getSystemResource("jumpSound.mp3").toString();
	private static final String DEAD_SOUND_PATH = ClassLoader.getSystemResource("deadSound.mp3").toString();

	private boolean soundFXEnable;
	private AudioClip jumpSound, deadSound;

	private double levelVelocity;
	private int minObstacle, maxObstacle;
	private GameLevel currentLevel, nextLevel;

	private Character player;
	private boolean canJump, isTouchGround;
	private String currentImage;
	private boolean isAKeyPressed, isDKeyPressed, isWKeyPressed;
	private int playerYVelocity, playerSpeed;

	private GameText scoreBox;
	private int score;

	private AnimationTimer timer;

	private GameMenu gameMenu;
	private Pane gamePane;
	private Scene gameScene;
	private Stage gameStage, menuStage;

	public GameView() {

		gamePane = new Pane();
		gamePane.setBackground(new Background(new BackgroundFill(
				new ImagePattern(new Image(GAME_BACKGROUND_IMAGE_PATH)), CornerRadii.EMPTY, Insets.EMPTY)));
		gamePane.setPrefSize(540, 720);
		gameScene = new Scene(gamePane);
		gameStage = new Stage();
		gameStage.setTitle("A 4 Chirstmas Present");
		gameStage.setMaxWidth(540);
		gameStage.setMaxHeight(720);
		gameStage.setScene(gameScene);
		gameStage.setResizable(false);

		createKeyListeners();
	}

	private void createKeyListeners() {

		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					isAKeyPressed = true;
				} else if (event.getCode() == KeyCode.D) {
					isDKeyPressed = true;
				} else if (event.getCode() == KeyCode.W) {
					isWKeyPressed = true;
				}
			}
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					isAKeyPressed = false;
				} else if (event.getCode() == KeyCode.D) {
					isDKeyPressed = false;
				} else if (event.getCode() == KeyCode.W) {
					isWKeyPressed = false;
					canJump = true;
				}
			}
		});
	}

	public void createNewGame(Stage menuStage, GameMenu gameMenu, Character character) {
		this.menuStage = menuStage;
		this.gameMenu = gameMenu;
		this.menuStage.hide();
		soundFXEnable = gameMenu.getSoundFXEnable();
		createCharacter(character);
		createLevel();
		createScore();
		createGameLoop();

		gameStage.show();
	}

	private void createCharacter(Character character) {

		player = character;
		player.setTranslateX(GAME_WIDTH / 2);
		player.setTranslateY(GAME_HEIGHT - 2 * OBSTACLE_SIZE - player.getCharacterHeight());
		playerSpeed = 5;
		playerYVelocity = GRAVITY - 1;
		isTouchGround = canJump = true;
		currentImage = player.getStillImgPath();
		gamePane.getChildren().add(player);

	}

	private void createLevel() {
		minObstacle = 3;
		maxObstacle = 5;
		currentLevel = new GameLevel(0, 0, minObstacle, maxObstacle);
		nextLevel = new GameLevel(0, -GAME_HEIGHT, minObstacle, maxObstacle);
		levelVelocity = 1;
		gamePane.getChildren().addAll(currentLevel, nextLevel);
	}

	private void createScore() {
		score = 0;
		scoreBox = new GameText("Score : " + score, 14);
		scoreBox.setFill(Color.WHITE);
		scoreBox.setTranslateY(50);
		scoreBox.setTranslateX(400);
		scoreBox.setViewOrder(-100);
		gamePane.getChildren().add(0, scoreBox);

	}

	public void createGameLoop() {
		timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {

				makePlayerFall();
				moveCharacter();
				moveLevel();
				updateScore();
				if (isDeath()) {
					timer.stop();
					createGameOver();
				}
			}
		};
		timer.start();
	}

	private void makePlayerFall() {
		if (playerYVelocity < GRAVITY) {
			playerYVelocity += 1;
		}
		movePlayerY(playerYVelocity);
	}

	private void updateScore() {
		score++;
		scoreBox.setText("Score : " + score);
		if (score % 1000 == 0 && score != 0) {
			levelVelocity += 0.5;
			if (levelVelocity >= FINAL_LEVEL_VELOCITY)
				levelVelocity = FINAL_LEVEL_VELOCITY;
		}

		if (score % 3000 == 0 && score != 0) {
			maxObstacle -= (maxObstacle > minObstacle) ? 1 : 0;
		}

		if (score % 10000 == 0 && score != 0) {
			minObstacle = 2;
		}
	}

	private void moveLevel() {
		if (currentLevel.getTranslateY() >= GAME_HEIGHT) {
			gamePane.getChildren().remove(currentLevel);
			gamePane.getChildren().remove(nextLevel);

			currentLevel = nextLevel;
			GameLevel temp = new GameLevel(0, -GAME_HEIGHT, minObstacle, maxObstacle);
			nextLevel = temp;

			gamePane.getChildren().addAll(currentLevel, nextLevel);
		} else {
			currentLevel.moveDown(levelVelocity);
			nextLevel.moveDown(levelVelocity);
		}

	}

	private void moveCharacter() {
		if (isAKeyPressed && isDKeyPressed) {
			return;
		} else if (isAKeyPressed) {
			movePlayerX(false);
			if (!(currentImage.equals(player.getMoveLeftImgPath())) && isTouchGround) {
				player.setImage(player.getMoveLeftImgPath());
				currentImage = player.getMoveLeftImgPath();
			}
		} else if (isDKeyPressed) {
			movePlayerX(true);
			if (!(currentImage.equals(player.getMoveRightImgPath())) && isTouchGround) {
				player.setImage(player.getMoveRightImgPath());
				currentImage = player.getMoveRightImgPath();
			}
		} else {
			if (!(currentImage.equals(player.getStillImgPath())) && isTouchGround) {
				player.setImage(player.getStillImgPath());
				currentImage = player.getStillImgPath();
			}
		}

		if (isWKeyPressed && canJump && isTouchGround) {
			playJumpSound();
			if (!(currentImage.equals(player.getJumpImgPath()))) {
				player.setImage(player.getJumpImgPath());
				currentImage = player.getJumpImgPath();
			}
			playerYVelocity = -21 + (int) (levelVelocity - 1);
			canJump = false;
			isTouchGround = false;

		}

	}

	private boolean isDeath() {
		return player.getTranslateY() >= GAME_HEIGHT;
	}

	private void createGameOver() {
		playDeadSound();
		VBox gameOverBox = new VBox(10);
		gameOverBox.setPadding(new Insets(10));
		gameOverBox.setAlignment(Pos.TOP_CENTER);
		gameOverBox.setStyle("-fx-background-color: rgba(192,192,192,0.5)");
		GameLabel gameOverLabel = new GameLabel("Game Over", 20);

		GameText finalScore = new GameText("Score : " + score, 16);

		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		MenuButton restartButton = new MenuButton("Restart");
		restartButton.setOnMouseClicked(event -> {
			clearScreen();
			createNewGame(menuStage, gameMenu, player);
			gamePane.getChildren().remove(gameOverBox);
		});

		MenuButton mainMenuButton = new MenuButton("Main Menu");

		mainMenuButton.setOnMouseClicked(event -> {
			gameStage.close();
			timer.stop();
			menuStage.show();
		});

		buttons.getChildren().addAll(restartButton, mainMenuButton);
		gameOverBox.getChildren().addAll(gameOverLabel, finalScore, buttons);
		gameOverBox.setTranslateX(GAME_WIDTH / 2 - 220);
		gameOverBox.setTranslateY(GAME_HEIGHT / 2 - 100);

		gamePane.getChildren().addAll(gameOverBox);

	}

	public void clearScreen() {
		gamePane.getChildren().remove(player);
		gamePane.getChildren().remove(currentLevel);
		gamePane.getChildren().remove(nextLevel);
		gamePane.getChildren().remove(scoreBox);
	}

	private void movePlayerY(int x) {
		int num = Math.abs(x);

		while (num-- > 0) {
			double pX = player.getTranslateX();
			double pY = player.getTranslateY();
			boolean movingUp = x < 0;

			for (Node node : currentLevel.getObstacleList()) {
				double nX = node.getTranslateX();
				double nY = node.getTranslateY() + currentLevel.getTranslateY();
				if (pX > nX + OBSTACLE_SIZE || pX + player.getCharacterWidth() < nX)
					continue;

				if ((pY >= nY && pY <= nY + OBSTACLE_SIZE) && movingUp) {
					if (!(nX + OBSTACLE_SIZE == pX) && !(pX + player.getCharacterWidth() == nX)) {
						playerYVelocity = 0;
						return;
					}
				}
				if ((pY + player.getCharacterHeight() >= nY && pY + player.getCharacterHeight() <= nY + OBSTACLE_SIZE)
						&& !movingUp) {
					if (!(nX + OBSTACLE_SIZE == pX) && !(pX + player.getCharacterWidth() == nX)) {
						isTouchGround = true;
						return;
					}
				}

			}

			for (Node node : nextLevel.getObstacleList()) {
				double nX = node.getTranslateX();
				double nY = node.getTranslateY() + nextLevel.getTranslateY();
				if (pX > nX + OBSTACLE_SIZE || pX + player.getCharacterWidth() < nX)
					continue;

				if ((pY >= nY && pY <= nY + OBSTACLE_SIZE) && movingUp) {
					if (!(nX + OBSTACLE_SIZE == pX) && !(pX + player.getCharacterWidth() == nX)) {
						playerYVelocity = 0;
						return;
					}
				}
				if ((pY + player.getCharacterHeight() >= nY && pY + player.getCharacterHeight() <= nY + OBSTACLE_SIZE)
						&& !movingUp) {
					if (!(nX + OBSTACLE_SIZE == pX) && !(pX + player.getCharacterWidth() == nX)) {
						isTouchGround = true;
						return;
					}
				}

			}

			isTouchGround = false;

			player.setTranslateY(pY + (movingUp ? -1 : 1));
		}
	}

	private void movePlayerX(boolean moveRight) {
		int num = playerSpeed;
		while (num-- > 0) {
			if (!checkSideCollision()) {
				player.moveX(moveRight);
			}
		}
	}

	private boolean checkSideCollision() {
		double pX = player.getTranslateX();
		double pY = player.getTranslateY();

		for (Node node : currentLevel.getObstacleList()) {
			if (isDKeyPressed && (pX + player.getCharacterWidth() == GAME_WIDTH))
				return true;
			if (isAKeyPressed && (pX == 0))
				return true;

			if (pY < node.getTranslateY() + OBSTACLE_SIZE + currentLevel.getTranslateY()
					&& pY + player.getCharacterHeight() > node.getTranslateY() + currentLevel.getTranslateY()) {

				if (isDKeyPressed && pX + player.getCharacterWidth() == node.getTranslateX())
					return true;
				if (isAKeyPressed && pX == node.getTranslateX() + OBSTACLE_SIZE)
					return true;

			}
		}
		for (Node node : nextLevel.getObstacleList()) {
			if (isDKeyPressed && (pX + player.getCharacterWidth() == GAME_WIDTH))
				return true;
			if (isAKeyPressed && (pX == 0))
				return true;

			if (pY < node.getTranslateY() + OBSTACLE_SIZE + nextLevel.getTranslateY()
					&& pY + player.getCharacterHeight() > node.getTranslateY() + nextLevel.getTranslateY()) {

				if (isDKeyPressed && pX + player.getCharacterWidth() == node.getTranslateX())
					return true;
				if (isAKeyPressed && pX == node.getTranslateX() + OBSTACLE_SIZE)
					return true;

			}
		}

		return false;
	}

	private void playJumpSound() {
		if (soundFXEnable) {
			jumpSound = new AudioClip(JUMP_SOUND_PATH);
			jumpSound.setVolume(0.2);
			jumpSound.play();
		}
	}

	private void playDeadSound() {
		if (soundFXEnable) {
			deadSound = new AudioClip(DEAD_SOUND_PATH);
			deadSound.setVolume(0.6);
			deadSound.play();
		}

	}

//	private void updateImage() {
//	if (isWKeyPressed) {
//		player.setImage(player.getJumpImgPath());
//	} else if (isAKeyPressed || isDKeyPressed) {
//		player.setImage(player.getMoveImgPath());
//	} else {
//		player.setImage(player.getStillImgPath());
//	}
//
//}
}
