import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class BoardPanel extends JPanel {

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
    private Controller controller;
    private BufferedImage image;
    public int width;
    public int height;
    private int stroke_thickness = 20;
    private Stroke stroke;


    private int x1cords;
    private int y1cords;
    private int x2cords;
    private int y2cords;

    public BoardPanel(Controller controller) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, controller);
    }

    public BoardPanel(int width, int height, Controller controller) {
        super();
        this.controller = controller;
        this.width = width;
        this.height = height;
        addMouseListener(new BoardMouseListener());
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        stroke = new BasicStroke(10);
        prepareDrawing();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void prepareDrawing() {
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    public Stroke getStroke() {
        return stroke;
    }

    private class BoardMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            System.out.println("x: " + mouseEvent.getX() + "  y: " + mouseEvent.getY());
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            System.out.println("stroke: " + ((BasicStroke)g2d.getStroke()).getLineWidth());
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            x1cords = mouseEvent.getX();
            y1cords = mouseEvent.getY();
        }

        @Override()
        public void mouseReleased(MouseEvent mouseEvent) {
            x2cords = mouseEvent.getX();
            y2cords = mouseEvent.getY();
            controller.addMark(x1cords, y1cords, x2cords, y2cords);
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
