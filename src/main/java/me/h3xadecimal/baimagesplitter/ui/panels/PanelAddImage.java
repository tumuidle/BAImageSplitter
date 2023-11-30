/*
 * Created by JFormDesigner on Thu Nov 30 00:35:30 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import java.awt.event.*;
import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author ht_ge
 */
public class PanelAddImage extends JPanel {
    private final UiMain main;

    public PanelAddImage(UiMain main) {
        this.main = main;
        initComponents();
    }

    private void addImage(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("加载图像");
        chooser.setFileFilter(new FileNameExtensionFilter("图像", "jpg", "png"));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(main);

        File f = chooser.getSelectedFile();
        if (f != null && f.isFile()) {
            main.logger.info("选择文件 " + f.getAbsolutePath());
            try {
                BufferedImage img = ImageIO.read(f);

                int warns = 0;
                StringBuilder warnMsg = new StringBuilder();
                if (img.getWidth() != 1000) {
                    warns ++;
                    warnMsg.append("\n图像宽度不为1000，可能不适用于手动分割模式");
                }
                if (warns != 0) {
                    JOptionPane.showMessageDialog(this, "出现" + warns + "个警告：" + warnMsg);
                }

                main.setCurrent(ImageIO.read(f));
                image.setIcon(new ImageIcon(main.getCurrent()));
                main.getManualSplit().refresh();
            } catch (Throwable t) {
                main.logger.error("文件加载失败", t);
                JOptionPane.showMessageDialog(this, "文件加载失败：\n" + t);
            }
        } else {
            main.logger.info("未选择文件");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        btnLoadImage = new JButton();
        image = new JLabel();

        //======== this ========
        setLayout(new BorderLayout());

        //---- btnLoadImage ----
        btnLoadImage.setText("\u52a0\u8f7d\u56fe\u50cf");
        btnLoadImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addImage(e);
            }
        });
        add(btnLoadImage, BorderLayout.SOUTH);

        //---- image ----
        image.setText("\u9884\u89c8");
        add(image, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton btnLoadImage;
    private JLabel image;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
