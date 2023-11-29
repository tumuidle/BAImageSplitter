/*
 * Created by JFormDesigner on Thu Nov 30 01:10:40 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;
import me.h3xadecimal.baimagesplitter.utils.parts.EnumImagePart;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author ht_ge
 */
public class PanelManualSplit extends JPanel {
    private final UiMain main;

    private final List<EnumImagePart> current = new ArrayList<>();

    public PanelManualSplit(UiMain main) {
        this.main = main;
        initComponents();

        pnComponents.setLayout(new GridLayout(EnumImagePart.getEntries().size(), 1));
        for (EnumImagePart part: EnumImagePart.getEntries()) {
            JButton btn = new JButton(part.getDisplayName());
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    current.add(part);
                    updateList();
                }
            });
            pnComponents.add(btn);
        }
    }

    private void updateList() {
        listAdded.setModel(new AbstractListModel<>() {
            @Override
            public int getSize() {
                return current.size();
            }

            @Override
            public String getElementAt(int index) {
                return current.get(index).getDisplayName();
            }
        });
    }

    private void removeSelected(MouseEvent e) {
        int ind = listAdded.getSelectedIndex();
        if (ind != -1) {
            current.remove(ind);
            updateList();
        }
    }

    private void clear(MouseEvent e) {
        current.clear();
        updateList();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        pnComponents = new JPanel();
        sp = new JScrollPane();
        listAdded = new JList<>();
        pnOperation = new JPanel();
        btnRemoveSelected = new JButton();
        btnClear = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== pnComponents ========
        {
            pnComponents.setLayout(new GridLayout(1, 10));
        }
        add(pnComponents, BorderLayout.WEST);

        //======== sp ========
        {
            sp.setViewportView(listAdded);
        }
        add(sp, BorderLayout.EAST);

        //======== pnOperation ========
        {
            pnOperation.setLayout(new GridLayout(5, 1));

            //---- btnRemoveSelected ----
            btnRemoveSelected.setText("\u79fb\u9664\u9009\u4e2d");
            btnRemoveSelected.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeSelected(e);
                }
            });
            pnOperation.add(btnRemoveSelected);

            //---- btnClear ----
            btnClear.setText("\u6e05\u7a7a");
            btnClear.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clear(e);
                }
            });
            pnOperation.add(btnClear);
        }
        add(pnOperation, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel pnComponents;
    private JScrollPane sp;
    private JList<String> listAdded;
    private JPanel pnOperation;
    private JButton btnRemoveSelected;
    private JButton btnClear;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
