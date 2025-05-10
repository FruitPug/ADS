import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MSTFrame extends JFrame {
    private final MSTController controller;
    private final MSTPanel graphPanel;

    public MSTFrame(int[][] matrix) {
        super("Minimum Spanning Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 525);

        this.controller = new MSTController(matrix);
        this.graphPanel = new MSTPanel(controller, controller.getMatrix());

        JPanel controlPanel = getjPanel();

        add(graphPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JPanel getjPanel() {
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener((ActionEvent e) -> {
            controller.startMST();
            controller.nextStep();
            graphPanel.repaint();
        });

        JButton nextBtn = new JButton("Next Step");
        nextBtn.addActionListener((ActionEvent e) -> {
            controller.nextStep();
            graphPanel.repaint();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(startBtn);
        controlPanel.add(nextBtn);
        return controlPanel;
    }
}
