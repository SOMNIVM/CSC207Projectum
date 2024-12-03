package views;

import app.Config;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.*;

public class StockInputComboBox extends JComboBox<String> {
    private final Map<String, String> nameToOption;
    private final Map<String, String> symbolToOption;
    private final JTextField editor;
    public StockInputComboBox() {
        this.setEditable(true);
        this.nameToOption = new HashMap<>();
        this.symbolToOption = new HashMap<>();
        for (Object obj: Config.STOCK_LIST) {
            JSONObject dataObject = (JSONObject) obj;
            String name = dataObject.getString("name");
            String symbol = dataObject.getString("symbol");
            String option = String.format("%s (%s)", name, symbol);
            this.nameToOption.put(name.toLowerCase(), option);
            this.symbolToOption.put(symbol.toLowerCase(), option);
        }
        List<String> options = new ArrayList<>(symbolToOption.values());
        Collections.sort(options);
        for (String option: options) {
            this.addItem(option);
        }
        this.editor = (JTextField) this.getEditor().getEditorComponent();
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evt) {
                modifyAvailableOptions(editor.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                modifyAvailableOptions(editor.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                modifyAvailableOptions(editor.getText());
            }
        });
    }

    private void modifyAvailableOptions(String prefix) {
        this.removeAllItems();
        String lowerCasePrefix = prefix.toLowerCase();
        List<String> newOptions = getNewOptions(lowerCasePrefix);
        for (String newOption: newOptions) {
            this.addItem(newOption);
        }
    }

    private List<String> getNewOptions(String prefix) {
        Set<String> newOptionSet = new HashSet<>();
        for (String name: nameToOption.keySet()) {
            if (name.startsWith(prefix)) {
                newOptionSet.add(nameToOption.get(name));
            }
        }
        for (String symbol: symbolToOption.keySet()) {
            if (symbol.startsWith(prefix)) {
                newOptionSet.add(symbolToOption.get(symbol));
            }
        }
        List<String> newOptions = new ArrayList<>(newOptionSet);
        Collections.sort(newOptions);
        return newOptions;
    }
}
