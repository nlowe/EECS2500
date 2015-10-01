package edu.utoledo.nlowe.PostfixFX.tests;

import edu.utoledo.nlowe.PostfixFX.CalculatorButton;
import edu.utoledo.nlowe.PostfixFX.Controllers.PrimaryController;
import edu.utoledo.nlowe.PostfixFX.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by nathan on 9/30/15
 */
public class CalculatorTest extends ApplicationTest
{
    private PrimaryController controller;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        interact(() -> {
            FXMLLoader loader = new FXMLLoader(CalculatorButton.class.getResource("/ui/main.fxml"));

            Parent root = null;
            try
            {
                root = loader.load();controller = loader.getController();

                primaryStage.setScene(new Scene(root, Main.CALCULATOR_WIDTH, Main.CALCULATOR_HEIGHT));
                primaryStage.setTitle("PostfixFX");
                primaryStage.setMinHeight(Main.CALCULATOR_HEIGHT);
                primaryStage.setMinWidth(Main.CALCULATOR_WIDTH);
                primaryStage.setResizable(false);
                primaryStage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        });

    }

    @Before
    public void setUp(){
        controller.getEntryBox().clear();
        controller.getResultBox().clear();
    }

    @Test
    public void allButtonsWork(){
        clickOn("7").clickOn("8").clickOn("9").clickOn("+").clickOn("SQRT").clickOn("(");
        clickOn("4").clickOn("5").clickOn("6").clickOn("-").clickOn("CBRT").clickOn(")");
        clickOn("1").clickOn("2").clickOn("3").clickOn("*").clickOn("LSH");
        clickOn("0").clickOn("MOD").clickOn("x^y").clickOn("/").clickOn("RSH");
        assertEquals("FAIL789+Q(456-C)123*<0%^/>", controller.getEntryBox().getText());
    }

    @Test
    public void oneTestStandard(){
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestStandard(){
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");
        clickOn("3").clickOn("+").clickOn("3").clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 3+3%n4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestStandardWithRecall(){
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");
        clickOn("+").clickOn("2");

        assertEquals("4 +2", controller.getEntryBox().getText());

        clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 4 +2%n4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void oneTestPostfix(){
        clickOn("#ModeButton");

        clickOn("2");
        controller.getEntryBox().appendText(" ");
        clickOn("2").clickOn("+").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("4: 2 2 + %n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestPostfix(){
        clickOn("#ModeButton");

        clickOn("2").push(KeyCode.SPACE).clickOn("2").clickOn("+").clickOn("#EnterButton");
        clickOn("3").push(KeyCode.SPACE).clickOn("3").clickOn("+").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 3 3 + %n4: 2 2 + %n");
        assertEquals(expected, controller.getResultBox().getText());
    }
    
    @Test
    public void focusesEntryBoxAfterButton(){
        clickOn("7").push(KeyCode.Z).clickOn("8").push(KeyCode.Z).clickOn("9").push(KeyCode.Z).clickOn("+").push(KeyCode.Z).clickOn("SQRT").push(KeyCode.Z).clickOn("(").push(KeyCode.Z);
        clickOn("4").push(KeyCode.Z).clickOn("5").push(KeyCode.Z).clickOn("6").push(KeyCode.Z).clickOn("-").push(KeyCode.Z).clickOn("CBRT").push(KeyCode.Z).clickOn(")").push(KeyCode.Z);
        clickOn("1").push(KeyCode.Z).clickOn("2").push(KeyCode.Z).clickOn("3").push(KeyCode.Z).clickOn("*").push(KeyCode.Z).clickOn("LSH").push(KeyCode.Z);
        clickOn("0").push(KeyCode.Z).clickOn("MOD").push(KeyCode.Z).clickOn("x^y").push(KeyCode.Z).clickOn("/").push(KeyCode.Z).clickOn("RSH").push(KeyCode.Z);

        assertEquals("7z8z9z+zQz(z4z5z6z-zCz)z1z2z3z*z<z0z%z^z/z>z", controller.getEntryBox().getText());
    }

    @Test
    public void allButtonsWorkInPostfixMode(){
        clickOn("#ModeButton");

        clickOn("7").clickOn("8").clickOn("9").clickOn("+").clickOn("SQRT").clickOn("(");
        clickOn("4").clickOn("5").clickOn("6").clickOn("-").clickOn("CBRT").clickOn(")");
        clickOn("1").clickOn("2").clickOn("3").clickOn("*").clickOn("LSH");
        clickOn("0").clickOn("MOD").clickOn("x^y").clickOn("/").clickOn("RSH");
        assertEquals("789 + Q (456 - C )123 * < 0 % ^ / > ", controller.getEntryBox().getText());
    }
}
