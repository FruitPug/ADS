import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TraversalFrame extends JFrame {
    private final TraversalController controller;
    private final TraversalPanel graphPanel;

    public TraversalFrame(int[][] matrix) {
        super("Graph Traversal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 525);

        this.controller = new TraversalController(matrix);
        this.graphPanel = new TraversalPanel(controller);

        JButton bfsBtn = new JButton("Start BFS");
        bfsBtn.addActionListener((ActionEvent e) -> {
            controller.startBFS();
            controller.nextStep();
            graphPanel.repaint();
        });

        JButton dfsBtn = new JButton("Start DFS");
        dfsBtn.addActionListener((ActionEvent e) -> {
            controller.startDFS();
            controller.nextStep();
            graphPanel.repaint();
        });

        JButton nextBtn = new JButton("Next Step");
        nextBtn.addActionListener((ActionEvent e) -> {
            controller.nextStep();
            graphPanel.repaint();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(bfsBtn);
        controlPanel.add(dfsBtn);
        controlPanel.add(nextBtn);

        setLayout(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
}
