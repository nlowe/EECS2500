package edu.utoledo.nlowe.PostfixFX.Controllers;

import edu.utoledo.nlowe.PostfixFX.CalculatorButton;
import edu.utoledo.nlowe.postfix.PostfixEngine;
import edu.utoledo.nlowe.postfix.exception.PostfixArithmeticException;
import edu.utoledo.nlowe.postfix.exception.PostfixOverflowException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The main controller for the calculator
 */
public class PrimaryController implements Initializable
{

    @FXML
    private MenuItem copyMenuItem;
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
        // '<' and '>' aren't allowed in button text
        // Set the entry characters for Left-Shift and Right-Shift buttons now
        leftShift.setEntry("<");
        rightShift.setEntry(">");

        entryBox.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                // Emulate the enter button being clicked
                onEnter(null);
            }
        });

        resultBox.selectionProperty().addListener((observable, oldValue, newValue) -> {
            copyMenuItem.setDisable(newValue.getLength() <= 0);
        });
    }

    @FXML
    private void onButton(ActionEvent e)
    {
        CalculatorButton button = (CalculatorButton) e.getSource();

        if (entryBox.getText().isEmpty() && !firstEntry && engine.isValidOperator(button.getEntry()))
        {
            // If this is the first button press for this entry and it's
            // not the first entry being evaluated, AND the button clicked
            // was an operator, prepend the last result to the entry
            entryBox.appendText(String.format("%d ", lastResult));
        }

        if (modeButton.isSelected() && engine.isValidOperator(button.getEntry()))
        {
            // Adding an operator in postfix mode
            if (!entryBox.getText().matches(".*" + PostfixEngine.TOKEN_SEPARATOR_REGEX))
            {
                // If the entry does not yet end with a separator, append a space
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
            // Don't even bother evaluating empty entries
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
            // There was an overflow or underflow. The result is in the exception
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
                // If we actually got a result, remember it
                lastResult = result;
                firstEntry = false;
            }

            // Reset the entry box and scroll the result box to the top
            entryBox.clear();
            resultBox.positionCaret(0);
            entryBox.requestFocus();
            entryBox.deselect();
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
        entryBox.requestFocus();
        entryBox.deselect();
        entryBox.positionCaret(entryBox.getText().length());
    }

    public TextArea getResultBox()
    {
        return resultBox;
    }

    public TextField getEntryBox()
    {
        return entryBox;
    }

    public ToggleButton getModeButton()
    {
        return modeButton;
    }

    public void onClearResultBox(ActionEvent actionEvent)
    {
        resultBox.clear();
        entryBox.requestFocus();
        entryBox.deselect();
        entryBox.positionCaret(entryBox.getText().length());
    }

    public void onCopyFromResultBox(ActionEvent actionEvent)
    {
        ClipboardContent content = new ClipboardContent();
        content.putString(resultBox.getSelectedText());

        Clipboard.getSystemClipboard().setContent(content);
    }
}
