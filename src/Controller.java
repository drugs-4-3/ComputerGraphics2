import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Controller {

    private JFileChooser chooser;
    private BoardPanel boardPanel;
    private ToolsPanel toolsPanel;
    JPanel mainPanel;
    private Window window;
    private static double MIN_MARK_SIZE = 30;
    public LinkedList<Mark> markList;
    private String currentToolName;
    private Mark temporaryMark;

    public Controller() {
        chooser = new JFileChooser();
        boardPanel = new BoardPanel(this);
        toolsPanel = new ToolsPanel(this);
        markList = new LinkedList<>();
        currentToolName = "RECTANGLE";
        temporaryMark = null;
    }

    public void run() {
        window = new Window(this);
        mainPanel = new JPanel();
        mainPanel.add(boardPanel);
        mainPanel.add(toolsPanel);
        window.add(mainPanel);
        window.start();
    }

    public AbstractAction getSaveFileMenuHandler() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                chooser.setSelectedFile(new File("filename.png"));
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    saveImage(chooser.getSelectedFile());
                }
            }
        };
    }

    public AbstractAction getOpenFileMenuHandler() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES", "jpg", "jpeg", "png", "gif");
                chooser.setFileFilter(filter);
                int result = chooser.showOpenDialog(boardPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        loadImage(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Cannot load file",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                chooser.setFileFilter(null); // don't forget to disable only image filter for chooser
            }
        };
    }

    public void addMark(int x1, int y1, int x2, int y2) {
        if (dist(x1, y1, x2, y2) > MIN_MARK_SIZE) {
            int x, y, width, height;
            x = x1 < x2 ? x1 : x2;
            y = y1 < y2 ? y1 : y2;
            width = (int)dist(x1, 0, x2, 0);
            height = (int)dist(0, y1, 0, y2);
            switch(this.getCurrentTool()) {
                case "RECTANGLE":
                    markList.add(new RectangleMark(x, y, width, height));
                    break;
                case "OVAL":
                    markList.add(new OvalMark(x, y, width, height));
                    break;
            }

        }
    }

    private double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    private void saveImage(File file) {
        BufferedImage imageToSave = boardPanel.getImage();
        try {
            ImageIO.write(imageToSave, "PNG", file);
        } catch (IOException e) {
            // handle the exception
        }
    }

    private void loadImage(File file) throws IOException{
        BufferedImage image = ImageIO.read(file);
        boardPanel.setImage(image);
        drawMarks();
        boardPanel.repaint();
        window.pack();
    }

    public void drawMarks() {
        BufferedImage image = boardPanel.getImage();
        Graphics g = image.getGraphics();

        for (Mark m: markList) {
            System.out.println("painting mark");
            m.draw(g);
        }
        if (temporaryMark != null) {
            temporaryMark.draw(g);
        }
        boardPanel.revalidate();
    }

    public void setCurrentTool(String name) {
        this.currentToolName = name.toUpperCase();
    }

    public String getCurrentTool() {
        return this.currentToolName;
    }

    public void setTemporaryMark(int x1, int y1, int x2, int y2) {
        int x = x1 < x2 ? x1 : x2;
        int y = y1 < y2 ? y1 : y2;
        int width = (int)dist(x1, 0, x2, 0);
        int height = (int)dist(0, y1, 0, y2);

        switch(this.getCurrentTool()) {
            case "RECTANGLE":
                temporaryMark = new RectangleMark(x, y, width, height, true);
                break;
            case "OVAL":
                markList.add(new OvalMark(x, y, width, height));
                break;
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void setTemporaryMark(Mark m) {
        this.temporaryMark = m;
    }

    public Mark getTemporaryMark() {
        return this.temporaryMark;
    }

    public static BufferedImage deepCopyBufferedImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}