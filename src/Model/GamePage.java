package Model;



import View.GameMenu;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GamePage extends HBox {
	public static final int BUTTON_BOX_WIDTH = 250;
	public static final int SIDE_BOX_WIDTH = GameMenu.WIDTH- BUTTON_BOX_WIDTH -25 -2*25;
	public static final int PAGE_HEIGHT = GameMenu.PAGE_HEIGHT;
	public static final int BOX_HEIGHT = PAGE_HEIGHT -25 -100;
	public static final int PAGE_WIDTH = GameMenu.WIDTH;
	
	private final int OFFSET = GameMenu.OFFSET;
	private VBox buttonBox;
	private VBox sideBox;
	private boolean isMain;
	
	
	public GamePage(boolean isMain,boolean sideBoxVisible){
		this.isMain = isMain;
		setPadding(new Insets(25,25,75,25));
		setSpacing(25);
		setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
		setAlignment(Pos.TOP_CENTER);
		initializeBox();
		sideBox.setVisible(sideBoxVisible);
		setPageLocation();
		
		
		
	}
	
	private void setPageLocation() {
		if (isMain) setTranslateX(0);
		else setTranslateX(OFFSET);
		setTranslateY(GameMenu.HEIGHT-PAGE_HEIGHT);
	}
	
	private void initializeBox() {
		initializeButtonBox();
		initializeSideBox();
	}
	
	private void initializeButtonBox() {
		buttonBox = new VBox(10);
		buttonBox.setAlignment(Pos.TOP_RIGHT);
		buttonBox.setPrefSize(BUTTON_BOX_WIDTH,BOX_HEIGHT);
		getChildren().add(buttonBox);
	}
	
	private void initializeSideBox() {
		sideBox = new VBox(10);
		sideBox.setPadding(new Insets(15));
		sideBox.setAlignment(Pos.TOP_CENTER);
		sideBox.setPrefSize(SIDE_BOX_WIDTH,BOX_HEIGHT);
		sideBox.setStyle("-fx-background-color: rgba(192,192,192,0.5)");
		sideBox.setVisible(false);
		getChildren().add(sideBox);
	}
	
	
	public void addButton(MenuButton button) {
		buttonBox.getChildren().add(button);
	}
	
	public void addSideBoxContent(Node node) {
		sideBox.getChildren().add(node);
	}
	
	public void setSideBoxVisible(boolean set) {
		sideBox.setVisible(set);
	}
	public void moveIn() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.75),this);
		tt.setToX(0);
		tt.play();
	}
	public void moveOut() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.75),this);
		tt.setToX(isMain ? -OFFSET : OFFSET);
		tt.play();
	}

}
