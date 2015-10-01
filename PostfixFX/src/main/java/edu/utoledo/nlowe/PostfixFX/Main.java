package edu.utoledo.nlowe.PostfixFX;

import edu.utoledo.nlowe.PostfixFX.Controllers.PrimaryController;
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

    private PrimaryController controller;

    public static void main(String[] args)
    {
        Application.launch((args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(CalculatorButton.class.getResource("/ui/main.fxml"));

        Parent root = loader.load();
        controller = loader.getController();

        primaryStage.setScene(new Scene(root, Main.CALCULATOR_WIDTH, Main.CALCULATOR_HEIGHT));
        primaryStage.setTitle("PostfixFX");
        primaryStage.setMinHeight(Main.CALCULATOR_HEIGHT);
        primaryStage.setMinWidth(Main.CALCULATOR_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public PrimaryController getController()
    {
        return controller;
    }
}
