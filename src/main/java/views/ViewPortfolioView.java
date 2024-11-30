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
    public ViewPortfolioView(ViewPortfolioViewModel viewPortfolioModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.viewManagerModel = managerModel;
        this.viewName = viewPortfolioModel.getViewName();
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel totalValueSummary = new JLabel(String.format("%s: %.2f", ViewPortfolioViewModel.TOTAL_VALUE_LABEL,
                viewPortfolioModel.getState().getTotalValue()));
        JTable data = new JTable(viewPortfolioModel.getState().getPortfolioData(), ViewPortfolioViewModel.COLUMNS);
        JScrollPane dataScrollPane = new JScrollPane(data);
        JButton back = new JButton(ViewPortfolioViewModel.BACK_BUTTON_LABEL);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("homepage");
                viewManagerModel.firePropertyChange();
            }
        });
        totalValueSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(totalValueSummary);
        this.add(dataScrollPane);
        this.add(back);
    }
    public String getViewName(){
        return viewName;
    }
}
