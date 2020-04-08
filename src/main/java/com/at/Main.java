package com.at;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("sample.fxml"); //incarca resursa
        Parent root = fxmlLoader.load(resourceAsStream);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
