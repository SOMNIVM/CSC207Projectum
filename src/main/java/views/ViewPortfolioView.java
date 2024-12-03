package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_portfolio.ViewPortfolioController;
import interface_adapters.view_portfolio.ViewPortfolioState;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewPortfolioView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final ViewPortfolioViewModel viewPortfolioViewModel;
    private ViewPortfolioController viewPortfolioController;
    private final JLabel totalValueSummary;
    private final JScrollPane dataScrollPane;
    public ViewPortfolioView(ViewPortfolioViewModel viewPortfolioModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.viewPortfolioViewModel = viewPortfolioModel;
        this.viewName = this.viewPortfolioViewModel.getViewName();
        this.viewPortfolioViewModel.addPropertyChangeListener(this);
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.totalValueSummary = new JLabel();
        this.dataScrollPane = new JScrollPane();
        JButton back = new JButton(ViewPortfolioViewModel.BACK_BUTTON_LABEL);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewPortfolioController.switchBack();
            }
        });
        totalValueSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataScrollPane.setPreferredSize(new Dimension(1200, 300));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(this.totalValueSummary);
        this.add(this.dataScrollPane);
        this.add(back);
    }
    public String getViewName(){
        return viewName;
    }
    public void setViewPortfolioController(ViewPortfolioController controller) {
        viewPortfolioController = controller;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("data")) {
            ViewPortfolioState newState = (ViewPortfolioState) evt.getNewValue();
            totalValueSummary.setText(String.format(
                    "%s: %.2f",
                    ViewPortfolioViewModel.TOTAL_VALUE_LABEL,
                    newState.getTotalValue()));
            dataScrollPane.setViewportView(new JTable(newState.getPortfolioData(), ViewPortfolioViewModel.COLUMNS));
        }
    }
}
