import java.awt.*;
import java.util.*;
import java.util.List;

public class TraversalController {
    private final int[][] matrix;
    private final boolean[] visited;
    private final List<Edge> traversalTree;
    private final List<Edge> highlightedEdges;
    private final Queue<Runnable> steps;
    private final Map<Integer, Color> nodeColors;
    private final List<Integer> traversalOrder;
    private Integer currentNode;


    public TraversalController(int[][] matrix) {
        this.matrix = matrix;
        this.visited = new boolean[matrix.length];
        this.traversalTree = new ArrayList<>();
        this.highlightedEdges = new ArrayList<>();
        this.steps = new LinkedList<>();
        this.nodeColors = new HashMap<>();
        this.traversalOrder = new ArrayList<>();
    }

    public void startBFS() {
        reset();
        bfsPrepareSteps();
    }

    public void startDFS() {
        reset();
        dfsPrepareSteps();
    }

    public void nextStep() {
        if (!steps.isEmpty()) {
            steps.poll().run();
        }
    }

    public List<Edge> getHighlightedEdges() {
        return highlightedEdges;
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

    private void reset() {
        Arrays.fill(visited, false);
        traversalTree.clear();
        highlightedEdges.clear();
        steps.clear();
        nodeColors.clear();
        traversalOrder.clear();
    }

    private void bfsPrepareSteps() {
        Queue<Integer> queue = new LinkedList<>();
        boolean firstNode = true;

        for (int start = 0; start < matrix.length; start++) {
            if (hasOutgoing(start) && !visited[start]) {
                queue.add(start);
                visited[start] = true;

                while (!queue.isEmpty()) {
                    int node = queue.poll();

                    prepareAndJump(node, firstNode);
                    firstNode = false;

                    List<Integer> discoveredNodes = new ArrayList<>();
                    List<Edge> discoveredEdges = new ArrayList<>();

                    for (int neighbor = 0; neighbor < matrix.length; neighbor++) {
                        if (matrix[node][neighbor] == 1 && !visited[neighbor]) {
                            Edge edge = new Edge(node, neighbor);

                            traversalTree.add(edge);
                            discoveredEdges.add(edge);
                            discoveredNodes.add(neighbor);

                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }

                    addToStep(discoveredNodes, discoveredEdges);
                }
            }
        }
        steps.add(() -> currentNode = null);

        printTraversalTreeMatrixAndMapping();
    }

    private void dfsPrepareSteps() {
        Stack<Integer> stack = new Stack<>();
        boolean firstNode = true;

        for (int start = 0; start < matrix.length; start++) {
            if (hasOutgoing(start) && !visited[start]) {
                stack.push(start);
                visited[start] = true;

                while (!stack.isEmpty()) {
                    int node = stack.pop();

                    prepareAndJump(node, firstNode);
                    firstNode = false;

                    List<Integer> discoveredNodes = new ArrayList<>();
                    List<Edge> discoveredEdges = new ArrayList<>();

                    for (int neighbor = matrix.length - 1; neighbor >= 0; neighbor--) {
                        if (matrix[node][neighbor] == 1 && !visited[neighbor]) {
                            Edge edge = new Edge(node, neighbor);

                            discoveredEdges.add(edge);
                            traversalTree.add(edge);
                            discoveredNodes.add(neighbor);

                            visited[neighbor] = true;
                            stack.push(neighbor);
                        }
                    }

                    addToStep(discoveredNodes, discoveredEdges);
                }
            }
        }

        steps.add(() -> currentNode = null);

        printTraversalTreeMatrixAndMapping();
    }

    private void addToStep(List<Integer> discoveredNodes, List<Edge> discoveredEdges) {
        List<Integer> nodesToColor = new ArrayList<>(discoveredNodes);
        List<Edge> edgesToHighlight = new ArrayList<>(discoveredEdges);

        if (!edgesToHighlight.isEmpty()) {
            steps.add(() -> {
                for (int i : nodesToColor)
                    nodeColors.put(i, Color.YELLOW);
                highlightedEdges.addAll(edgesToHighlight);
            });
        }
    }

    private void prepareAndJump(int node, boolean firstNode) {
        if (!firstNode)
            steps.add(() -> nodeColors.put(node, Color.CYAN));

        if (!traversalOrder.contains(node)) {
            traversalOrder.add(node);
        }

        steps.add(() -> {
            currentNode = node;
            nodeColors.put(node, Color.GREEN);
        });
    }


    private boolean hasOutgoing(int node) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[node][i] == 1)
                return true;
        }
        return false;
    }

    private void printTraversalTreeMatrixAndMapping() {
        int n = traversalOrder.size();
        int[][] treeMatrix = new int[n][n];

        Map<Integer, Integer> newIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            newIndex.put(traversalOrder.get(i), i);
        }

        for (Edge edge : traversalTree) {
            Integer fromIndex = newIndex.get(edge.from());
            Integer toIndex = newIndex.get(edge.to());

            if (fromIndex != null && toIndex != null) {
                treeMatrix[fromIndex][toIndex] = 1;
            }
        }

        System.out.println("\nTraversal Tree Adjacency Matrix:");
        for (int[] row : treeMatrix) {
            for (int val : row) {
                System.out.print(val + "  ");
            }
            System.out.println();
        }

        // +1 for node alignment
        System.out.println("\nNode Mapping:");
        for (int i = 0; i < n; i++) {
            System.out.println("№" + (i + 1) + " - " + (traversalOrder.get(i) + 1));
        }
    }

}
