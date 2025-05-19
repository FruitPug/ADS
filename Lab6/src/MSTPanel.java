import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MSTPanel extends JPanel {
    private final MSTController controller;
    private final int[][] matrix;

    public MSTPanel(MSTController controller, int[][] matrix) {
        this.controller = controller;
        this.matrix = matrix;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        Point[] centers = drawNodes(g2);
        drawConnections(g2, centers);
    }

    private Point[] drawNodes(Graphics2D g2) {
        final int bigCircleX = getWidth() / 2;
        final int bigCircleY = getHeight() / 2;
        final int bigCircleRadius = 200;
        final int smallCirclesRadius = 15;

        int count = matrix.length;
        Point[] centers = new Point[count];

        for (int i = 0; i < count; i++) {
            double angle = 2 * Math.PI * i / count;
            int x = bigCircleX + (int) (bigCircleRadius * Math.cos(angle));
            int y = bigCircleY + (int) (bigCircleRadius * Math.sin(angle));
            centers[i] = new Point(x, y);
        }

        drawNodeColors(centers, g2, smallCirclesRadius);

        return centers;
    }

    private void drawConnections(Graphics2D g2, Point[] centers) {
        g2.setColor(Color.GREEN);
        for (Edge edge : controller.getHighlightedEdges()) {
            if (edge.from() == edge.to())
                drawSelfLoop(g2, centers[edge.from()]);
            drawLine(g2, centers[edge.from()], centers[edge.to()], matrix[edge.from()][edge.to()]);

        }
    }

    private void drawLine(Graphics2D g2, Point from, Point to, int weight) {
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

        g2.setColor(Color.GREEN);
        g2.drawLine(startX, startY, endX, endY);

        // Draw the weight at the midpoint slightly offset
        int midX = (startX + endX) / 2;
        int midY = (startY + endY) / 2;
        g2.setColor(Color.BLUE);
        g2.drawString(String.valueOf(weight), midX + 5, midY - 5);
    }


    private void drawSelfLoop(Graphics2D g2, Point center) {
        int loopSize = 30;
        int offsetX = 7;
        int offsetY = 7;

        int x = center.x + offsetX;
        int y = center.y - offsetY;

        g2.setColor(Color.GREEN);
        g2.drawArc(x, y, loopSize, loopSize, 200, 285);
    }

    private void drawNodeColors(Point[] centers, Graphics2D g2, int smallCirclesRadius) {
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
}
