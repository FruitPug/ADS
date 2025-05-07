import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TraversalPanel extends JPanel {
    private final TraversalController controller;

    public TraversalPanel(TraversalController controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int bigCircleX = getWidth() / 2;
        final int bigCircleY = getHeight() / 2;

        final int bigCircleRadius = 200;
        final int smallCirclesRadius = 15;

        Graphics2D g2 = (Graphics2D) g;
        int[][] matrix = controller.getMatrix();
        int count = matrix.length;
        Point[] centers = new Point[count];

        for (int i = 0; i < count; i++) {
            double angle = 2 * Math.PI * i / count;
            int x = bigCircleX + (int) (bigCircleRadius * Math.cos(angle));
            int y = bigCircleY + (int) (bigCircleRadius * Math.sin(angle));
            centers[i] = new Point(x, y);
        }

        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (matrix[i][j] == 1) {
                    if (i == j)
                        drawSelfLoop(g2, centers[i]);
                    else
                        drawVector(g2, centers[i], centers[j]);
                }
            }
        }

        g2.setColor(Color.GREEN);
        for (Edge edge : controller.getHighlightedEdges()) {
            if (edge.from() == edge.to())
                drawSelfLoop(g2, centers[edge.from()]);
            drawVector(g2, centers[edge.from()], centers[edge.to()]);
        }

        Map<Integer, Color> nodeColors = controller.getNodeColors();
        Integer current = controller.getCurrentNode();

        for (int i = 0; i < centers.length; i++) {
            Point p = centers[i];

            Color color = nodeColors.getOrDefault(i, Color.LIGHT_GRAY);
            if (current != null && i == current)
                color = new Color(220, 0, 220);

            g2.setColor(color);
            g2.fillOval(p.x - smallCirclesRadius, p.y - smallCirclesRadius, smallCirclesRadius * 2, smallCirclesRadius * 2);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - smallCirclesRadius, p.y - smallCirclesRadius, smallCirclesRadius * 2, smallCirclesRadius * 2);
            g2.drawString(String.valueOf(i + 1), p.x - 5, p.y + 5);
        }

    }

    private void drawVector(Graphics2D g2, Point from, Point to) {
        final int radius = 15;

        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dist = Math.hypot(dx, dy);
        double unitX = dx / dist;
        double unitY = dy / dist;

        int startX = (int) (from.x + unitX * radius);
        int startY = (int) (from.y + unitY * radius);
        int endX = (int) (to.x - unitX * radius);
        int endY = (int) (to.y - unitY * radius);

        g2.drawLine(startX, startY, endX, endY);

        double angle = Math.atan2(dy, dx);
        drawArrow(g2, endX, endY, angle);
    }

    private void drawSelfLoop(Graphics2D g2, Point center) {
        int loopSize = 30;
        int offsetX = 7;
        int offsetY = 7;

        int x = center.x + offsetX;
        int y = center.y - offsetY;

        g2.drawArc(x, y, loopSize, loopSize, 200, 285);

        int xOffset = 8;
        int yOffset = 14;
        int arrowTilt = 230;

        int endX = center.x + xOffset;
        int endY = center.y + yOffset;

        double angle = Math.toRadians(arrowTilt);
        drawArrow(g2, endX, endY, angle);
    }

    private void drawArrow(Graphics2D g2, int endX, int endY, double angle) {
        int arrowLength = 10;

        for (int i = 0; i < 2; i++) {
            double theta = angle + (i == 0 ? Math.PI / 6 : -Math.PI / 6);
            int hx = (int) (endX - arrowLength * Math.cos(theta));
            int hy = (int) (endY - arrowLength * Math.sin(theta));
            g2.drawLine(endX, endY, hx, hy);
        }
    }
}
