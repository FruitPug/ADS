import javax.swing.*;
import java.util.*;

public class Main {

    public static int[][] createDirectedMatrix(int length, int seed, double k) {
        Random rand = new Random(seed);

        int[][] matrix = new int[length][length];
        final int min = 0;
        final int max = 2;
        double randomDouble;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                randomDouble = rand.nextDouble(max - min) + min;

                if (randomDouble * k < 1)
                    matrix[i][j] = 0;
                else
                    matrix[i][j] = 1;
            }
        }

        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int numberOfVertices = 12;
        final int seed = 4421;
        final double k = 1 - 2 * 0.01 - 0.005 - 0.15;

        int[][] directMatrix = createDirectedMatrix(numberOfVertices, seed, k);

        System.out.println("Directional matrix:");
        printMatrix(directMatrix);

        SwingUtilities.invokeLater(() -> {
            TraversalFrame frame = new TraversalFrame(directMatrix);
            frame.setLocation(400, 100);
            frame.setVisible(true);
        });
    }
}
