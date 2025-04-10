import javax.swing.*;
import java.util.Random;

public class Main {
    public static void graphicsSetUpAndDraw(int[][] directMatrix, int[][] nonDirectMatrix) {
        final int windowWidth = 450;
        final int windowHeight = 450;

        JFrame frame1 = new JFrame("Directional Graph");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(windowWidth, windowHeight);
        frame1.add(new DirectionalPanel(directMatrix));
        frame1.setLocation(100, 100);
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("Non-Directional Graph");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(windowWidth, windowHeight);
        frame2.add(new NonDirectionalPanel(nonDirectMatrix));
        frame2.setLocation(550, 100);
        frame2.setVisible(true);
    }

    public static int[][] createDirectMatrix(int length) {
        final int seed = 4421;
        Random rand = new Random(seed);

        int[][] matrix = new int[length][length];
        final int min = 0;
        final int max = 2;
        final double k = 1 - 2 * 0.02 - 1 * 0.005 - 0.25;
        double randomDouble;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                randomDouble = rand.nextDouble(max - min) + min;

                if (randomDouble * k < 1) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }

        return matrix;
    }

    public static int[][] transformToNonDirectMatrix (int[][] directMatrix) {
        final int length = directMatrix.length;
        int [][] nonDirectMatrix = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                nonDirectMatrix[i][j] = (directMatrix[i][j] == 1 || directMatrix[j][i] == 1) ? 1 : 0;
            }
        }

        return nonDirectMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int numberOfVertices = 12;
        int[][] directMatrix = createDirectMatrix(numberOfVertices);
        int[][] nonDirectMatrix = transformToNonDirectMatrix(directMatrix);

        System.out.println("Directional matrix:");
        printMatrix(directMatrix);

        System.out.println("\nNon-directional matrix:");
        printMatrix(nonDirectMatrix);

        graphicsSetUpAndDraw(directMatrix, nonDirectMatrix);
    }
}