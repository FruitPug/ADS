import javax.swing.*;
import java.util.*;

public class Main {
    public static void graphicsSetUpAndDraw(int[][] directedMatrix, int[][] undirectedMatrix, int[][] secondDirectedMatrix, int[][] condMatrix) {
        final int windowWidth = 340;
        final int windowHeight = 340;

        JFrame frame1 = new JFrame("Directed Graph 1");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(windowWidth, windowHeight);
        frame1.add(new DirectedPanel(directedMatrix));
        frame1.setLocation(300, 0);
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("Undirected Graph 1");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(windowWidth, windowHeight);
        frame2.add(new UndirectedPanel(undirectedMatrix));
        frame2.setLocation(700, 0);
        frame2.setVisible(true);

        JFrame frame3= new JFrame("Directed Graph 2");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setSize(windowWidth, windowHeight);
        frame3.add(new DirectedPanel(secondDirectedMatrix));
        frame3.setLocation(300, 340);
        frame3.setVisible(true);

        JFrame frame4= new JFrame("Condensation Graph");
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.setSize(windowWidth, windowHeight);
        frame4.add(new DirectedPanel(condMatrix));
        frame4.setLocation(700, 340);
        frame4.setVisible(true);
    }

    public static int[][] createDirectedMatrix(int length, int seed, double k) {
        Random rand = new Random(seed);

        int[][] matrix = new int[length][length];
        final int min = 0;
        final int max = 2;
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

    public static int[][] transformToUndirectedMatrix(int[][] directMatrix) {
        final int length = directMatrix.length;
        int[][] nonDirectMatrix = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                nonDirectMatrix[i][j] = (directMatrix[i][j] == 1 || directMatrix[j][i] == 1) ? 1 : 0;
            }
        }

