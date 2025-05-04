import java.awt.*;

public class DirectedPanel extends BaseVectorPlane {
    public DirectedPanel(int[][] matrix) {
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
        Line line = drawLine(g2, from, to);

        double arrowAngle = Math.toRadians(25);
        int arrowLength = 10;

        for (int i = 0; i < 2; i++) {
            double theta = Math.atan2(line.dy(), line.dx()) + (i == 0 ? arrowAngle : -arrowAngle);
            int x = (int) (line.endX() - arrowLength * Math.cos(theta));
            int y = (int) (line.endY() - arrowLength * Math.sin(theta));
            g2.drawLine(line.endX(), line.endY(), x, y);
        }
    }


    private void drawLoopArrow(Graphics2D g2, Point center) {
        int xOffset = 8;
        int yOffset = 14;
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
