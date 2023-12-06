/*
 * Created by JFormDesigner on Thu Nov 30 01:10:40 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author ht_ge
 */
public class PanelManualSplit extends JPanel {
    private final UiMain main;

    private double scale = 1.0;
    private Image scaledImg;
    private final List<Integer> anchors = new ArrayList<>();

    public PanelManualSplit(UiMain main) {
        this.main = main;
        initComponents();
    }

    void refresh() {
        BufferedImage original = main.getCurrent();
        scale = (double) (lbImage.getWidth()-slider.getWidth()) /original.getWidth();
        int width = (int) Math.round(original.getWidth()*scale);
        int height = (int) Math.round(scale*original.getHeight());
        main.logger.info(String.format("scale=%s , width=%d , height=%d", scale, width, height));
        scaledImg = original.getScaledInstance(width, height, Image.SCALE_FAST);
        Icon ico = new ImageIcon(scaledImg);

        lbImage.setIcon(ico);
        lbImage.setSize(width, height);
        slider.setMaximum(lbImage.getHeight());

        refreshList();
    }

    private void refreshList() {
        listCurrent.setModel(new AbstractListModel<>() {
            @Override
            public int getSize() {
                return anchors.size();
            }

            @Override
            public String getElementAt(int index) {
                return String.valueOf(anchors.get(index));
            }
        });
    }

    private void refreshClick(MouseEvent e) {
        refresh();
    }

    private void addAnchor(MouseEvent e) {
        anchors.add((int) ((lbImage.getHeight()-slider.getValue())/scale));
        refreshList();
    }

    private void removeSelected(MouseEvent e) {
        anchors.remove(listCurrent.getSelectedIndex());
        refreshList();
    }

    public double getScale() {
        return scale;
    }

    public List<Integer> getAnchors() {
        return anchors;
    }

    private void removeAll(MouseEvent e) {
        anchors.clear();
        refreshList();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        pnOperation = new JPanel();
        btnRefresh = new JButton();
        btnAdd = new JButton();
        btnRemove = new JButton();
        btnClear = new JButton();
        spCurrent = new JScrollPane();
        listCurrent = new JList<>();
        spSplitter = new JScrollPane();
        pnSplitter = new JPanel();
        slider = new JSlider();
        pnPreview = new JPanel();
        lbImage = new JLabel();

        //======== this ========
        setLayout(new BorderLayout());

        //======== pnOperation ========
        {
            pnOperation.setLayout(new GridLayout(4, 1));

            //---- btnRefresh ----
            btnRefresh.setText("\u5237\u65b0\u663e\u793a");
            btnRefresh.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshClick(e);
                }
            });
            pnOperation.add(btnRefresh);

            //---- btnAdd ----
            btnAdd.setText("\u6dfb\u52a0\u951a\u70b9");
            btnAdd.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addAnchor(e);
                }
            });
            pnOperation.add(btnAdd);

            //---- btnRemove ----
            btnRemove.setText("\u79fb\u9664\u9009\u4e2d");
            btnRemove.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeSelected(e);
                }
            });
            pnOperation.add(btnRemove);

            //---- btnClear ----
            btnClear.setText("\u6e05\u7a7a\u5168\u90e8");
            btnClear.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeAll(e);
                }
            });
            pnOperation.add(btnClear);
        }
        add(pnOperation, BorderLayout.WEST);

        //======== spCurrent ========
        {
            spCurrent.setViewportView(listCurrent);
        }
        add(spCurrent, BorderLayout.EAST);

        //======== spSplitter ========
        {

            //======== pnSplitter ========
            {
                pnSplitter.setLayout(new BorderLayout());

                //---- slider ----
                slider.setOrientation(SwingConstants.VERTICAL);
                pnSplitter.add(slider, BorderLayout.WEST);

                //======== pnPreview ========
                {
                    pnPreview.setLayout(new BorderLayout());

                    //---- lbImage ----
                    lbImage.setText("text");
                    pnPreview.add(lbImage, BorderLayout.CENTER);
                }
                pnSplitter.add(pnPreview, BorderLayout.CENTER);
            }
            spSplitter.setViewportView(pnSplitter);
        }
        add(spSplitter, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel pnOperation;
    private JButton btnRefresh;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnClear;
    private JScrollPane spCurrent;
    private JList<String> listCurrent;
    private JScrollPane spSplitter;
    private JPanel pnSplitter;
    private JSlider slider;
    private JPanel pnPreview;
    private JLabel lbImage;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
