package Snake;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ContrSnake {
	@FXML
	GridPane snakeGrid;
	LinkedList<Point> queueue = new LinkedList<Point>(Arrays.asList(new Point(3,10)));
	Timeline timeline = new Timeline();
	Point oldChange = new Point(1,0);
	Point foodP;
	Random r = new Random();
	boolean fed = false, foodSpawned = false;

	@FXML
	public void initialize() {
		// snakeGrid.setBackground(new Background(new
		// BackgroundFill(Paint.valueOf("black"), CornerRadii.EMPTY, Insets.EMPTY)));
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Rectangle r = new Rectangle(30, 30);
				r.setId("field" + j + i);
				r.setFill(Paint.valueOf("black"));
				snakeGrid.add(r, j, i);
			}
		}
		timeline = new Timeline(new KeyFrame(Duration.millis(1000/20), e -> {
			move();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	public void restartGame() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				((Rectangle) snakeGrid.lookup("#field" + j + i)).setFill(Paint.valueOf("black"));
				Main.change.setLocation(new Point(1,0));
				oldChange.setLocation(new Point(1,0));
				fed = false;
				foodSpawned = false;
				queueue = new LinkedList<Point>(Arrays.asList(new Point(3,10)));
				timeline.play();
			}
		}
	}

	private void move() {
		// makes food
		if(!foodSpawned) {
			spawnFood();
			foodSpawned = true;
		}
		
		Point curPoint = queueue.getLast();
		Point newP;
		//checks so that it doesnt go straight back
		if(Main.change.x * -1 == oldChange.x && Main.change.y * -1 == oldChange.y) {
			newP = new Point(curPoint.x + oldChange.x, curPoint.y + oldChange.y);
		} else {
			newP = new Point(curPoint.x + Main.change.x, curPoint.y + Main.change.y);
			oldChange.setLocation(Main.change);
		}
		if(newP.equals(foodP)) {
			fed = true;
			foodSpawned = false;
		} else if(queueue.contains(newP)) {
			System.out.println("THE SNAKE BIT ITS OWN TAIL");
			timeline.stop();
		}
		// if its not going out of field do changes
		if (newP.x >= 0 && newP.x < 20 && newP.y >= 0 && newP.y < 20) {
			((Rectangle) snakeGrid.lookup("#field" + newP.x + newP.y)).setFill(Paint.valueOf("white"));
			queueue.add(newP);
			if(!fed) {
				Point lastP = queueue.removeFirst();
				((Rectangle) snakeGrid.lookup("#field" + lastP.x + lastP.y)).setFill(Paint.valueOf("black"));
			} else {
				fed = false;
			}
		} else {
			timeline.stop();
			System.out.println("YOU SUCK");
		}
	}
	
	private void spawnFood() {
		do {
			foodP = new Point(r.nextInt(20), r.nextInt(20));
		} while (queueue.contains(foodP));
		((Rectangle) snakeGrid.lookup("#field" + foodP.x + foodP.y)).setFill(Paint.valueOf("green"));
	}
}
