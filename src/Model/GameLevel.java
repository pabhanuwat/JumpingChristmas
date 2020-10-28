package Model;

import java.util.ArrayList;

import View.GameView;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameLevel extends Pane {

	private ArrayList<Node> ObstacleList;
	private static final int GAME_WIDTH = GameView.GAME_WIDTH;
	private static final int GAME_HEIGHT = GameView.GAME_HEIGHT;
	private static final int OBSTACLE_SIZE = GameView.OBSTACLE_SIZE;
	private static final int OBSTACLE_INTERVAL = 4;
	private static final String OBSTACLE_IMAGE_PATH = ClassLoader.getSystemResource("block.png").toString();
	private static final int TOTAL_COL = GAME_WIDTH / OBSTACLE_SIZE;
	private static final int TOTAL_ROW = GAME_HEIGHT / OBSTACLE_SIZE;
	private int maxObstacle;
	private int minObstacle;
	private int xOrigin, yOrigin;

	public GameLevel(int x, int y, int min, int max) {
		xOrigin = x;
		yOrigin = y;
		minObstacle = min;
		maxObstacle = max;
		ObstacleList = new ArrayList<>();
		setTranslateX(xOrigin);
		setTranslateY(yOrigin);
		setPrefSize(GAME_HEIGHT, GAME_WIDTH);
		createLevel();

	}

	private void createLevel() {

		for (int row = 0; row < TOTAL_ROW; row++) {
			if (row % OBSTACLE_INTERVAL == 3) {
				if (yOrigin == 0 && row == 15) { // floor
					createFloor(row);
				} else {
					int obstacleCount = 0;
					for (int col = 0; col < TOTAL_COL; col++) {
						double random = (Math.floor(Math.random() * 10) + 1);
						if (random % 3 == 0 && obstacleCount <= maxObstacle) {
							ObstacleList.add(createObstacle(col, row));
							obstacleCount++;
						}
					}
					while (obstacleCount++ < minObstacle) {
						int col = (int) (Math.floor(Math.random() * 10) + 1);
						ObstacleList.add(createObstacle(col, row));
					}
				}
			}
			
			if (yOrigin == 0 && row == 14) {
				createFloor(row);
			}
		}
	}

	

	private void createFloor(int row) {
		for (int col = 0; col < TOTAL_COL; col++) {
			ObstacleList.add(createObstacle(col, row));
		}
	}

	private Node createObstacle(int col, int row) {
		ImageView obstacle = new ImageView(new Image(OBSTACLE_IMAGE_PATH));

		obstacle.setFitHeight(OBSTACLE_SIZE);
		obstacle.setFitWidth(OBSTACLE_SIZE);
		obstacle.setTranslateX(col * OBSTACLE_SIZE);
		obstacle.setTranslateY(row * OBSTACLE_SIZE);

		getChildren().add(obstacle);

		return obstacle;
	}

	public ArrayList<Node> getObstacleList() {
		return ObstacleList;
	}

	public void moveDown(double y) {
		setTranslateY(getTranslateY() + y);
	}
}
