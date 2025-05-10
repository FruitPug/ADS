import javax.swing.*;
import java.util.*;

public class Main {

    public static int[][] createUndirectedMatrix(int length, int seed, double k) {
        Random rand = new Random(seed);
        int[][] directedMatrix = new int[length][length];
        int[][] undirectedMatrix = new int[length][length];
        double randomDouble;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                randomDouble = rand.nextDouble() * 2.0;

                if (randomDouble * k < 1) {
                    directedMatrix[i][j] = 0;
                } else {
                    directedMatrix[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                undirectedMatrix[i][j] = (directedMatrix[i][j] == 1 || directedMatrix[j][i] == 1) ? 1 : 0;
            }
        }

        return undirectedMatrix;
    }

    public static int[][] createWeightedUndirectedGraph(int[][] undirectedMatrix, int seed) {
        final int length = undirectedMatrix.length;
        double[][] B = new double[length][length];
        int[][] C = new int[length][length];
        int[][] D = new int[length][length];
        int[][] H = new int[length][length];
        int[][] Tr = new int[length][length];
        int[][] W = new int[length][length];

        Random rand = new Random(seed);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                B[i][j] = rand.nextDouble() * 2.0;
                C[i][j] = (int) Math.ceil(B[i][j] * 100 * undirectedMatrix[i][j]);
                D[i][j] = C[i][j] > 0 ? 1 : 0;
                H[i][j] = D[i][j] != D[j][i] ? 1 : 0;
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                Tr[i][j] = 1;
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int w = (D[i][j] + H[i][j] * Tr[i][j]) * C[i][j];
                W[i][j] = w;
                W[j][i] = w;
            }
        }

        return W;
    }


    public static void printMatrix(int[][] matrix) {
        MSTController.printMatrix(matrix);
    }

    public static void main(String[] args) {
        int numberOfVertices = 12;
        final int seed = 4421;
        final double k = 1 - 2 * 0.01 - 0.005 - 0.05;

        int[][] undirectedMatrix = createUndirectedMatrix(numberOfVertices, seed, k);
        int[][] weightedUndirectedMatrix = createWeightedUndirectedGraph(undirectedMatrix, seed);

        System.out.println("Undirected matrix:");
        printMatrix(undirectedMatrix);

        System.out.println("\nWeighted undirected matrix:");
        printMatrix(weightedUndirectedMatrix);

        SwingUtilities.invokeLater(() -> {
            WeightedFrame frame1 = new WeightedFrame(weightedUndirectedMatrix);
            frame1.setLocation(150, 100);
            frame1.setVisible(true);

            MSTFrame frame2 = new MSTFrame(weightedUndirectedMatrix);
            frame2.setLocation(650, 100);
            frame2.setVisible(true);
        });
    }
}
