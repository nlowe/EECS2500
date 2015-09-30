package edu.utoledo.nlowe.PostfixFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by nathan on 9/29/15
 */
public class Main extends Application
{

    public static void main(String[] args)
    {
        Application.launch((args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(CalculatorButton.class.getResource("/ui/main.fxml"));

        primaryStage.setScene(new Scene(root, 550, 560));
        primaryStage.setTitle("PostfixFX");
        primaryStage.setMinHeight(560);
        primaryStage.setMinWidth(550);
        //primaryStage.setResizable(false);
        primaryStage.show();
    }
}
