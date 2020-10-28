package Application;

import View.GameMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	GameMenu gameMenu;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			gameMenu = new GameMenu();
			primaryStage = gameMenu.getMainStage();
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
