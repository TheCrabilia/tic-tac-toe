package net.abracadabralab;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.paint.Color;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  private static Group newGrid() {
    Group group = new Group();

    // Vertical lines
    for (int i = 0; i < 2; i++) {
      Line line = new Line(153 + (150 * i), 5, 153 + (150 * i), 455);
      line.setStrokeWidth(5);
      group.getChildren().add(line);
    }

    // Horizontal lines
    for (int i = 0; i < 2; i++) {
      Line line = new Line(5, 153 + (150 * i), 455, 153 + (150 * i));
      line.setStrokeWidth(5);
      group.getChildren().add(line);
    }

    return group;
  }

  private static Pair<Integer, Integer> resolveCell(double x, double y) {
    // First line
    if ((x >= 0 && x <= 150) && (y >= 0 && y <= 150)) {
      return new Pair<Integer, Integer>(0, 0);
    } else if ((x >= 155 && x <= 305) && (y >= 0 && y <= 150)) {
      return new Pair<Integer, Integer>(1, 0);
    } else if ((x >= 310 && x <= 460) && (y >= 0 && y <= 150)) {
      return new Pair<Integer, Integer>(2, 0);
      // Second line
    } else if ((x >= 0 && x <= 150) && (y >= 155 && y <= 305)) {
      return new Pair<Integer, Integer>(0, 1);
    } else if ((x >= 155 && x <= 305) && (y >= 155 && y <= 305)) {
      return new Pair<Integer, Integer>(1, 1);
    } else if ((x >= 310 && x <= 460) && (y >= 155 && y <= 305)) {
      return new Pair<Integer, Integer>(2, 1);
      // Third line
    } else if ((x >= 0 && x <= 150) && (y >= 310 && y <= 460)) {
      return new Pair<Integer, Integer>(0, 2);
    } else if ((x >= 155 && x <= 305) && (y >= 310 && y <= 460)) {
      return new Pair<Integer, Integer>(1, 2);
    } else if ((x >= 310 && x <= 460) && (y >= 310 && y <= 460)) {
      return new Pair<Integer, Integer>(2, 2);
    }
    // Something went wrong
    return null;
  }

  private static Node newXs(Pair<Integer, Integer> cell) {
    double centerX = cell.getKey() * 150 + 75;
    double centerY = cell.getValue() * 150 + 75;
    Line line1 = new Line(centerX - 50, centerY - 50, centerX + 50, centerY + 50);
    line1.setStroke(Color.BLUE);
    line1.setStrokeWidth(5);
    Line line2 = new Line(centerX - 50, centerY + 50, centerX + 50, centerY - 50);
    line2.setStroke(Color.BLUE);
    line2.setStrokeWidth(5);
    return new Group(line1, line2);
  }

  private static Node newOs(Pair<Integer, Integer> cell) {
    double centerX = cell.getKey() * 150 + 75;
    double centerY = cell.getValue() * 150 + 75;
    Circle circle = new Circle(centerX, centerY, 50);
    circle.setFill(null);
    circle.setStroke(Color.GREEN);
    circle.setStrokeWidth(5);
    return circle;
  }

  @Override
  public void start(Stage primaryStage) {
    Group grid = newGrid();
    VBox vbox = new VBox(grid);
    vbox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(vbox, 460, 460);

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        Pair<Integer, Integer> cell = resolveCell(e.getX(), e.getY());
        if (cell == null)
          return;

        switch (e.getButton()) {
          case PRIMARY:
            grid.getChildren().add(newOs(cell));
            break;
          case SECONDARY:
            grid.getChildren().add(newXs(cell));
            break;
          default:
            break;
        }
      }
    };
    // Registering the event filter
    scene.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

    primaryStage.setTitle("Tic-Tac-Toe");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
