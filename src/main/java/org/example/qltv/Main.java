package org.example.qltv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("SignIn"));
        stage.setScene(scene);
        stage.setTitle("Library Management System");
        stage.show();
    }
    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        stage.setWidth(root.prefWidth(-1));
        stage.setHeight(root.prefHeight(-1));
    }

    private static Parent loadFXML(String fxml) throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        launch();

    }
}


