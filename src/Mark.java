import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class Mark implements Serializable {

    public static final int CATEGORY_RECT = 1;
    public static final int CATEGORY_OVAL = 2;
    public static final int CATEGORY_CIRCLE = 3;
    protected static final int DEFAULT_STROKE_THICKNESS = 8;
    protected static final int TEMPORARY_STROKE_THICKNESS = 4;
    public static final Stroke SELECTED_STROKE = new BasicStroke(9);
    public static final Color SELECTED_COLOR = Color.YELLOW;
    protected int CATEGORY;
    protected Shape bigger;
    protected Shape smaller;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Stroke stroke;
    protected Color color;
    protected boolean isSelected = false;

    public Mark(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isSelected) {
            g.setColor(Mark.SELECTED_COLOR);
            g2d.setStroke(Mark.SELECTED_STROKE);
        } else {
            g.setColor(this.color);
            g2d.setStroke(this.stroke);
        }
    }

    public boolean isPointInsideBorder(int x, int y) {
        return getBiggerShape().contains(x, y) && !getSmallerShape().contains(x, y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Stroke getStroke() {
        return this.stroke;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSelected(boolean val) {
        this.isSelected = val;
    }

    public boolean isSelected() {
        return this.isSelected;
    }


    protected Shape getSmallerShape() {
        if (smaller == null) {
            switch(CATEGORY) {
                case CATEGORY_RECT:
                    smaller =  new Rectangle2D.Double(
                            this.x + DEFAULT_STROKE_THICKNESS/2,
                            this.y + DEFAULT_STROKE_THICKNESS/2,
                            this.width - DEFAULT_STROKE_THICKNESS,
                            this.height - DEFAULT_STROKE_THICKNESS);
                    break;

                case CATEGORY_OVAL:
                    smaller =  new Ellipse2D.Double(
                            this.x + DEFAULT_STROKE_THICKNESS/2,
                            this.y + DEFAULT_STROKE_THICKNESS/2,
                            this.width - DEFAULT_STROKE_THICKNESS,
                            this.height - DEFAULT_STROKE_THICKNESS);
                    break;

                default:
                    break;
            }
        }
        return smaller;
    }

    protected Shape getBiggerShape() {
        if (this.bigger == null) {
            switch(this.CATEGORY) {
                case CATEGORY_RECT:
                    bigger =  new Rectangle2D.Double(
                            this.x - DEFAULT_STROKE_THICKNESS/2,
                            this.y - DEFAULT_STROKE_THICKNESS/2,
                            this.width + DEFAULT_STROKE_THICKNESS,
                            this.height + DEFAULT_STROKE_THICKNESS);
                    break;

                case CATEGORY_OVAL:
                    bigger =  new Ellipse2D.Double(
                            this.x - DEFAULT_STROKE_THICKNESS/2,
                            this.y - DEFAULT_STROKE_THICKNESS/2,
                            this.width + DEFAULT_STROKE_THICKNESS,
                            this.height + DEFAULT_STROKE_THICKNESS);
                    break;

                default:
                    break;
            }
        }
        return bigger;
    }

    @Override
    public String toString() {
        switch(CATEGORY) {
            case CATEGORY_RECT:
                return String.format("Rectangle: (%d, %d, %d, %d)", x, y, x+width, y+height);
            case CATEGORY_OVAL:
                return String.format("Oval: (%d, %d, %d, %d)", x, y, x+width, y+height);
            default:
                break;
        }
        return "";
    }
}
