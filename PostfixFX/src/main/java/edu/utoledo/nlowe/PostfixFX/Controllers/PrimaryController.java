package edu.utoledo.nlowe.PostfixFX.Controllers;

import edu.utoledo.nlowe.PostfixFX.CalculatorButton;
import edu.utoledo.nlowe.postfix.PostfixEngine;
import edu.utoledo.nlowe.postfix.exception.PostfixArithmeticException;
import edu.utoledo.nlowe.postfix.exception.PostfixOverflowException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nathan on 9/29/15
 */
public class PrimaryController implements Initializable
{

    @FXML
    private ToggleButton modeButton;
    @FXML
    private TextArea resultBox;
    @FXML
    private TextField entryBox;
    @FXML
    private CalculatorButton leftShift, rightShift;
    @FXML
    private GridPane buttonGrid;

    private PostfixEngine engine;

    private boolean firstEntry = true;
    private long lastResult = 0L;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        engine = new PostfixEngine();
        leftShift.setEntry("<");
        rightShift.setEntry(">");

        entryBox.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                System.out.println("Enter");
                onEnter(null);
            }
        });
    }

    @FXML
    private void onButton(ActionEvent e)
    {
        CalculatorButton button = (CalculatorButton) e.getSource();
        System.out.println("Click from " + button.getEntry());

        if (entryBox.getText().isEmpty() && !firstEntry && engine.isValidOperator(button.getEntry()))
        {
            entryBox.appendText(String.format("%d ", lastResult));
        }

        if (modeButton.isSelected() && engine.isValidOperator(button.getEntry()))
        {
            if (!entryBox.getText().matches(".*" + PostfixEngine.TOKEN_SEPARATOR_REGEX))
            {
                entryBox.appendText(" ");
            }
            entryBox.appendText(button.getEntry() + " ");
        }
        else
        {
            entryBox.appendText(button.getEntry());
        }

        entryBox.requestFocus();
        entryBox.deselect();
        entryBox.positionCaret(entryBox.getText().length());
    }

    @FXML
    private void onEnter(ActionEvent e)
    {
        if (entryBox.getText().isEmpty())
        {
            return;
        }

        Long result = null;
        try
        {
            result = modeButton.isSelected() ? engine.evaluate(entryBox.getText()) : engine.evaluateInfix(entryBox.getText());

            resultBox.setText(String.format("%d: %s%n%s", result, entryBox.getText(), resultBox.getText()));
        }
        catch (PostfixArithmeticException ex)
        {
            result = ex.getResult();
            resultBox.setText(String.format("%d: (%s) %s%n%s", ex.getResult(), (ex instanceof PostfixOverflowException ? "Overflow" : "Underflow"), entryBox.getText(), resultBox.getText()));
        }
        catch (IllegalArgumentException ex)
        {
            resultBox.setText(String.format("%s%n%s", ex.getMessage(), resultBox.getText()));
        }
        finally
        {
            if (result != null)
            {
                lastResult = result;
                firstEntry = false;
            }
            entryBox.clear();
            resultBox.positionCaret(0);
        }
    }

    public void onModeChanged(ActionEvent actionEvent)
    {
        ToggleButton source = (ToggleButton) actionEvent.getSource();
        if (source.isSelected())
        {
            source.setText("RPN");
        }
        else
        {
            source.setText("STD");
        }
    }
}
