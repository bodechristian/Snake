package Snake;

import java.awt.Point;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	public static Point change = new Point(1,0);
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SnakeField.fxml")), 880, 880);
		scene.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> changeDirection(event));
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		primaryStage.show();
	}
	
	public void changeDirection(KeyEvent event) {
		if(event.getCode() == KeyCode.LEFT) {
			change.setLocation(new Point(-1,0));
		} else 	if(event.getCode() == KeyCode.RIGHT) {
			change.setLocation(new Point(1,0));
		} else if(event.getCode() == KeyCode.UP) {
			change.setLocation(new Point(0,-1));
		} else if(event.getCode() == KeyCode.DOWN) {
			change.setLocation(new Point(0,1));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
