/*
 * Created by JFormDesigner on Thu Nov 30 01:35:32 CST 2023
 */

package me.h3xadecimal.baimagesplitter.ui.panels;

import java.awt.event.*;
import me.h3xadecimal.baimagesplitter.Application;
import me.h3xadecimal.baimagesplitter.ui.windows.UiMain;
import me.h3xadecimal.baimagesplitter.utils.ImageProcessor;

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

    private BufferedImage watermark = null;

    public PanelExportImage(UiMain main) {
        this.main = main;
        initComponents();

        cbFormat.addItem("png");
        cbFormat.addItem("jpg");

        cbObfuscation.addItem("关闭");
        cbObfuscation.addItem("随机线条");
        cbObfuscation.addItem("点阵");
    }

    private void export(BufferedImage[] images) {
        main.logger.info("正在重载图像");
        try {
            main.getAddImage().reloadLastSelected();
        } catch (Throwable t) {
            main.logger.warn("尝试重载失败，将使用已加载的图像", t);
        }

        main.logger.info("正在导出");
        List<BufferedImage> proceed = new ArrayList<>(List.of(images));

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

            for (BufferedImage bi: images) {
                if (watermark != null) ImageProcessor.createWaterMark(watermark, 100, bi);
                switch ((String) cbObfuscation.getSelectedItem()) {
                    case "关闭" -> {}
                    case "随机线条" -> ImageProcessor.createRandomLineObfuscation(bi);
                    case "点阵" -> ImageProcessor.createDotObfuscation(bi);
                    default -> main.logger.warn("找不到指定的混淆模式：" + cbObfuscation.getSelectedItem());
                }
            }

            int errors = 0;
            StringBuilder err = new StringBuilder();
            err.append("导出完成，但出现").append(errors).append("个错误：");
            for (int i = 0; i < proceed.size(); i++) {
                BufferedImage bi = proceed.get(i);
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
        export(ImageProcessor.splitSimple(source, count));
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
        export(ImageProcessor.splitManual(source, points));
    }

    private void selectWmFile(MouseEvent e) {
        main.logger.info("打开水印图像选择");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("选择水印图像");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(Application.INSTANCE.getRunningDir());
        chooser.setMultiSelectionEnabled(false);
        chooser.showOpenDialog(this);

        File sel = chooser.getSelectedFile();
        if (sel != null) {
            main.logger.info("已选择 " + sel.getAbsolutePath());

            try {
                watermark = ImageIO.read(sel);
                lbWmFile.setIcon(new ImageIcon(watermark));
            } catch (Throwable t) {
                main.logger.warn("水印图像读取失败", t);
                JOptionPane.showMessageDialog(this, "水印图像读取失败：\n" + t);
            }
        } else {
            main.logger.info("未选择水印图像");
        }
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
        pnWatermark = new JPanel();
        lbWatermark = new JLabel();
        btnSelectWMFile = new JButton();
        lbWmFile = new JLabel();
        pnObfuscation = new JPanel();
        lbObfuscation = new JLabel();
        cbObfuscation = new JComboBox<>();

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

            //======== pnWatermark ========
            {
                pnWatermark.setLayout(new GridLayout(1, 3));

                //---- lbWatermark ----
                lbWatermark.setText("\u6dfb\u52a0\u6c34\u5370\uff0c\u7559\u7a7a\u4e0d\u4f7f\u7528");
                pnWatermark.add(lbWatermark);

                //---- btnSelectWMFile ----
                btnSelectWMFile.setText("\u9009\u62e9\u56fe\u50cf");
                btnSelectWMFile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        selectWmFile(e);
                    }
                });
                pnWatermark.add(btnSelectWMFile);
                pnWatermark.add(lbWmFile);
            }
            pnExportProperty.add(pnWatermark);

            //======== pnObfuscation ========
            {
                pnObfuscation.setLayout(new GridLayout(1, 2));

                //---- lbObfuscation ----
                lbObfuscation.setText("\u56fe\u50cf\u6df7\u6dc6");
                pnObfuscation.add(lbObfuscation);
                pnObfuscation.add(cbObfuscation);
            }
            pnExportProperty.add(pnObfuscation);
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
    private JPanel pnWatermark;
    private JLabel lbWatermark;
    private JButton btnSelectWMFile;
    private JLabel lbWmFile;
    private JPanel pnObfuscation;
    private JLabel lbObfuscation;
    private JComboBox<String> cbObfuscation;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
