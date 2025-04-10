import java.awt.*;

public class NonDirectionalPanel extends BaseVectorPlane {
    public NonDirectionalPanel(int[][] matrix) {
        super(matrix);
    }

    @Override
    protected void drawVectors(Graphics2D g2, Point[] centers) {
        g2.setColor(Color.RED);
        final int radius = 20;

        for (int i = 0; i < centers.length; i++) {
            for (int j = i; j < centers.length; j++) {
                if (matrix[i][j] == 1) {
                    Point p1 = centers[i];
                    Point p2 = centers[j];

                    if (i == j) {
                        drawSelfLoop(g2, p1);
                    } else {
                        double dx = p2.x - p1.x;
                        double dy = p2.y - p1.y;
                        double dist = Math.hypot(dx, dy);
                        double ux = dx / dist;
                        double uy = dy / dist;

                        int x1 = (int) (p1.x + ux * radius);
                        int y1 = (int) (p1.y + uy * radius);
                        int x2 = (int) (p2.x - ux * radius);
                        int y2 = (int) (p2.y - uy * radius);

                        g2.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        }
    }


}
