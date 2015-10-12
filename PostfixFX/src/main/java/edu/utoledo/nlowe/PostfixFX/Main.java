package edu.utoledo.nlowe.PostfixFX;

import edu.utoledo.nlowe.PostfixFX.Controllers.PrimaryController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A calculator that uses libpostfix to evaluate expressions in
 * either Standard Notation or Postfix Notation
 */
public class Main extends Application
{

    /** The set width of the calculator */
    public static final int CALCULATOR_WIDTH = 550;
    /** The set height of the calculator */
    public static final int CALCULATOR_HEIGHT = 560;

    /** The controller for the UI */
    private PrimaryController controller;

    public static void main(String[] args)
    {
        Application.launch((args));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Load the UI from the layout and style files
        FXMLLoader loader = new FXMLLoader(CalculatorButton.class.getResource("/ui/main.fxml"));
        Parent root = loader.load();

        // Get the controller
        controller = loader.getController();

        // Start the application
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
