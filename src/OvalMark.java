import java.awt.*;

public class OvalMark extends Mark {

    private int x, y, width, height;
    public final int CATEGORY = Mark.CATEGORY_OVAL;

    public OvalMark(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
        this.stroke = new BasicStroke(8);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.color);
        g2d.setStroke(this.stroke);
        g.drawOval(x, y, width, height);
    }
}
