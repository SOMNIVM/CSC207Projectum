package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPortfolioView extends JPanel{
    private final String viewName;
    private final ViewManagerModel viewManagerModel;
    public ViewPortfolioView(ViewPortfolioViewModel viewPortfolioViewModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.viewManagerModel = managerModel;
        this.viewName = viewPortfolioViewModel.getViewName();
        JLabel title = new JLabel(viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel totalValueSummary = new JLabel(ViewPortfolioViewModel.TOTAL_VALUE_LABEL + ": "
                + viewPortfolioViewModel.getState().getTotalValue());
        JTable data = new JTable(viewPortfolioViewModel.getState().getPortfolioData(), ViewPortfolioViewModel.COLUMNS);
        JScrollPane dataScrollPane = new JScrollPane(data);
        JButton back = new JButton(ViewPortfolioViewModel.BACK_BUTTON_LABEL);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("homepage");
                viewManagerModel.firePropertyChange();
            }
        });
        this.add(title, BorderLayout.CENTER);
        this.add(totalValueSummary, BorderLayout.CENTER);
        this.add(dataScrollPane, BorderLayout.CENTER);
        this.add(back, BorderLayout.CENTER);
    }
    public String getViewName(){
        return viewName;
    }
}
