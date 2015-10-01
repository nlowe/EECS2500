package edu.utoledo.nlowe.PostfixFX.tests;

import edu.utoledo.nlowe.PostfixFX.Controllers.PrimaryController;
import edu.utoledo.nlowe.PostfixFX.Main;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

/**
 * Created by nathan on 9/30/15
 */
public class CalculatorTest extends ApplicationTest
{
    private PrimaryController controller;

    private Main calculator;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        calculator = new Main();
        calculator.start(primaryStage);

        controller = calculator.getController();
    }

    @Override
    public void stop() throws Exception
    {
        calculator.stop();
    }

    @Before
    public void setUp()
    {
        controller.getEntryBox().clear();
        controller.getResultBox().clear();
    }

    @Test
    public void allButtonsWork()
    {
        clickOn("7").clickOn("8").clickOn("9").clickOn("+").clickOn("SQRT").clickOn("(");
        clickOn("4").clickOn("5").clickOn("6").clickOn("-").clickOn("CBRT").clickOn(")");
        clickOn("1").clickOn("2").clickOn("3").clickOn("*").clickOn("LSH");
        clickOn("0").clickOn("MOD").clickOn("x^y").clickOn("/").clickOn("RSH");
        assertEquals("789+Q(456-C)123*<0%^/>", controller.getEntryBox().getText());
    }

    @Test
    public void oneTestStandard()
    {
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestStandard()
    {
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");
        clickOn("3").clickOn("+").clickOn("3").clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 3+3%n4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestStandardWithRecall()
    {
        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");
        clickOn("+").clickOn("2");

        assertEquals("4 +2", controller.getEntryBox().getText());

        clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 4 +2%n4: 2+2%n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void oneTestPostfix()
    {
        clickOn("#ModeButton");

        clickOn("2");
        controller.getEntryBox().appendText(" ");
        clickOn("2").clickOn("+").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("4: 2 2 + %n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void multiTestPostfix()
    {
        clickOn("#ModeButton");

        clickOn("2").push(KeyCode.SPACE).clickOn("2").clickOn("+").clickOn("#EnterButton");
        clickOn("3").push(KeyCode.SPACE).clickOn("3").clickOn("+").clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());

        String expected = String.format("6: 3 3 + %n4: 2 2 + %n");
        assertEquals(expected, controller.getResultBox().getText());
    }

    @Test
    public void focusesEntryBoxAfterButton()
    {
        clickOn("7").push(KeyCode.Z).clickOn("8").push(KeyCode.Z).clickOn("9").push(KeyCode.Z).clickOn("+").push(KeyCode.Z).clickOn("SQRT").push(KeyCode.Z).clickOn("(").push(KeyCode.Z);
        clickOn("4").push(KeyCode.Z).clickOn("5").push(KeyCode.Z).clickOn("6").push(KeyCode.Z).clickOn("-").push(KeyCode.Z).clickOn("CBRT").push(KeyCode.Z).clickOn(")").push(KeyCode.Z);
        clickOn("1").push(KeyCode.Z).clickOn("2").push(KeyCode.Z).clickOn("3").push(KeyCode.Z).clickOn("*").push(KeyCode.Z).clickOn("LSH").push(KeyCode.Z);
        clickOn("0").push(KeyCode.Z).clickOn("MOD").push(KeyCode.Z).clickOn("x^y").push(KeyCode.Z).clickOn("/").push(KeyCode.Z).clickOn("RSH").push(KeyCode.Z);

        assertEquals("7z8z9z+zQz(z4z5z6z-zCz)z1z2z3z*z<z0z%z^z/z>z", controller.getEntryBox().getText());
    }

    @Test
    public void allButtonsWorkInPostfixMode()
    {
        clickOn("#ModeButton");

        clickOn("7").clickOn("8").clickOn("9").clickOn("+").clickOn("SQRT").clickOn("(");
        clickOn("4").clickOn("5").clickOn("6").clickOn("-").clickOn("CBRT").clickOn(")");
        clickOn("1").clickOn("2").clickOn("3").clickOn("*").clickOn("LSH");
        clickOn("0").clickOn("MOD").clickOn("x^y").clickOn("/").clickOn("RSH");
        assertEquals("789 + Q (456 - C )123 * < 0 % ^ / > ", controller.getEntryBox().getText());
    }

    @Test
    public void doesNothingWithEmptyEntry()
    {
        clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());
        assertEquals("", controller.getResultBox().getText());

        clickOn("2").clickOn("+").clickOn("2").clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("4: 2+2%n"), controller.getResultBox().getText());
    }

    @Test
    public void doesNothingWithEmptyEntryPostfix()
    {
        clickOn("#ModeButton");
        clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());
        assertEquals("", controller.getResultBox().getText());

        clickOn("2").push(KeyCode.SPACE).clickOn("2").clickOn("+").clickOn("#EnterButton");
        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("4: 2 2 + %n"), controller.getResultBox().getText());
    }

    @Test
    public void warnsAboutOverAndUnderFlow()
    {
        controller.getEntryBox().setText("2<31+1");
        clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("4294967297: (Overflow) 2<31+1%n"), controller.getResultBox().getText());

        controller.getEntryBox().setText("(0-1)*(2<31)");
        clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("-4294967296: (Underflow) (0-1)*(2<31)%n4294967297: (Overflow) 2<31+1%n"), controller.getResultBox().getText());
    }

    @Test
    public void warnsAboutIllegalArguments()
    {
        controller.getEntryBox().setText("@");
        clickOn("#EnterButton");

        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("Unrecognized token '@'%n"), controller.getResultBox().getText());
    }

    @Test
    public void canDoEnterWithEnterKey()
    {
        clickOn("2").clickOn("+").clickOn("2").push(KeyCode.ENTER);

        assertEquals("", controller.getEntryBox().getText());
        assertEquals(String.format("4: 2+2%n"), controller.getResultBox().getText());
    }

    @Test
    public void modeButtonSetsCorrectText()
    {
        assertEquals("STD", controller.getModeButton().getText());
        clickOn("#ModeButton");
        assertEquals("RPN", controller.getModeButton().getText());
        clickOn("#ModeButton");
        assertEquals("STD", controller.getModeButton().getText());
    }
}
