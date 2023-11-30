/*
 * Created by JFormDesigner on Thu Nov 30 01:35:32 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import java.awt.event.*;
import me.h3xadecimal.baimagesplitter.Application;
import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;
import me.h3xadecimal.baimagesplitter.utils.ImageSplitter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author ht_ge
 */
public class PanelExportImage extends JPanel {
    private final UiMain main;

    public PanelExportImage(UiMain main) {
        this.main = main;
        initComponents();

        cbFormat.addItem("png");
    }

    private void export(BufferedImage[] images) {
        String format = (String) cbFormat.getSelectedItem();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("导出");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(Application.INSTANCE.getRunningDir());
        chooser.showSaveDialog(this);

        File dir = chooser.getSelectedFile();
        if (dir != null) {
            if (!dir.exists() || !dir.isDirectory()) {
                JOptionPane.showMessageDialog(this, "目录不存在或不是目录");
                return;
            }

            int errors = 0;
            StringBuilder err = new StringBuilder();
            err.append("导出完成，但出现").append(errors).append("个错误：");
            for (int i = 0; i < images.length; i++) {
                BufferedImage bi = images[i];
                File f = new File(dir, i + "." + format);
                try {
                    ImageIO.write(bi, format, f);
                } catch (Throwable t) {
                    main.logger.error("输出" + f.getName() + "时出错");
                    err.append("\n").append(t);
                    errors++;
                }
            }
            System.gc();
            if (errors != 0) {
                JOptionPane.showMessageDialog(this, err.toString());
            } else {
                JOptionPane.showMessageDialog(this, "输出完成，没有错误发生");
            }
        } else {
            main.logger.info("未选择输出目录");
        }
    }

    private void exportSimple(MouseEvent e) {
        BufferedImage source = main.getCurrent();
        if (source == null) {
            JOptionPane.showMessageDialog(this, "未选择图像");
            return;
        }

        int count;
        try {
            count = Integer.parseInt(main.getSimpleSplit().getCount());
        } catch (Throwable t) {
            main.logger.info("无法解析数字", t);
            JOptionPane.showMessageDialog(this, "未指定数量或无效数字");
            return;
        }

        main.logger.info("开始输出");
        export(ImageSplitter.splitSimple(source, count));
    }

    private void exportManual(MouseEvent e) {
        BufferedImage source = main.getCurrent();
        if (source == null) {
            JOptionPane.showMessageDialog(this, "未选择图像");
            return;
        }

        List<Integer> points = main.getManualSplit().getAnchors();
        points.add(source.getHeight());
        main.logger.info("已选中 " + points.size() + "个锚点");

        main.logger.info("开始输出");
        export(ImageSplitter.splitManual(source, points));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        bottomBar = new JPanel();
        btnExportSimple = new JButton();
        btnExportManual = new JButton();
        pnExportProperty = new JPanel();
        pnFormat = new JPanel();
        lbFormat = new JLabel();
        cbFormat = new JComboBox<>();

        //======== this ========
        setLayout(new BorderLayout());

        //======== bottomBar ========
        {
            bottomBar.setLayout(new GridLayout(1, 2));

            //---- btnExportSimple ----
            btnExportSimple.setText("\u5bfc\u51fa\u7b80\u5355\u6a21\u5f0f");
            btnExportSimple.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    exportSimple(e);
                }
            });
            bottomBar.add(btnExportSimple);

            //---- btnExportManual ----
            btnExportManual.setText("\u5bfc\u51fa\u624b\u52a8\u6a21\u5f0f");
            btnExportManual.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    exportManual(e);
                }
            });
            bottomBar.add(btnExportManual);
        }
        add(bottomBar, BorderLayout.SOUTH);

        //======== pnExportProperty ========
        {
            pnExportProperty.setLayout(new GridLayout(5, 1));

            //======== pnFormat ========
            {
                pnFormat.setLayout(new GridLayout(1, 2));

                //---- lbFormat ----
                lbFormat.setText("\u5bfc\u51fa\u683c\u5f0f");
                pnFormat.add(lbFormat);
                pnFormat.add(cbFormat);
            }
            pnExportProperty.add(pnFormat);
        }
        add(pnExportProperty, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel bottomBar;
    private JButton btnExportSimple;
    private JButton btnExportManual;
    private JPanel pnExportProperty;
    private JPanel pnFormat;
    private JLabel lbFormat;
    private JComboBox<String> cbFormat;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
