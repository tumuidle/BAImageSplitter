/*
 * Created by JFormDesigner on Thu Nov 30 01:10:53 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import me.h3xadecimal.baimagesplitter.ui.components.ComponentPropertyInput;
import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;

import java.awt.*;
import javax.swing.*;

/**
 * @author ht_ge
 */
public class PanelSimpleSplit extends JPanel {
    private final UiMain main;

    private final ComponentPropertyInput count = new ComponentPropertyInput("拆分数量");

    public PanelSimpleSplit(UiMain main) {
        this.main = main;
        initComponents();

        add(count);
    }

    public String getCount() {
        return count.getValue();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off

        //======== this ========
        setLayout(new GridLayout(10, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
