package me.h3xadecimal.baimagesplitter.ui.components;

import javax.swing.*;
import java.awt.*;

public class ComponentPropertyInput extends JPanel {
    private String key;
    private final JLabel keyLabel;
    private final JTextField valueField;

    public ComponentPropertyInput(String key) {
        this.key = key;
        this.keyLabel = new JLabel(key);
        this.valueField = new JTextField();

        setLayout(new GridLayout(1, 2));
        add(keyLabel);
        add(valueField);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return valueField.getText();
    }
}
