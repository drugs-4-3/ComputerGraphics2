import java.awt.*;
import java.awt.geom.Ellipse2D;

public class OvalMark extends Mark {

    public static final Color TEMPORARY_COLOR = Color.BLUE;
    public static final Color DEFAULT_COLOR = Color.ORANGE;
    public static final int TEMPORARY_STROKE_THICKNESS = 5;
    public static final int DEFAULT_STROKE_THICKNESS = 8;

    public OvalMark(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.color = DEFAULT_COLOR;
        this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        this.CATEGORY = Mark.CATEGORY_OVAL;
    }

    public OvalMark(int x, int y, int width, int height, boolean isTemporary) {
        super(x, y, width, height);
        if (isTemporary) {
            this.color = TEMPORARY_COLOR;
            this.stroke = new BasicStroke(TEMPORARY_STROKE_THICKNESS);
        }
        else {
            this.color = DEFAULT_COLOR;
            this.stroke = new BasicStroke(DEFAULT_STROKE_THICKNESS);
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawOval(x, y, width, height);
    }

}
