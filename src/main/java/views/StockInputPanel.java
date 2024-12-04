package views;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONObject;

import app.Config;

/**
 * A JPanel for choosing stocks that combines JTextField and JComboBox functionalities.
 */
public class StockInputPanel extends JPanel {
    private static final int TEXTFIELD_COL = 30;
    private static final int ROWCOUNT = 6;
    private final JTextField prefixField;
    private final JComboBox<String> stockInputBox;
    private final DefaultComboBoxModel<String> model;
    private final Map<String, String> nameToOption;
    private final Map<String, String> symbolToOption;

    public StockInputPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.prefixField = new JTextField(TEXTFIELD_COL);
        this.prefixField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nameToOption = new HashMap<>();
        this.symbolToOption = new HashMap<>();
        for (Object obj: Config.STOCK_LIST) {
            final JSONObject dataObj = (JSONObject) obj;
            final String name = dataObj.getString("name");
            final String symbol = dataObj.getString("symbol");
            final String option = String.format("%s (%s)", name, symbol);
            this.nameToOption.put(name.toLowerCase(), option);
            this.symbolToOption.put(symbol.toLowerCase(), option);
        }
        final String[] options = this.symbolToOption.values().toArray(new String[0]);
        Arrays.sort(options);
        this.model = new DefaultComboBoxModel<>(options);
        this.stockInputBox = new JComboBox<>(this.model);
        this.stockInputBox.setMaximumRowCount(ROWCOUNT);
        this.stockInputBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.prefixField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                model.removeAllElements();
                model.addAll(getNewOptions(prefixField.getText()));
                if (stockInputBox.isShowing()) {
                    stockInputBox.showPopup();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                model.removeAllElements();
                model.addAll(getNewOptions(prefixField.getText()));
                if (stockInputBox.isShowing()) {
                    stockInputBox.showPopup();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                model.removeAllElements();
                model.addAll(getNewOptions(prefixField.getText()));
                if (stockInputBox.isShowing()) {
                    stockInputBox.showPopup();
                }
            }
        });
        this.add(this.prefixField);
        this.add(this.stockInputBox);
    }

    /**
     * Get the chosen stock as the String-valued stock name.
     * @return the stock name.
     */
    public String getText() {
        final String option = (String) stockInputBox.getSelectedItem();
        int idx = option.length() - 1;
        int unmatched = 0;
        while (idx >= 0) {
            if (option.charAt(idx) == ')') {
                unmatched += 1;
            }
            else if (option.charAt(idx) == '(') {
                unmatched -= 1;
            }
            if (unmatched == 0) {
                break;
            }
            idx -= 1;
        }
        return option.substring(0, idx - 1);
    }

    private List<String> getNewOptions(String prefix) {
        final String lowerCasePrefix = prefix.toLowerCase();
        final Set<String> optionSet = new HashSet<>();
        for (String name: nameToOption.keySet()) {
            if (name.startsWith(lowerCasePrefix)) {
                optionSet.add(nameToOption.get(name));
            }
        }
        for (String symbol: symbolToOption.keySet()) {
            if (symbol.startsWith(lowerCasePrefix)) {
                optionSet.add(symbolToOption.get(symbol));
            }
        }
        final List<String> optionList = new ArrayList<>(optionSet);
        Collections.sort(optionList);
        return optionList;
    }
}
