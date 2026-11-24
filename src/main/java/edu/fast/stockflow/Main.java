package edu.fast.stockflow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox content = new VBox(8, new Label("StockFlow"), new Label("JavaFX setup check"));
        content.setAlignment(Pos.CENTER);

        stage.setTitle("StockFlow");
        stage.setScene(new Scene(content, 440, 240));
        stage.show();

        if (getParameters().getRaw().contains("--smoke")) {
            System.out.println("StockFlow JavaFX window opened.");
            Platform.exit();
        }
    }
}
