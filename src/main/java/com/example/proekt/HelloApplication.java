package com.example.proekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main window");
        stage.setScene(scene);
        stage.show();
    }

    static public void openAnotherWindow(String name){
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(name));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}