        return nonDirectMatrix;
    }

    public static void analyzeGraphs(int[][] directMatrix, int[][] nonDirectMatrix) {
        System.out.println("\n--- Analysis ---");

        int length = directMatrix.length;

        System.out.println("Directed Graph:");
        int[] inDegrees = new int[length];
        int[] outDegrees = new int[length];

        countDegrees(directMatrix, length, inDegrees, outDegrees);

        boolean isDirectedHomogeneous = true;
        int expectedOut = outDegrees[0];
        int expectedIn = inDegrees[0];
        for (int i = 0; i < length; i++) {
            System.out.printf("Vertex %d: In-Degree = %d, Out-Degree = %d%n", i + 1, inDegrees[i], outDegrees[i]);
            if (inDegrees[i] != expectedIn || outDegrees[i] != expectedOut) {
                isDirectedHomogeneous = false;
            }
        }
        if (isDirectedHomogeneous) {
            System.out.printf("Graph is homogeneous (regular) with in-degree %d and out-degree %d.%n", expectedIn, expectedOut);
        } else {
            System.out.println("Graph is not homogeneous.");
        }

        System.out.println("\nUndirected Graph:");
        int[] degrees = new int[length];
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (nonDirectMatrix[i][j] == 1) {
                    if (i == j) degrees[i] += 2;
                    else {
                        degrees[i]++;
                        degrees[j]++;
                    }
                }
            }
        }

        boolean isUndirectedHomogeneous = true;
        int expectedDegree = degrees[0];
        for (int i = 0; i < length; i++) {
            System.out.printf("Vertex %d: Degree = %d%n", i + 1, degrees[i]);
            if (degrees[i] != expectedDegree) {
                isUndirectedHomogeneous = false;
            }
        }
        if (isUndirectedHomogeneous) {
            System.out.printf("Graph is homogeneous (regular) with degree %d.%n", expectedDegree);
        } else {
            System.out.println("Graph is not homogeneous.");
        }

        System.out.println("\nIsolated and Hanging Vertices (Undirected):");
        for (int i = 0; i < length; i++) {
            if (degrees[i] == 0) {
                System.out.printf("Vertex %d is isolated.%n", i + 1);
            } else if (degrees[i] == 1) {
                System.out.printf("Vertex %d is hanging.%n", i + 1);
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + "  ");
            }
            System.out.println();
        }
    }

    private static void countDegrees(int[][] matrix, int n, int[] inDeg, int[] outDeg) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    outDeg[i]++;
                    inDeg[j]++;
                }
            }
        }
    }

    public static void printPathsOfLength(int[][] matrix, int length) {
        int[][] result = matrixPower(matrix, length);
        int n = matrix.length;
        System.out.printf("\n--- Paths of Length %d ---\n", length);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (result[i][j] > 0) {
                    System.out.printf("Paths %d -> %d (%d path(s)):\n", i + 1, j + 1, result[i][j]);
                    printPathsUtil(matrix, i, j, length, new ArrayList<>(), new boolean[n]);
                }
            }
        }
    }

    private static void printPathsUtil(int[][] matrix, int u, int dest, int length, List<Integer> path, boolean[] visited) {
        path.add(u);
        if (length == 0) {
            if (u == dest) {
                for (int v : path) {
                    System.out.print((v + 1) + " ");
                }
                System.out.println();
            }
            path.remove(path.size() - 1);
            return;
        }

        visited[u] = true;
        for (int v = 0; v < matrix.length; v++) {
            if (matrix[u][v] == 1) {
                printPathsUtil(matrix, v, dest, length - 1, path, visited);
            }
        }
        visited[u] = false;
        path.remove(path.size() - 1);
    }

    public static int[][] matrixPower(int[][] matrix, int power) {
        int n = matrix.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            result[i][i] = 1;  // Identity matrix
        }

        for (int p = 0; p < power; p++) {
            result = multiplyMatrices(result, matrix);
        }

        return result;
    }

    private static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] product = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    product[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return product;
    }

    private static int[][] copyMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    public static int[][] reachabilityMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] reach = copyMatrix(matrix);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reach[i][j] = reach[i][j] | (reach[i][k] & reach[k][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) reach[i][i] = 1;

        System.out.println("\n--- Reachability Matrix ---");
        printMatrix(reach);
        return reach;
    }

    public static int[][] strongConnectivityMatrix(int[][] reach) {
        int n = reach.length;
        int[][] strong = new int[n][n];

        System.out.println("\n--- Strong Connectivity Matrix ---");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                strong[i][j] = (reach[i][j] == 1 && reach[j][i] == 1) ? 1 : 0;
            }
        }

        printMatrix(strong);
        return strong;
    }

    public static List<List<Integer>> findStronglyConnectedComponents(int[][] strong) {
        int n = strong.length;
        boolean[] visited = new boolean[n];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (strong[i][j] == 1 && strong[j][i] == 1) {
                        visited[j] = true;
                        component.add(j + 1);
                    }
                }
                components.add(component);
            }
        }

        System.out.println("\n--- Strongly Connected Components ---");
        for (int i = 0; i < components.size(); i++) {
            System.out.printf("Component %d: %s%n", i + 1, components.get(i));
        }

        return components;
    }

    public static int[][] buildCondensationGraph(int[][] matrix, List<List<Integer>> components) {
        int n = matrix.length;
        int compCount = components.size();
        int[][] condMat = new int[compCount][compCount];

        int[] compMap = new int[n];
        for (int i = 0; i < components.size(); i++) {
            for (int v : components.get(i)) {
                compMap[v - 1] = i;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    int ci = compMap[i];
                    int cj = compMap[j];
                    if (ci != cj) {
                        condMat[ci][cj] = 1;
                    }
                }
            }
        }

        return condMat;
    }

    private static int[][] analyzeK2Matrix(int[][] k2Matrix) {
        analyzeGraphs(k2Matrix, transformToUndirectedMatrix(k2Matrix));

        printPathsOfLength(k2Matrix, 2);
        printPathsOfLength(k2Matrix, 3);

        int[][] reach = reachabilityMatrix(k2Matrix);
        int[][] strong = strongConnectivityMatrix(reach);
        List<List<Integer>> components = findStronglyConnectedComponents(strong);

        return buildCondensationGraph(k2Matrix, components);
    }

    public static void main(String[] args) {
        int numberOfVertices = 12;
        final int seed = 4421;
        final double k1 = 1 - 2 * 0.01 - 0.01 - 0.3;
        final double k2 = 1 - 2 * 0.005 - 0.005 - 0.27;
        //final double k2 = 0.57;

        int[][] directMatrix = createDirectedMatrix(numberOfVertices, seed, k1);
        int[][] nonDirectMatrix = transformToUndirectedMatrix(directMatrix);

        System.out.println("Directional matrix:");
        printMatrix(directMatrix);

        System.out.println("\nNon-directional matrix:");
        printMatrix(nonDirectMatrix);

        analyzeGraphs(directMatrix, nonDirectMatrix);

        int[][] k2Matrix = createDirectedMatrix(numberOfVertices, seed, k2);

        System.out.println("\n--- New Directed Matrix (k2) ---");
        printMatrix(k2Matrix);

        int[][] condensationMatrix = analyzeK2Matrix(k2Matrix);
        System.out.println("\n--- Condensation Graph Matrix ---");
        printMatrix(condensationMatrix);

        graphicsSetUpAndDraw(directMatrix, nonDirectMatrix, k2Matrix, condensationMatrix);
    }
}
