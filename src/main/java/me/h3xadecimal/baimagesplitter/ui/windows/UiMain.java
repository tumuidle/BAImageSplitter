/*
 * Created by JFormDesigner on Thu Nov 30 00:27:16 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.windows;

import me.h3xadecimal.baimagesplitter.Application;
import me.h3xadecimal.baimagesplitter.ui.panels.PanelAddImage;
import me.h3xadecimal.baimagesplitter.ui.panels.PanelExportImage;
import me.h3xadecimal.baimagesplitter.ui.panels.PanelManualSplit;
import me.h3xadecimal.baimagesplitter.ui.panels.PanelSimpleSplit;
import me.h3xadecimal.baimagesplitter.utils.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * @author ht_ge
 */
public class UiMain extends JFrame {
    public final Logger logger = LogManager.getLogger("Ui");
    private BufferedImage current = null;

    private final PanelAddImage addImage;
    private final PanelSimpleSplit simpleSplit;
    private final PanelManualSplit manualSplit;
    private final PanelExportImage exportImage;

    public UiMain() {
        initComponents();

        addImage = new PanelAddImage(this);
        tp.add("加载图像", addImage);
        simpleSplit = new PanelSimpleSplit(this);
        tp.add("简单剪裁", simpleSplit);
        manualSplit = new PanelManualSplit(this);
        tp.add("手动剪裁", manualSplit);
        exportImage = new PanelExportImage(this);
        tp.add("导出图像", exportImage);

        setIconImage(Resources.getIcon());
    }

    private void close(WindowEvent e) {
        Application.INSTANCE.exit(0);
    }

    public void setCurrent(BufferedImage img) {
        this.current = img;
    }

    public BufferedImage getCurrent() {
        return current;
    }

    public PanelAddImage getAddImage() {
        return addImage;
    }

    public PanelSimpleSplit getSimpleSplit() {
        return simpleSplit;
    }

    public PanelManualSplit getManualSplit() {
        return manualSplit;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        tp = new JTabbedPane();

        //======== this ========
        setTitle("BAImageSplitter");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(tp, BorderLayout.CENTER);
        setSize(635, 430);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTabbedPane tp;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
