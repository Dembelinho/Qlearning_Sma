package Ayou.dev.qlearning.sma;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class QLearningApp extends Application {
    private static Label[][] gridLabels;
    private static final int GRID_SIZE = 3;

    public static void updateAgentPosition(int i, int j) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (row == i && col == j) {
                    gridLabels[row][col].setStyle("-fx-background-color: yellow;");
                } else {
                    gridLabels[row][col].setStyle("");
                }
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridLabels = new Label[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Label label = new Label();
                label.setMinSize(50, 50);
                label.setMaxSize(50, 50);
                label.setStyle("-fx-border-color: black;");
                gridLabels[i][j] = label;
                gridPane.add(label, j, i);
            }
        }

        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QLearning Agent");
        primaryStage.show();
    }
}

