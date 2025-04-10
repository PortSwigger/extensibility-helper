package ui.view.pane.storefront;

import data.ImportMetadata;
import data.Item;
import ui.model.StorefrontModel;
import ui.view.pane.storefront.ActionCallbacks.ButtonTogglingActionCallbacks;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.LEADING;
import static javax.swing.SwingConstants.VERTICAL;
import static ui.model.StorefrontModel.*;

public class PreviewPanel<T extends Item> extends JPanel {
    private final StorefrontModel<T> model;
    private final ActionController<T> actionController;
    private final JLabel statusLabel;
    private final JButton importButton;
    private final JButton copyButton;
    private final JButton saveButton;
    private final JButton saveAllButton;

    public PreviewPanel(StorefrontModel<T> storefrontModel,
                        ActionController<T> actionController,
                        ImportMetadata importMetadata) {
        super(new BorderLayout());

        this.model = storefrontModel;
        this.actionController = actionController;

        this.statusLabel = new JLabel();
        this.importButton = new JButton("Import");
        this.copyButton = new JButton("Copy to clipboard");
        this.saveButton = new JButton("Save to file");
        this.saveAllButton = new JButton("Save all items to disk");

        this.importButton.setEnabled(importMetadata.isImportSupported());
        this.importButton.setToolTipText(importMetadata.getImportTooltipText());

        JTextArea codePreview = buildCodePreview();

        add(new JScrollPane(codePreview), CENTER);
        add(buildActionPanel(), SOUTH);

        model.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case STATUS_CHANGED -> statusLabel.setText((String) evt.getNewValue());
                case SELECTED_ITEM_CHANGED -> {
                    T selectedItem = model.getSelectedItem();
                    boolean itemSelected = selectedItem != null;

                    copyButton.setEnabled(itemSelected);
                    saveButton.setEnabled(itemSelected);

                    String previewText = itemSelected ? selectedItem.content() : "";

                    codePreview.setText(previewText);
                    codePreview.setCaretPosition(0);
                }
                case SEARCH_FILTER_CHANGED, ITEMS_UPDATED -> {
                    boolean itemsEmpty = model.getFilteredItems().isEmpty();
                    saveAllButton.setEnabled(!itemsEmpty);
                }
            }
        });
    }

    private JTextArea buildCodePreview() {
        JTextArea codePreview = new JTextArea();
        Font monospacedFont = new Font(
                "monospaced",
                codePreview.getFont().getStyle(),
                codePreview.getFont().getSize()
        );

        codePreview.setEditable(false);
        codePreview.setFont(monospacedFont);
        codePreview.setWrapStyleWord(true);
        codePreview.setComponentPopupMenu(new ItemPopupMenu<>(actionController));

        return codePreview;
    }

    private JComponent buildActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(LEADING));

        importButton.addActionListener(e -> actionController.importSelected());
        copyButton.addActionListener(e -> actionController.copySelected());
        saveButton.addActionListener(e -> actionController.saveSelected(new ButtonTogglingActionCallbacks(saveButton)));
        saveAllButton.addActionListener(e -> actionController.saveAllVisible(new ButtonTogglingActionCallbacks(saveAllButton)));

        actionPanel.add(importButton);
        actionPanel.add(copyButton);
        actionPanel.add(saveButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(saveAllButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(statusLabel);

        return actionPanel;
    }
}
