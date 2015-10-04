package edu.utoledo.nlowe.PostfixFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 * A button that contains the text to append to the entry box
 * when clicked. This is needed because some characters ('<' and '>')
 * are not allowed in the test property of standard buttons
 */
public class CalculatorButton extends Button
{
    private StringProperty entry = new SimpleStringProperty();

    public CalculatorButton()
    {
        super();

        this.getStyleClass().add("calculator-button");

        // The entry, by default, is the same as the text assigned to the button
        this.entry.bind(this.textProperty());

        this.setMinSize(0, 0);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public String getEntry()
    {
        return entry.get();
    }

    public void setEntry(String entry)
    {
        // If someone sets a custom entry, we need to
        // unbind it from the text property first
        this.entry.unbind();
        this.entry.set(entry);
    }
}
