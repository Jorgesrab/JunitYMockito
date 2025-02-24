package com.example.stock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ensure this path matches the resources folder structure
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Stock Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}