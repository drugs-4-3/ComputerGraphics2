import java.awt.*;

public class OvalMark extends Mark {

    private int x, y, width, height;
    public final int CATEGORY = Mark.CATEGORY_OVAL;
    public static final Color TEMPORARY_COLOR = Color.BLUE;
    public static final Color DEFAULT_COLOR = Color.ORANGE;
    public static final int TEMPORARY_STROKE_THICKNESS = 5;
    public static final int DEFAULT_STROKE_THICKNESS = 8;

    public OvalMark(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = DEFAULT_COLOR;
        this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
    }

    public OvalMark(int x, int y, int width, int height, boolean isTemporary) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (isTemporary) {
            this.color = TEMPORARY_COLOR;
            this.stroke = new BasicStroke(TEMPORARY_STROKE_THICKNESS);
        } else {
            this.color = DEFAULT_COLOR;
            this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(this.color);
        g2d.setStroke(this.stroke);
        g.drawOval(x, y, width, height);
    }
}
