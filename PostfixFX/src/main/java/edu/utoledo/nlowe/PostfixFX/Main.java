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

    public static final int CALCULATOR_WIDTH = 550;
    public static final int CALCULATOR_HEIGHT = 560;

    public static void main(String[] args)
    {
        Application.launch((args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(CalculatorButton.class.getResource("/ui/main.fxml"));

        primaryStage.setScene(new Scene(root, CALCULATOR_WIDTH, CALCULATOR_HEIGHT));
        primaryStage.setTitle("PostfixFX");
        primaryStage.setMinHeight(CALCULATOR_HEIGHT);
        primaryStage.setMinWidth(CALCULATOR_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
