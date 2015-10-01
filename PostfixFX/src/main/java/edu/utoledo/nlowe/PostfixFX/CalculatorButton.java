package edu.utoledo.nlowe.PostfixFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 * Created by nathan on 9/29/15
 */
public class CalculatorButton extends Button
{
    private StringProperty entry = new SimpleStringProperty();

    public CalculatorButton()
    {
        super();

        this.getStyleClass().add("calculator-button");

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
        this.entry.unbind();
        this.entry.set(entry);
    }
}
