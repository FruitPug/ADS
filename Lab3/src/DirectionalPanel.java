import java.awt.*;

public class DirectionalPanel extends BaseVectorPlane {
    public DirectionalPanel(int[][] matrix) {
        super(matrix);
    }

    @Override
    protected void drawVectors(Graphics2D g2, Point[] centers) {
        g2.setColor(Color.BLUE);
        for (int i = 0; i < centers.length; i++) {
            for (int j = 0; j < centers.length; j++) {
                if (matrix[i][j] == 1) {
                    if (i == j) {
                        drawSelfLoop(g2, centers[i]);
                        drawLoopArrow(g2, centers[i]);
                    } else {
                        drawArrow(g2, centers[i], centers[j]);
                    }
                }
            }
        }
    }

    private void drawArrow(Graphics2D g2, Point from, Point to) {
        final int radius = 20;

        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dist = Math.hypot(dx, dy);
        double unitX = dx / dist;
        double unitY = dy / dist;

        int endX = (int) (to.x - unitX * radius);
        int endY = (int) (to.y - unitY * radius);

        int startX = (int) (from.x + unitX * radius);
        int startY = (int) (from.y + unitY * radius);

        g2.drawLine(startX, startY, endX, endY);

        double arrowAngle = Math.toRadians(25);
        int arrowLength = 10;

        for (int i = 0; i < 2; i++) {
            double theta = Math.atan2(dy, dx) + (i == 0 ? arrowAngle : -arrowAngle);
            int x = (int) (endX - arrowLength * Math.cos(theta));
            int y = (int) (endY - arrowLength * Math.sin(theta));
            g2.drawLine(endX, endY, x, y);
        }
    }

    private void drawLoopArrow(Graphics2D g2, Point center) {
        int xOffset = 11;
        int yOffset = 17;
        int arrowTilt = 230;

        int endX = center.x + xOffset;
        int endY = center.y + yOffset;

        double angle = Math.toRadians(arrowTilt);
        int arrowLength = 10;

        for (int i = 0; i < 2; i++) {
            double theta = angle + (i == 0 ? Math.PI / 6 : -Math.PI / 6);
            int hx = (int) (endX - arrowLength * Math.cos(theta));
            int hy = (int) (endY - arrowLength * Math.sin(theta));
            g2.drawLine(endX, endY, hx, hy);
        }
    }


}
