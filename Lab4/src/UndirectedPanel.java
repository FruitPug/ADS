import java.awt.*;

public class UndirectedPanel extends BaseVectorPlane {
    public UndirectedPanel(int[][] matrix) {
        super(matrix);
    }

    @Override
    protected void drawVectors(Graphics2D g2, Point[] centers) {
        g2.setColor(Color.RED);

        for (int i = 0; i < centers.length; i++) {
            for (int j = i; j < centers.length; j++) {
                if (matrix[i][j] == 1) {
                    Point to = centers[i];
                    Point from = centers[j];

                    if (i == j) {
                        drawSelfLoop(g2, to);
                    } else {
                        drawLine(g2, from, to);
                    }
                }
            }
        }
    }


}
