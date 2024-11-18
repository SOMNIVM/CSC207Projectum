

package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BackTestView extends JPanel {
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final JButton runBacktestButton;
    private final JTextArea resultsArea;

    public BackTestView() {
        setLayout(new BorderLayout());
        
        // Input panel for dates
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField();
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField();
        inputPanel.add(endDateField);
        
        // Button panel
        runBacktestButton = new JButton("Run Backtest");
        
        // Results area
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        
        // Add components to main panel
        add(inputPanel, BorderLayout.NORTH);
        add(runBacktestButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    public void addBacktestListener(ActionListener listener) {
        runBacktestButton.addActionListener(listener);
    }

    public String getStartDate() {
        return startDateField.getText();
    }

    public String getEndDate() {
        return endDateField.getText();
    }

    public void displayResults(String results) {
        resultsArea.setText(results);
    }
}