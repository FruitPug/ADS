import javax.swing.*;
import java.awt.*;

public abstract class BaseVectorPlane extends JPanel {
    protected final int[][] matrix;

    public BaseVectorPlane(int[][] matrix) {
        this.matrix = matrix;
    }

    protected Point[] drawCircles(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        final int bigCircleX = getWidth() / 2;
        final int bigCircleY = getHeight() / 2;

        final int bigCircleRadius = 150;
        final int smallCirclesRadius = 20;

        final int radialScale = 360 / matrix.length;
        final int radialOffset = 60;
        int x, y;
        double angle;

        Point[] centers = new Point[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            angle = Math.toRadians(i * radialScale - radialOffset);
            x = (int) (bigCircleX + bigCircleRadius * Math.cos(angle)) - smallCirclesRadius;
            y = (int) (bigCircleY + bigCircleRadius * Math.sin(angle)) - smallCirclesRadius;
            g2.drawOval(x, y, smallCirclesRadius * 2, smallCirclesRadius * 2);
            g2.drawString(String.valueOf(i + 1), x + smallCirclesRadius - 5, y + smallCirclesRadius);
            centers[i] = new Point(x + smallCirclesRadius, y + smallCirclesRadius);
        }

        return centers;
    }

    protected void drawSelfLoop(Graphics2D g2, Point center) {
        final int loopSize = 40;
        final int offsetX = 10;
        final int offsetY = 10;

        int x = center.x + offsetX;
        int y = center.y - offsetY;

        g2.drawArc(x, y, loopSize, loopSize, 200, 285);
    }

    protected abstract void drawVectors(Graphics2D g2, Point[] centers);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Point[] centers = drawCircles(g2);
        drawVectors(g2, centers);
    }
}
