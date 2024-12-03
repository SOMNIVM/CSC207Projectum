package views;

import app.Config;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Timer;
import java.util.*;

public class StockInputComboBox extends JComboBox<String> {
    private final Map<String, String> nameToOption;
    private final Map<String, String> symbolToOption;
    private final DefaultComboBoxModel<String> model;
    private final JTextField editor;
    private final Timer debounceTimer;
    private boolean suppressUpdate;

    public StockInputComboBox() {
        this.suppressUpdate = false;
        this.nameToOption = new HashMap<>();
        this.symbolToOption = new HashMap<>();
        this.debounceTimer = new Timer(200, null)
        for (Object obj : Config.STOCK_LIST) {
            JSONObject dataObject = (JSONObject) obj;
            String name = dataObject.getString("name");
            String symbol = dataObject.getString("symbol");
            String option = String.format("%s (%s)", name, symbol);
            this.nameToOption.put(name.toLowerCase(), option);
            this.symbolToOption.put(symbol.toLowerCase(), option);
        }
        String[] options = this.symbolToOption.values().toArray(new String[0]);
        Arrays.sort(options);
        this.model = new DefaultComboBoxModel(options);
        this.setModel(this.model);
        this.editor = (JTextField) this.getEditor().getEditorComponent();
        this.editor.setColumns(30);
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evt) {
                SwingUtilities.invokeLater(() -> handleOptionChange());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                SwingUtilities.invokeLater(() -> handleOptionChange());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                SwingUtilities.invokeLater(() -> handleOptionChange());
            }
        });
        this.setMaximumRowCount(6);
        this.setEditable(true);
    }

    private void handleOptionChange() {
        if (!suppressUpdate) {
            suppressUpdate = true;
            try {
                String prefix = editor.getText();
                modifyAvailableOptions(prefix);
                editor.setText(prefix);
                if (StockInputComboBox.this.isShowing()) {
                    StockInputComboBox.this.showPopup();
                }
            }
            finally {
                suppressUpdate = false;
            }
        }
    }

    private void modifyAvailableOptions(String prefix) {
        String lowerCasePrefix = prefix.toLowerCase();
        model.removeAllElements();
        Set<String> newOptionSet = new HashSet<>();
        for (String name : nameToOption.keySet()) {
            if (name.startsWith(lowerCasePrefix)) {
                newOptionSet.add(nameToOption.get(name));
            }
        }
        for (String symbol : symbolToOption.keySet()) {
            if (symbol.startsWith(lowerCasePrefix)) {
                newOptionSet.add(symbolToOption.get(symbol));
            }
        }
        List<String> newOptions = new ArrayList<>(newOptionSet);
        Collections.sort(newOptions);
        for (String newOption: newOptions) {
            model.addElement(newOption);
        }
    }
}