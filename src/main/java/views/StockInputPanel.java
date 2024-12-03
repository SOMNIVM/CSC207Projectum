package views;

import app.Config;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StockInputPanel extends JPanel {
    private final JTextField prefixField;
    private final JComboBox<String> stockInputBox;
    private final DefaultComboBoxModel<String> model;
    private final Map<String, String> nameToOption;
    private final Map<String, String> symbolToOption;
    public StockInputPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.prefixField = new JTextField(30);
        this.prefixField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nameToOption = new HashMap<>();
        this.symbolToOption = new HashMap<>();
        for (Object obj: Config.STOCK_LIST) {
            JSONObject dataObj = (JSONObject) obj;
            String name = dataObj.getString("name");
            String symbol = dataObj.getString("symbol");
            String option = String.format("%s (%s)", name, symbol);
            this.nameToOption.put(name.toLowerCase(), option);
            this.symbolToOption.put(symbol.toLowerCase(), option);
        }
        String[] options = this.symbolToOption.values().toArray(new String[0]);
        Arrays.sort(options);
        this.model = new DefaultComboBoxModel<>(options);
        this.stockInputBox = new JComboBox<>(this.model);
        this.stockInputBox.setMaximumRowCount(6);
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

    public String getText() {
        String option = (String) stockInputBox.getSelectedItem();
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
        String lowerCasePrefix = prefix.toLowerCase();
        Set<String> optionSet = new HashSet<>();
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
        List<String> optionList = new ArrayList<>(optionSet);
        Collections.sort(optionList);
        return optionList;
    }
}