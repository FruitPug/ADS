import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WeightedFrame extends JFrame {

    public WeightedFrame(int[][] matrix) {
        super("Weighted Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 525);

        MSTController controller = new MSTController(matrix);

        List<Edge> edges = new ArrayList<>();
        fillEdges(matrix, edges);
        controller.setHighlightedEdges(edges);

        MSTPanel graphPanel = new MSTPanel(controller, matrix);

        graphPanel.repaint();

        add(graphPanel, BorderLayout.CENTER);
    }

    private static void fillEdges(int[][] matrix, List<Edge> edgesToHighlight) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0) {
                Edge edge = new Edge(i, j, matrix[i][j]);
                edgesToHighlight.add(edge);
                }
            }
        }
    }
}
