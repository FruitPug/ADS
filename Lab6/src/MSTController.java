import java.awt.*;
import java.util.*;
import java.util.List;

public class MSTController {
    private final int[][] matrix;
    private final List<Edge> traversalTree;
    private final List<Edge> highlightedEdges;
    private final Queue<Runnable> steps;
    private final Map<Integer, Color> nodeColors;
    private Integer currentNode;

    public MSTController(int[][] matrix) {
        this.matrix = matrix;
        this.traversalTree = new ArrayList<>();
        this.highlightedEdges = new ArrayList<>();
        this.steps = new LinkedList<>();
        this.nodeColors = new HashMap<>();
    }

    public void startMST() {
        reset();
        mstPrepareSteps();
    }

    public void nextStep() {
        if (!steps.isEmpty()) {
            steps.poll().run();
        }
    }

    public List<Edge> getHighlightedEdges() {
        return highlightedEdges;
    }

    public void setHighlightedEdges(List<Edge> highlightedEdges) {
        this.highlightedEdges.addAll(highlightedEdges);
    }

    public Map<Integer, Color> getNodeColors() {
        return nodeColors;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Integer getCurrentNode() {
        return currentNode;
    }

    static void printMatrix(int[][] mstMatrix) {
        for (int[] row : mstMatrix) {
            for (int val : row) {
                if (val < 100) {
                    System.out.print(" ");
                }

                System.out.print(val + "  ");

                if (val < 10) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void reset() {
        traversalTree.clear();
        highlightedEdges.clear();
        steps.clear();
        nodeColors.clear();
        currentNode = null;
    }

    private void mstPrepareSteps() {
        int n = matrix.length;
        int[] minWeight = new int[n];
        int[] parent = new int[n];
        boolean[] inMST = new boolean[n];

        Arrays.fill(minWeight, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        minWeight[0] = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(i -> minWeight[i]));
        priorityQueue.add(0);

        while (!priorityQueue.isEmpty()) {
            int node = priorityQueue.poll();
            if (inMST[node]) continue;

            steps.add(() -> {
                currentNode = node;
                nodeColors.put(node, Color.GREEN);
            });

            inMST[node] = true;

            if (parent[node] != -1) {
                int from = parent[node];
                int weight = matrix[from][node];
                Edge edge = new Edge(from, node, weight);
                traversalTree.add(edge);
                addToStep(Collections.singletonList(edge));
            }

            for (int v = 0; v < n; v++) {
                if (matrix[node][v] > 0 && !inMST[v] && matrix[node][v] < minWeight[v]) {
                    minWeight[v] = matrix[node][v];
                    parent[v] = node;
                    priorityQueue.add(v);
                }
            }
        }

        steps.add(() -> currentNode = null);

        printMinimumSpanningTree();
    }

    private void addToStep(List<Edge> discoveredEdges) {
        List<Edge> edgesToHighlight = new ArrayList<>(discoveredEdges);

        if (!edgesToHighlight.isEmpty()) {
            steps.add(() -> highlightedEdges.addAll(edgesToHighlight));
        }
    }

    private void printMinimumSpanningTree() {
        int n = matrix.length;
        int[][] mstMatrix = new int[n][n];
        int totalWeight = 0;

        for (Edge edge : traversalTree) {
            int from = edge.from();
            int to = edge.to();
            int weight = edge.weight();
            mstMatrix[from][to] = weight;
            mstMatrix[to][from] = weight;
            totalWeight += weight;
        }

        System.out.println("\nMinimum Spanning Tree Matrix:");
        printMatrix(mstMatrix);
        System.out.println("Total weight of MST: " + totalWeight);
    }

}
